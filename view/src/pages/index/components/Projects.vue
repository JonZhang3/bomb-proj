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
            <el-col :span="6">
                <div style="display: flex;">
                    <div style="flex: 1;">
                        <search-input placeholder="项目名称" button-text="搜索" @search="handleSearch"></search-input>
                    </div>
                    <div style="padding-left: 10px;">
                        <el-button @click="newProjectDialogVisible = true"
                                   type="primary" size="small" icon="el-icon-plus">新增项目</el-button>
                    </div>
                </div>
            </el-col>
        </el-row>
        <el-row style="margin-top: 10px;flex: 1;overflow-y: auto;">
            <el-table :data="tableData"
                      :row-style="{cursor: 'pointer'}"
                      stripe style="width: 100%;" @row-click="projectTableRowClick">
                <el-table-column prop="img" label="封面">
                    <template slot-scope="scope">
                        <img v-if="scope.row.cover" alt="cover" :src="coverBaseUrl + scope.row.cover" style="width: 32px;height: 32px;border-radius: 5px;">
                        <img v-else src="../../../assets/default_cover.png" alt="cover" style="width: 32px;height: 32px;border-radius: 5px;">
                    </template>
                </el-table-column>
                <el-table-column prop="projectName" label="项目名称"></el-table-column>
                <el-table-column prop="userName" label="创建者"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-tooltip effect="dark" content="编辑" placement="top">
                            <el-button @click="projectTableRowEditClick" icon="el-icon-edit" circle size="small"></el-button>
                        </el-tooltip>
                        <el-popover placement="top" width="160" v-model="deleteProjectPopVisible">
                            <p>确定要删除该项目吗？</p>
                            <div style="text-align: right; margin: 0">
                                <el-button size="mini" type="text" @click="deleteProjectPopVisible = false">取消</el-button>
                                <el-button type="primary" size="mini" @click="deleteProjectPopVisible = false">确定</el-button>
                            </div>
<!--                            <el-tooltip effect="dark" content="删除" placement="top" slot="reference">-->
                            <el-button @click="projectTableRowDelClick" type="danger" icon="el-icon-delete" circle size="small" slot="reference"></el-button>
<!--                            </el-tooltip>-->
                        </el-popover>
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
                            @added="newProjectDialogVisible = false;listProjects(null, 1)"></new-project-dialog>
    </div>
</template>

<script>

    import apis from "../../../api/apis";

    import SearchInput from "../../../components/SearchInput";
    import NewProjectDialog from "./project/NewProjectDialog";

    export default {
        name: 'projects',
        components: {
            SearchInput,
            NewProjectDialog
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
                newProjectDialogVisible: false,
                deleteProjectPopVisible: false,
                tableData: []
            }
        },
        mounted() {
            this.listProjects(null, 1);
        },
        methods: {
            projectTableRowClick(row, column, e) {
                e.stopPropagation();
                e.preventDefault();
                alert(1);
            },
            projectTableRowEditClick(e) {
                e.stopPropagation();
                e.preventDefault();
                alert(2);
            },
            projectTableRowDelClick(e) {
                e.stopPropagation();
                e.preventDefault();

            },
            handleSearch(searchText) {
                this.listProjects(searchText, 1);
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
