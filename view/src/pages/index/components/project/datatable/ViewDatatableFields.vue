<template>
    <el-row>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>数据库表</el-breadcrumb-item>
            <el-breadcrumb-item>查看【{{tableName}}】</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top: 10px;">
            <el-row style="margin-top: 10px;">
                <el-col :span="12">
                    <el-button-group>
                        <el-button @click="handleBack" icon="el-icon-arrow-left" size="mini" round>返回</el-button>
                        <el-button @click="getDataTableFields" icon="el-icon-refresh" size="mini" round>刷新</el-button>
                    </el-button-group>
                    <span style="margin-left: 10px;font-size: 14px;color: #606266;">版本：</span>
                    <el-select v-model="currentVersion"
                               @change="handleFieldVersionChange" size="mini">
                        <el-option v-for="item in fieldVersions" :key="item" :value="item" :label="item"></el-option>
                    </el-select>
                </el-col>
                <el-col :span="12" style="text-align: right">
                    <search-input style="width: 40%;" v-model="searchText"
                                  placeholder="字段名" button-text="搜索" @search="handleSearch"></search-input>
                </el-col>
            </el-row>
        </el-row>
        <el-row style="margin-top: 10px;">
            <el-table
                :data="fields.filter(data => !searchText || data.fieldName.toLowerCase().includes(searchText.toLowerCase()))"
                stripe>
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
                            <el-table :data="scope.row.indexes ? [{indexes: scope.row.indexes, indexesName: scope.row.indexesName}] : []">
                                <el-table-column label="索引" min-width="140">
                                    <template slot-scope="iscope">
                                        <span>{{isArray(iscope.row.indexes) ? iscope.row.indexes.join('/') : ''}}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="索引名" prop="indexesName" min-width="120"></el-table-column>
                            </el-table>
                            <el-button size="mini" slot="reference">其 他</el-button>
                        </el-popover>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
    </el-row>
</template>

<script>

    import apis from "../../../../../api/apis";
    import utils from "../../../../../common/utils";

    import SearchInput from "../../../../../components/SearchInput";

    export default {
        name: 'view-datatable-fields',
        components: {
            SearchInput
        },
        data() {
            return {
                searchText: '',
                fields: [],
                currentVersion: '',
                fieldVersions: []
            }
        },
        computed: {
            projectName() {
                return this.$store.state.projectName;
            },
            projectId() {
                return this.$store.state.projectId;
            },
            tableId() {
                return this.$route.params.tableId;
            },
            tableName() {
                return this.$route.params.tableName
            },
            dbType() {
                return this.$route.params.type;
            }
        },
        mounted() {
            if(!this.tableName) {
                this.handleBack();
                return;
            }
            this.getDataTableFields();
        },
        methods: {
            isArray(src) {
                return utils.isArray(src);
            },
            handleSearch() {

            },
            handleBack() {
                this.$router.replace({path: `/project/${this.projectId}/datatable`})
            },
            getDataTableFields() {
                apis.getDataTableFields(this.projectId, this.tableId)
                .then(data => {
                    if(data.code === 1) {
                        this.fields = data.data.fields;
                        this.fieldVersions = data.data.versions;
                        this.currentVersion = this.fieldVersions && this.fieldVersions.length > 0
                            ? this.fieldVersions[0] : '';
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            handleFieldVersionChange(val) {
                apis.getDataTableFields(this.projectId, this.tableId, {version: val})
                    .then(data => {
                        if(data.code === 1) {
                            this.fields = data.data.fields;
                            this.fieldVersions = data.data.versions;
                        } else {
                            this.$message.error(data.message);
                        }
                    });
            }
        }
    }

</script>
