<template>
    <el-row class="main-content-root">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>数据库</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top: 10px;">
            <el-row style="margin-top: 10px;">
                <el-col :span="12">
                    <el-button-group>
                        <el-button @click="handleRefresh"
                                   icon="el-icon-refresh" size="mini" round>刷新</el-button>
                        <el-button @click="handleAddTable"
                                   type="primary"
                                   icon="el-icon-plus" size="mini" round>新增数据库</el-button>
                    </el-button-group>
                </el-col>
                <el-col :span="12" style="text-align: right">
                    <search-input style="width: 40%;"
                        v-model="databaseSearchText"
                        placeholder="数据库名" button-text="搜索"></search-input>
                </el-col>
            </el-row>
        </el-row>
        <el-row style="margin-top: 10px;">
<!--            <span style="font-size: 12px;color: #909399">Tip：点击每行去编辑数据库中的表</span>-->
            <el-table stripe style="width: 100%;"
                      :data="databases.filter(data => !databaseSearchText || data.databaseName.toLowerCase().includes(databaseSearchText.toLowerCase()))">
                <el-table-column prop="databaseName" show-overflow-tooltip sortable label="数据库"></el-table-column>
                <el-table-column prop="databaseDesc" show-overflow-tooltip label="描述" width="380"></el-table-column>
                <el-table-column prop="type" sortable label="类型" width="120"></el-table-column>
                <el-table-column prop="createUser" sortable label="创建者" width="150"></el-table-column>
                <el-table-column prop="updateTime" sortable label="更新时间" width="150"></el-table-column>
                <el-table-column width="100" label="操作">
                    <template slot-scope="scope">
                        <el-tooltip effect="dark" content="查看" placement="top">
                            <el-button type="primary"
                                       @click.stop="handleTableRowView(scope.row)"
                                       icon="el-icon-view" circle size="small"></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="删除" placement="top">
                            <el-button type="danger"
                                       @click="handleTableRowDelete(scope.row, $event)"
                                       icon="el-icon-delete" circle size="small"></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <add-or-edit-project-database-dialog :visible.sync="addProjectDatabaseDialogVisible"
                                      :project-id="projectId"
                                      :db-data="editDbData"
                                      :edit="editDbData != null"
                                      @success="handleTableAddOrUpdateSuccess"
                                      @cancel="handleTableAddOrEditDialogCancle"></add-or-edit-project-database-dialog>
    </el-row>
</template>

<script>

    import apis from "../../../../../api/apis";

    import SearchInput from "../../../../../components/SearchInput";
    import AddOrEditProjectDatabaseDialog from "./AddOrEditProjectDatabaseDialog";

    export default {
        name: 'project-database',
        components: {
            SearchInput,
            AddOrEditProjectDatabaseDialog
        },
        data() {
            return {
                addProjectDatabaseDialogVisible: false,
                databaseSearchText: '',
                databases: [],
                pager: {
                    pageSize: 0,
                    page: 1,
                    total: 0
                },
                editDbData: null
            }
        },
        mounted() {
            this.listProjectDatabases();
        },
        computed: {
            projectName() {
                return this.$store.state.projectName;
            },
            projectId() {
                return this.$store.state.projectId;
            }
        },
        methods: {
            listProjectDatabases() {
                apis.listProjectDatabase(this.projectId).then(data => {
                    if(data.code === 1) {
                        this.databases = data.data;
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            handleTableAddOrUpdateSuccess() {
                this.addProjectDatabaseDialogVisible = false;
                this.databaseSearchText = '';
                this.listProjectDatabases();
            },
            handleTableRowDelete(row, e) {
                e.preventDefault();
                e.stopPropagation();
                this.$confirm(`<span>确定删除 [<strong style="color: #f56c6c;">${row.databaseName}</strong>] 数据库吗</span>`, "提示", {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: '删除',
                    type: 'warning'
                }).then(() => {
                    apis.deleteProjectDatabase(this.projectId, row.id).then(data => {
                        if(data.code === 1) {
                            this.$message.success('删除成功');
                            this.listProjectDatabases();
                        } else {
                            this.$message.error(data.message);
                        }
                    });
                }).catch(() => {});
            },
            handleTableRowView(row) {
                this.$router.push({
                    name: 'project-database-view',
                    params: {
                        id: this.projectId,
                        dbId: row.id,
                        databaseName: row.databaseName,
                        type: row.type
                    }
                });
            },
            handleAddTable() {
                this.editDbData = null;
                this.addProjectDatabaseDialogVisible = true;
            },
            handleTableAddOrEditDialogCancle() {
                this.editDbData = null;
                this.addProjectDatabaseDialogVisible = false;
            },
            handleRefresh() {
                this.databaseSearchText = '';
                this.listProjectDatabases();
            }
        }
    }

</script>
