<template>
    <el-row style="padding: 0 10px">
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
        <el-tabs v-model="activeTab">
            <el-tab-pane label="基本信息" name="baseInfo">{{tableData.tableName}}</el-tab-pane>
            <el-tab-pane label="列信息" name="columnsInfo">
                <el-table
                    :data="fields"
                    size="medium" stripe>
                    <el-table-column label="字段名" prop="fieldName"></el-table-column>
                    <el-table-column label="类型" prop="type"></el-table-column>
                    <el-table-column label="长度" prop="length"></el-table-column>
                    <el-table-column label="注释" prop="notes"></el-table-column>
                    <el-table-column label="非空" prop="notNull">
                        <template slot-scope="scope">
                            <i v-if="scope.row.notNull === '1'"
                               style="font-size: 20px; color: #409EFF;" class="el-icon-success"></i>
                            <i v-else style="font-size: 20px;" class="el-icon-error"></i>
                        </template>
                    </el-table-column>
                    <el-table-column label="主键" prop="pk">
                        <template slot-scope="scope">
                            <i v-if="scope.row.pk === '1'"
                               style="font-size: 20px; color: #409EFF;" class="el-icon-success"></i>
                            <i v-else style="font-size: 20px;" class="el-icon-error"></i>
                        </template>
                    </el-table-column>
                    <el-table-column label="自增" prop="autoIncrement">
                        <template slot-scope="scope">
                            <i v-if="scope.row.autoIncrement === '1'"
                               style="font-size: 20px; color: #409EFF;" class="el-icon-success"></i>
                            <i v-else style="font-size: 20px;" class="el-icon-error"></i>
                        </template>
                    </el-table-column>
                    <el-table-column label="默认值" prop="defaultValue"></el-table-column>
                    <el-table-column label="其他">
                        <template slot-scope="scope">
                            <el-popover placement="top-start" trigger="click">
                                <el-table :data="[{indexes: scope.row.indexes, indexesName: scope.row.indexesName, createUserName: scope.row.createUserName}]">
                                    <el-table-column label="索引" min-width="140">
                                        <template slot-scope="scope">
                                            <span>{{isArray(scope.row.indexes) ? scope.row.indexes.join('/') : '-'}}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="索引名" prop="indexesName" min-width="120">
                                        <template slot-scope="scope">
                                            <span>{{scope.row.indexesName ? scope.row.indexesName : '-'}}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="创建者" prop="createUserName" min-width="120"></el-table-column>
                                </el-table>
                                <el-button size="mini" slot="reference">其 他</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                </el-table>
            </el-tab-pane>
            <el-tab-pane label="索引" name="indexesInfo">索引</el-tab-pane>
        </el-tabs>
    </el-row>
</template>

<script>

    import apis from "../../../../../api/apis";
    import utils from "../../../../../common/utils";

    export default {
        name: 'ViewDataTable',
        inject: ['root'],
        data() {
            return {
                activeTab: 'baseInfo',
                currentVersion: '',
                fieldVersions: [],
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

<style scoped>
    .el-tabs__item {
        height: 30px;
        line-height: 30px;
    }
</style>
