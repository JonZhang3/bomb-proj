<template>
    <el-row>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>项目成员</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top: 10px;">
            <el-col :span="12">
                <el-button-group>
                    <el-button @click="listProjectMemebers(null, 1)"
                               icon="el-icon-refresh" size="mini" round>刷新</el-button>
                    <el-button type="primary"
                               @click="newProjectMemberDialogVisible = true"
                               icon="el-icon-plus" size="mini" round>新增成员</el-button>
                </el-button-group>
            </el-col>
            <el-col :span="12" style="text-align: right">
                <search-input
                    style="width: 40%;"
                    placeholder="成员昵称" button-text="搜索" @search="handleSearch"></search-input>
            </el-col>
        </el-row>
        <el-row style="margin-top: 10px;">
            <el-table stripe style="width: 100%;" :data="tableData">
                <el-table-column prop="nickName" label="成员"></el-table-column>
                <el-table-column prop="email" label="成员邮箱"></el-table-column>
                <el-table-column prop="phone" label="成员手机号"></el-table-column>
                <el-table-column width="500" label="成员权限">
                    <template slot-scope="scope">
                        <div v-if="scope.row.permission" class="project-member-permiss-tags">
                            <el-tag effect="dark"
                                    v-for="(item, index) in scope.row.permission.split(',')"
                                    :key="index"
                                    v-if="getPermissionName(item)">
                                {{getPermissionName(item)}}
                            </el-tag>
                        </div>
                        <div v-else>-</div>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-tooltip effect="dark" content="编辑" placement="top">
                            <el-button @click=""
                                       icon="el-icon-edit" circle size="small"></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="删除" placement="top">
                            <el-button @click="handleMemberTableDelete"
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
        <add-project-member-dialog :visible.sync="newProjectMemberDialogVisible"></add-project-member-dialog>
    </el-row>
</template>

<script>

    import apis from "../../../../../api/apis";
    import permission from "../../../../../common/permission";

    import SearchInput from "../../../../../components/SearchInput";
    import AddProjectMemberDialog from "./AddProjectMemberDialog";

    export default {
        name: 'project-member',
        components: {
            SearchInput,
            AddProjectMemberDialog
        },
        data() {
            return {
                newProjectMemberDialogVisible: false,
                pager: {
                    pageSize: 0,
                    page: 1,
                    total: 0
                },
                tableData: []
            }
        },
        mounted() {
            this.listProjectMemebers(null, 1);
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
            listProjectMemebers(name, page) {
                const params = {};
                if(name) {
                    params['name'] = name;
                }
                params['page'] = page;
                apis.queryProjectMembers(this.projectId, params).then(data => {
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
                this.listProjectMemebers(searchText, 1);
            },
            getPermissionName(key) {
                return permission.getName(key);
            },
            handleMemberTableDelete(id) {
                // this.$confirm()
            }
        }
    }

</script>

<style>

    .project-member-permiss-tags .el-tag {
        margin-top: 2px;
        border-radius: 16px;
    }

    .project-member-permiss-tags .el-tag:not(:last-child) {
        margin-right: 2px;
    }

</style>
