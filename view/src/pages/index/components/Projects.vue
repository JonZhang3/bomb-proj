<template>
    <div style="display: flex;flex-direction: column; background-color: #ffffff;height: 100%;">
        <el-row type="flex" justify="space-between">
            <el-col :span="6">
                <el-radio-group v-model="projectType" size="small">
                    <el-radio-button :label="1">我的</el-radio-button>
                    <el-radio-button :label="2">我创建的</el-radio-button>
                    <el-radio-button :label="3">我加入的</el-radio-button>
                </el-radio-group>
            </el-col>
            <el-col :span="8">
                <div style="display: flex;">
                    <div style="flex: 1;">
                        <search-input placeholder="项目名称" button-text="搜索" @search="handleSearch"></search-input>
                    </div>
                    <div style="padding-left: 10px;">
                        <el-button icon="el-icon-refresh-right" size="small" @click="listProjects(null, 1)">刷新</el-button>
                        <el-button @click="newProjectDialogVisible = true"
                                   type="primary" size="small" icon="el-icon-plus">新增项目</el-button>
                    </div>
                </div>
            </el-col>
        </el-row>
        <el-row style="margin-top: 10px;flex: 1;overflow-y: auto;">
            <el-table :data="tableData"
                      :row-style="{cursor: 'pointer'}"
                      stripe style="width: 100%;" @row-click="handleProjectTableRowClick">
                <el-table-column prop="img" label="封面" width="80">
                    <template slot-scope="scope">
                        <img v-if="scope.row.cover" alt="cover" :src="coverBaseUrl + scope.row.cover" style="width: 32px;height: 32px;border-radius: 5px;">
                        <img v-else src="../../../assets/default_cover.png" alt="cover" style="width: 32px;height: 32px;border-radius: 5px;">
                    </template>
                </el-table-column>
                <el-table-column prop="projectName" label="项目名称"></el-table-column>
                <el-table-column prop="userName" label="创建者"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
                <el-table-column label="操作" width="100">
                    <template slot-scope="scope">
                        <el-tooltip effect="dark" content="编辑" placement="top">
                            <el-button @click="handleProjectTableRowEditClick(scope.row, $event)"
                                       icon="el-icon-edit" circle size="small"></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="删除" placement="top">
                            <el-button @click="handleProjectTableRowDelClick(scope.row.projectName, scope.row.id, $event)"
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
        <new-project-dialog :visible.sync="newProjectDialogVisible"
                            @cancel="newProjectDialogVisible = false"
                            @added="handleProjectAdded"></new-project-dialog>
        <edit-project-dialog :visible.sync="editProjectDialogVisible"
                             :projectData="editProjectData"
                             @edited="handleProjectEdited"
                             @cancel="editProjectDialogVisible = false"></edit-project-dialog>
    </div>
</template>

<script>

    import apis from "../../../api/apis";

    import SearchInput from "../../../components/SearchInput";
    import NewProjectDialog from "./project/NewProjectDialog";
    import EditProjectDialog from "./project/EditProjectDialog";

    export default {
        name: 'projects',
        components: {
            SearchInput,
            NewProjectDialog,
            EditProjectDialog
        },
        data() {
            return {
                coverBaseUrl: apis.coverBaseUrl,
                projectType: '1',
                pager: {
                    pageSize: 0,
                    page: 1,
                    total: 0
                },
                editProjectData: {},
                newProjectDialogVisible: false,
                editProjectDialogVisible: false,
                tableData: []
            }
        },
        mounted() {
            this.listProjects(null, 1);
        },
        methods: {
            handleProjectTableRowClick(row, column, e) {
                e.stopPropagation();
                e.preventDefault();
                this.$router.push({path: `/project/${row.id}`});
            },
            handleProjectTableRowEditClick(project, e) {
                e.stopPropagation();
                e.preventDefault();
                this.editProjectData = {
                    id: project.id,
                    name: project.projectName,
                    desc: project.projectDesc,
                    type: project.type,
                    cover: project.cover,
                    gitAddr: project.gitAddr
                };
                this.editProjectDialogVisible = true;
            },
            handleProjectTableRowDelClick(projectName, projectId, e) {
                e.stopPropagation();
                e.preventDefault();
                this.$confirm(`<span>确定删除 [<strong style="color: #f56c6c;">${projectName}</strong>] 项目吗</span>`, '提示', {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: '删除',
                    type: 'warning'
                }).then(() => {
                    // TODO delete project
                }).catch(() => {});
            },
            handleSearch(searchText) {
                this.listProjects(searchText, 1);
            },
            handleProjectAdded() {
                this.newProjectDialogVisible = false;
                this.listProjects(null, 1)
            },
            handleProjectEdited() {
                this.editProjectDialogVisible = false;
                this.listProjects(null, 1)
            },
            listProjects(name, page) {
                const params = {};
                if(name) {
                    params['name'] = name;
                }
                params['page'] = page;
                apis.queryProjects(params).then(data => {
                    if(data.code === 1) {
                        this.pager.pageSize = data.data.limit;
                        this.pager.page = data.data.page;
                        this.pager.total = data.data.total;
                        this.tableData = data.data.records;
                    } else {
                        this.$message.error(data.message);
                    }
                });
            }
        }
    }

</script>
