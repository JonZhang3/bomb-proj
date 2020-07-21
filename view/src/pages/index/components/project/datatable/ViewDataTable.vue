<template>
    <el-row class="project-table-info">
        <el-row style="display: flex;align-items: center;">
            <el-col :span="12">
                <span style="font-size: 14px;color: #606266">查看表【{{tableData.tableName}}】</span>
            </el-col>
            <el-col :span="12" style="display: flex;align-items: center; justify-content: flex-end">
                <div v-if="activeTab === 'columnsInfo'" style="margin-right: 10px;">
                    <span style="margin-left: 10px;font-size: 14px;color: #606266;">版本：</span>
                    <el-select v-model="currentVersion" @change="handleTableVersionChange" size="mini">
                        <el-option v-for="item in fieldVersions" :key="item" :value="item" :label="item"></el-option>
                    </el-select>
                </div>
                <el-button-group>
                    <el-button @click="handleRefresh" icon="el-icon-refresh" size="mini" round>刷新</el-button>
                    <el-button @click="handleEdit" type="primary" icon="el-icon-edit" size="mini" round>编辑</el-button>
                    <el-button @click="handleTableDelete" type="danger" icon="el-icon-delete" size="mini" round>删除</el-button>
                </el-button-group>
            </el-col>
        </el-row>
        <el-tabs v-model="activeTab" style="flex: 1;overflow: hidden;">
            <el-tab-pane ref="baseInfoEl" label="基本信息" name="baseInfo" style="padding: 0 10px;">
                <el-form>
                    <el-form-item label="表名：">
                        <span>{{tableData.tableName}}</span>
                    </el-form-item>
                    <el-form-item label="注释：">
                        <span>{{tableData.tableDesc}}</span>
                    </el-form-item>
                    <el-form-item label="创建者：">
                        <span>{{tableData.createUser}}</span>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="列信息" name="columnsInfo">
                <div ref="tableWrapper" style="height: 100%;display: flex;flex-direction: column;">
                    <div style="text-align: right;">
                        <search-input style="width: 30%;" v-model="fieldNameSearchText" placeholder="模糊搜索列名"></search-input>
                    </div>
                    <bomb-table :columns="columns"
                                :data="fields.filter(data => !fieldNameSearchText || data.fieldName.toLowerCase().includes(fieldNameSearchText.toLowerCase()))"
                                fix-header>
                        <template slot-scope="scope" slot="notNull">
                            <i v-if="scope.notNull === '1'"
                               style="font-size: 20px; color: #409EFF;" class="el-icon-success"></i>
                            <i v-else style="font-size: 20px;" class="el-icon-error"></i>
                        </template>
                        <template slot-scope="scope" slot="pk">
                            <i v-if="scope.pk === '1'"
                               style="font-size: 20px; color: #409EFF;" class="el-icon-success"></i>
                            <i v-else style="font-size: 20px;" class="el-icon-error"></i>
                        </template>
                        <template slot-scope="scope" slot="autoIncrement">
                            <i v-if="scope.autoIncrement === '1'"
                               style="font-size: 20px; color: #409EFF;" class="el-icon-success"></i>
                            <i v-else style="font-size: 20px;" class="el-icon-error"></i>
                        </template>
                    </bomb-table>
                </div>
            </el-tab-pane>
            <el-tab-pane label="索引" name="indexesInfo">
                <el-table>
                    <el-table-column label="索引名" prop="fieldName"></el-table-column>
                    <el-table-column label="包含列" prop="fieldName"></el-table-column>
                    <el-table-column label="索引名" prop="fieldName"></el-table-column>
                    <el-table-column label="索引类型" prop="fieldName"></el-table-column>
                    <el-table-column label="索引方式" prop="fieldName"></el-table-column>
                </el-table>
            </el-tab-pane>
        </el-tabs>
    </el-row>
</template>

<script>

    import apis from "../../../../../api/apis";
    import utils from "../../../../../common/utils";
    import SearchInput from "../../../../../components/SearchInput";

    export default {
        name: 'ViewDataTable',
        inject: ['root'],
        components: {
            SearchInput
        },
        data() {
            return {
                activeTab: 'baseInfo',
                fieldNameSearchText: '',
                tableHeight: '',
                currentVersion: '',
                fieldVersions: [],
                columns: [
                    {title: '列名', key: 'fieldName'},
                    {title: '类型', key: 'type'},
                    {title: '长度', key: 'length'},
                    {title: '注释', key: 'notes'},
                    {title: '非空', key: 'notNull', slot: 'notNull'},
                    {title: '主键', key: 'pk', slot: 'pk'},
                    {title: '自增', key: 'autoIncrement', slot: 'autoIncrement'},
                    {title: '默认值', key: 'defaultValue'},
                ],
                fields: [],
                tableData: this.root.currentTableData
            }
        },
        computed: {
            projectName() {
                return this.$store.state.projectName;
            },
            projectId() {
                return this.$store.state.projectId;
            },
            databaseId() {
                return this.$route.params.dbId;
            },
            tableId() {
                return this.$route.params.tableId;
            }
        },
        mounted() {
            this.getDataTableFields();
        },
        watch: {
            tableId() {
                this.activeTab = 'baseInfo';
                this.fieldNameSearchText = '';
                this.getDataTableFields();
            },
            'root.currentTableData'(val) {
                this.tableData = val;
            }
        },
        methods: {
            isArray(src) {
                return utils.isArray(src);
            },
            handleRefresh() {
                this.getDataTableFields();
            },
            handleEdit() {
                this.root.changeBreadcrumb('编辑');
                this.$router.push({path: `/project/${this.projectId}/db/${this.databaseId}/table/${this.tableId}/edit`});
            },
            handleTableDelete() {
                this.$confirm(`<span>确定删除 [<strong style="color: #f56c6c;">${this.tableData.tableName}</strong>] 表吗</span>`, "提示", {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: '删除',
                    type: 'warning'
                }).then(() => {
                    this.deleteTable();
                }).catch(() => {});
            },
            handleTableVersionChange(val) {
                apis.getDataTableFields(this.projectId, this.databaseId, this.tableId, {version: val}).then(data => {
                    if(data.code === 1) {
                        this.fields = data.data.fields;
                        this.fieldVersions = data.data.versions;
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            getDataTableFields() {
                apis.getDataTableFields(this.projectId, this.databaseId, this.tableId).then(data => {
                    if (data.code === 1) {
                        this.fields = data.data.fields;
                        this.fieldVersions = data.data.versions;
                        this.currentVersion = this.fieldVersions && this.fieldVersions.length > 0 ? this.fieldVersions[0] : '';
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            deleteTable() {
                apis.deleteProjectDataTable(this.projectId, this.databaseId, this.tableId).then(data => {
                    if(data.code === 1) {
                        this.$message.success('删除成功');
                    } else {
                        this.$message.error(data.message);
                    }
                });
            }
        }
    }

</script>

<style>
    .project-table-info {
        padding: 0 10px;
        height: 100%;
        display: flex;
        flex-direction: column;

    }
    .project-table-info .el-tabs {
        display: flex;
        flex-direction: column;
    }
    .project-table-info .el-tabs__item {
        height: 30px;
        line-height: 30px;
    }
    .project-table-info .el-form-item {
        margin-bottom: 0;
    }
    .project-table-info .el-tabs__content {
        flex: 1;
    }
    .project-table-info .el-tab-pane {
        height: 100%;
    }
</style>
