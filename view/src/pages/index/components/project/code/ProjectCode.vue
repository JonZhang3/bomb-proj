<template>
    <el-row>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>状态码</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top: 10px;">
            <el-col :span="12">
                <el-button-group>
                    <el-button @click="listProjectCode(null, 1)"
                               icon="el-icon-refresh" size="mini" round>刷新</el-button>
                    <el-button type="primary"
                               @click="addProjectCodeDialogVisible = true"
                               icon="el-icon-plus" size="mini" round>新增状态码</el-button>
                </el-button-group>
            </el-col>
            <el-col :span="12" style="text-align: right">
                <search-input
                    style="width: 40%;"
                    placeholder="状态码 / 描述" button-text="搜索" @search="handleSearch"></search-input>
            </el-col>
        </el-row>
        <el-row style="margin-top: 10px;">
            <el-table stripe style="width: 100%;" :data="tableData">
                <el-table-column prop="code" label="状态码"></el-table-column>
                <el-table-column prop="message" label="描述"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-tooltip effect="dark" content="编辑" placement="top">
                            <el-button @click="handleTableRowEditClick(scope.row, $event)"
                                       icon="el-icon-edit" circle size="small"></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="删除" placement="top">
                            <el-button @click="handleTableRowDeleteClick(scope.row.code, scope.row.id, $event)"
                                       type="danger" icon="el-icon-delete" circle size="small"></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <el-row style="text-align: right;margin-top: 10px;">
            <el-pagination background layout="prev,pager,next"
                           :page-size="pager.pageSize"
                           :current-page="pager.page" :total="pager.total"></el-pagination>
        </el-row>
        <add-project-code-dialog
            :visible.sync="addProjectCodeDialogVisible"
            @added="handleCodeAdded"
            @cancel="addProjectCodeDialogVisible = false"></add-project-code-dialog>
        <edit-project-code-dialog
            :code-data="editCodeData"
            :visible.sync="editProjectCodeDialogVisible"
            @edited="handleCodeEdited"
            @cancel="editProjectCodeDialogVisible = false"></edit-project-code-dialog>
    </el-row>
</template>

<script>

    import apis from "../../../../../api/apis";

    import SearchInput from "../../../../../components/SearchInput";
    import AddProjectCodeDialog from "./AddProjectCodeDialog";
    import EditProjectCodeDialog from "./EditProjectCodeDialog";

    export default {
        name: 'project-code',
        components: {
            EditProjectCodeDialog,
            AddProjectCodeDialog,
            SearchInput
        },
        data() {
            return {
                tableData: [],
                pager: {
                    pageSize: 0,
                    page: 1,
                    total: 0
                },
                addProjectCodeDialogVisible: false,
                editProjectCodeDialogVisible: false,
                editCodeData: {}
            }
        },
        mounted() {
            this.listProjectCode(null, 1);
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
            listProjectCode(searchText, page) {
                const params = {};
                if(searchText) {
                    params['queryText'] = searchText;
                }
                params['page'] = page;
                apis.listProjectCode(this.projectId, params).then(data => {
                    if(data.code === 1) {
                        this.pager.pageSize = data.data.limit;
                        this.pager.page = data.data.page;
                        this.pager.total = data.data.total;
                        this.tableData = data.data.records;
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            handleSearch(searchText) {
                this.listProjectCode(searchText, 1);
            },
            handleCodeAdded() {
                this.addProjectCodeDialogVisible = false;
                this.listProjectCode(null, 1);
            },
            handleCodeEdited() {
                this.editProjectCodeDialogVisible = false;
                this.listProjectCode(null, 1);
            },
            handleTableRowEditClick(code, e) {
                e.stopPropagation();
                e.preventDefault();
                this.editCodeData = {
                    id: code.id,
                    code: code.code,
                    message: code.message
                };
                this.editProjectCodeDialogVisible = true;
            },
            handleTableRowDeleteClick(code, id, e) {
                e.stopPropagation();
                e.preventDefault();
                this.$confirm(`<span>确定删除 [<strong style="color: #f56c6c;">${code}</strong>] 状态码吗</span>`, '提示', {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: '删除',
                    type: 'warning'
                }).then(() => {
                    // TODO delete project
                    apis.deleteProjectCode(this.projectId, id).then(data => {
                        if(data.code === 1) {
                            this.$message.success('删除成功');
                            this.listProjectCode(null, 1);
                        } else {
                            this.$message.error(data.message);
                        }
                    });
                }).catch(() => {});
            }
        }
    }

</script>
