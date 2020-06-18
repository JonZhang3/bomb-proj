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
                        <search-input placeholder="项目名称" button-text="搜索"></search-input>
                    </div>
                    <div style="padding-left: 10px;">
                        <el-button @click="() => this.newProjectDialogVisible = true"
                                   type="primary" size="small" icon="el-icon-plus">新增项目</el-button>
                    </div>
                </div>
            </el-col>
        </el-row>
        <el-row style="margin-top: 10px;flex: 1;overflow-y: auto;">
            <el-table :data="tableData"
                      :row-style="{cursor: 'pointer'}"
                      stripe style="width: 100%;" @row-click="projectTableRowClick">
                <el-table-column prop="img" label="封面"></el-table-column>
                <el-table-column prop="name" label="名称"></el-table-column>
                <el-table-column prop="create" label="创建者"></el-table-column>
                <el-table-column prop="time" label="创建时间"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-tooltip effect="dark" content="编辑" placement="top">
                            <el-button @click="projectTableRowEditClick" icon="el-icon-edit" circle size="small"></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="删除" placement="top">
                            <el-button @click="projectTableRowDelClick" type="danger" icon="el-icon-delete" circle size="small"></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <el-row style="text-align: right;margin-top: 10px;">
            <el-pagination background layout="prev,pager,next"
                           :page-size="pager.pageSize"
                           :current-page="pager.page" :total="pager.totle"></el-pagination>
        </el-row>
        <new-project-dialog :visible.sync="newProjectDialogVisible"></new-project-dialog>
    </div>
</template>

<script>

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
                projectType: '1',
                pager: {
                    pageSize: 10,
                    page: 1,
                    totle: 100
                },
                newProjectDialogVisible: false,
                tableData: [{
                    date: '2016-05-03',
                    name: '王小虎1',
                    address: '上海市普陀区金沙江路 1518 弄'
                }, {
                    date: '2016-05-02',
                    name: '王小虎2',
                    address: '上海市普陀区金沙江路 1518 弄'
                }]
            }
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

            }
        }
    }

</script>
