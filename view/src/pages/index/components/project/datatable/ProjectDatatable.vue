<template>
    <el-row>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>数据库表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top: 10px;">
            <el-row style="margin-top: 10px;">
                <el-col :span="12">
                    <el-button-group>
                        <el-button @click="listProjectDataTables(1)"
                                   icon="el-icon-refresh" size="mini" round>刷新</el-button>
                        <el-button @click="handleAddTable"
                                   type="primary"
                                   icon="el-icon-plus" size="mini" round>新增数据库表</el-button>
                    </el-button-group>
                </el-col>
                <el-col :span="12" style="text-align: right">
                    <search-input style="width: 40%;"
                        v-model="tableSearchText"
                        placeholder="表名" button-text="搜索" @search="handleSearch"></search-input>
                </el-col>
            </el-row>
        </el-row>
        <el-row style="margin-top: 10px;">
            <el-table stripe style="width: 100%;" :data="tables">
                <el-table-column prop="tableName" label="表名"></el-table-column>
                <el-table-column prop="tableDesc" label="描述"></el-table-column>
                <el-table-column prop="type" label="数据库类型"></el-table-column>
                <el-table-column prop="createUser" label="创建者"></el-table-column>
                <el-table-column prop="updateTime" label="更新时间"></el-table-column>
                <el-table-column width="100" label="操作">
                    <template slot-scope="scope">
                        <el-tooltip effect="dark" content="编辑" placement="top">
                            <el-button @click="handleTableRowEdit(scope.row)"
                                       icon="el-icon-edit" circle size="small"></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="删除" placement="top">
                            <el-button type="danger"
                                       @click="handleTableRowDelete(scope.row)"
                                       icon="el-icon-delete" circle size="small"></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
<!--        <el-row style="text-align: right;margin-top: 10px;">-->
<!--            <el-pagination background layout="prev,pager,next"-->
<!--                           :page-size="pager.pageSize"-->
<!--                           :current-page="pager.page" :total="pager.total"></el-pagination>-->
<!--        </el-row>-->
        <add-or-edit-project-datatable-dialog :visible.sync="addProjectDatatableDialogVisible"
                                      :project-id="projectId"
                                      :table-data="editTableData"
                                      :edit="editTableData != null"
                                      @success="handleTableAddOrUpdateSuccess"
                                      @cancel="handleTableAddOrEditDialogCancle"></add-or-edit-project-datatable-dialog>
    </el-row>
</template>

<script>
    // #E5F2FF

    import apis from "../../../../../api/apis";

    import SearchInput from "../../../../../components/SearchInput";
    import AddOrEditProjectDatatableDialog from "./AddOrEditProjectDatatableDialog";

    export default {
        name: 'project-datatable',
        components: {
           SearchInput,
            AddOrEditProjectDatatableDialog
        },
        data() {
            return {
                addProjectDatatableDialogVisible: false,
                tableSearchText: '',
                tables: [],
                pager: {
                    pageSize: 0,
                    page: 1,
                    total: 0
                },
                editTableData: null
            }
        },
        mounted() {
            this.listProjectDataTables(1);
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
            handleSearch() {
                this.listProjectDataTables(1);
            },
            listProjectDataTables(page) {
                const params = {};
                if(this.tableSearchText) {
                    params['tableName'] = this.tableSearchText;
                }
                // params['page'] = page;
                apis.listProjectDataTables(this.projectId, params).then(data => {
                    if(data.code === 1) {
                        this.tables = data.data;
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            handleTableAddOrUpdateSuccess() {
                this.addProjectDatatableDialogVisible = false;
                this.tableSearchText = '';
                this.listProjectDataTables(1);
            },
            handleTableRowDelete(row) {
                this.$confirm(`<span>确定删除 [<strong style="color: #f56c6c;">${row.tableName}</strong>] 表吗</span>`, "提示", {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: '删除',
                    type: 'warning'
                }).then(() => {
                    apis.deleteProjectDataTable(this.projectId, row.id).then(data => {
                        if(data.code === 1) {
                            this.$message.success('删除成功');
                            this.listProjectDataTables(1);
                        } else {
                            this.$message.error(data.message);
                        }
                    });
                }).catch(() => {});
            },
            handleTableRowEdit(row) {
                this.editTableData = {
                    id: row.id,
                    tableName: row.tableName,
                    tableDesc: row.tableDesc,
                    type: row.type
                };
                this.addProjectDatatableDialogVisible = true;
            },
            handleAddTable() {
                this.editTableData = null;
                this.addProjectDatatableDialogVisible = true;
            },
            handleTableAddOrEditDialogCancle() {
                this.editTableData = null;
                this.addProjectDatatableDialogVisible = false;
            }
        }

    }

</script>
