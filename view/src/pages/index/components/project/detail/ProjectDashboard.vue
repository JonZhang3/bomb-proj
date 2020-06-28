<template>
    <el-row>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>项目详情</el-breadcrumb-item>
        </el-breadcrumb>
        <div style="margin-top: 10px;background-color: #fff;padding: 10px;border-radius: 5px;">
            <div>
                <div style="display: flex;align-items: center;">
                    <span style="color: #409EFF;font-size: 20px;">{{projectName}}</span>
                    <el-button-group style="margin-left: 5px;">
                        <el-button @click="editProjectDialogVisible = true" round
                                   icon="el-icon-edit" size="mini">编辑</el-button>
                        <el-button @click="handleRefresh" icon="el-icon-refresh" round size="mini">刷新</el-button>
                        <el-button icon="el-icon-delete" round type="danger" size="mini">删除</el-button>
                        <el-button icon="el-icon-switch-button" type="danger" round size="mini">退出</el-button>
                    </el-button-group>
                </div>
            </div>
            <div class="project-db-detail-root">
                <div>
                    <span class="detail-item">
                        <span class="title">项目类型：</span>
                        <span class="content">{{projectData.type === 1 ? "私有项目" : "公开项目"}}</span>
                    </span>
                    <span class="detail-item"><span class="title">创建人：</span><span class="content">{{projectData.userName}}</span></span>
                </div>
                <div style="margin-top: 10px;">
                    <span class="detail-item"><span class="title">创建时间：</span><span class="content">{{projectData.createTime}}</span></span>
                    <span class="detail-item"><span class="title">更新时间：</span><span class="content">{{projectData.updateTime}}</span></span>
                </div>
                <div style="margin-top: 10px;" v-if="projectData.gitAddr">
                    <span class="detail-item"><span class="title">Git 地址：</span><span class="content">{{projectData.gitAddr}}</span></span>
                </div>
                <div style="margin-top: 10px;" v-if="projectData.desc">
                    <span class="detail-item"><span class="title">项目简介：</span><span class="content">{{projectData.desc}}</span></span>
                </div>
                <div style="margin-top: 10px;" v-if="projectData.remark">
                    <span class="detail-item"><span class="title">备注：</span><span class="content">{{projectData.remark}}</span></span>
                </div>
            </div>
            <div>
                <p style="font-size: 14px;">项目统计</p>
                <div style="display: flex; flex-wrap: wrap">
                    <a class="project-db-statistics-item">
                        <span class="title"><i class="el-icon el-icon-edit"></i>项目成员</span>
                        <br/>
                        <span class="desc">成员数：3</span>
                    </a>
                    <a class="project-db-statistics-item">
                        <span class="title"><i class="el-icon el-icon-edit"></i>项目成员</span>
                        <br/>
                        <span class="desc">成员数：3</span>
                    </a>
                </div>
            </div>
        </div>
        <edit-project-dialog :visible.sync="editProjectDialogVisible"
                             :projectData="projectData"
                             @edited="handleProjectEdited"
                             @cancel="editProjectDialogVisible = false"></edit-project-dialog>
    </el-row>
</template>

<script>

    import apis from "../../../../../api/apis";

    import EditProjectDialog from "../EditProjectDialog";

    export default {
        name: 'project-dashboard',
        components: {
            EditProjectDialog
        },
        data() {
            return {
                editProjectDialogVisible: false,
                projectData: {
                    id: '',
                    name: '',
                    desc: '',
                    type: '',
                    cover: '',
                    gitAddr: '',
                    userId: '',
                    userName: '',
                    createTime: '',
                    updateTime: '',
                    remark: '',
                },
            }
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
            handleProjectEdited() {
                this.editProjectDialogVisible = false;
                this.projectDetail();
            },
            handleRefresh() {
                this.projectDetail();
            },
            projectDetail() {
                apis.getProjectDetail(this.projectId).then(data => {
                    if(data.code === 1) {
                        this.projectData.id = data.data.id;
                        this.projectData.name = data.data.projectName;
                        this.projectData.desc = data.data.projectDesc;
                        this.projectData.type = data.data.type;
                        this.projectData.cover = data.data.cover;
                        this.projectData.gitAddr = data.data.gitAddr;
                        this.projectData.createTime = data.data.createTime;
                        this.projectData.updateTime = data.data.updateTime;
                        this.projectData.remark = data.data.remark;
                        this.projectData.userName = data.data.userName;
                        this.projectData.userId = data.data.userId;
                    } else {
                        this.$message.error(data.message);
                    }
                })
            }
        },
        mounted() {
            this.projectDetail();
        }
    }

</script>

<style>

    .project-db-detail-root {
        padding: 10px;
        margin-top: 5px;
        border-radius:5px;
        font-size: 14px;
        background-color: #F9FAFB;
        color: #909399;
    }

    .project-db-detail-root .detail-item:not(:first-child) {
        margin-left: 20px;
    }

    .project-db-detail-root .detail-item .title {
        display: inline-block;
        width: 80px;
        text-align: right;
    }

    .project-db-detail-root .detail-item .content {
        color: #303133;
    }

    .project-db-statistics-item {
        background-color: #F9FAFB;
        padding: 10px;
        width: 160px;
        display: block;
        border: 1px solid #eeeeee;
        overflow: hidden;
        cursor: pointer;
    }

    .project-db-statistics-item:hover .title {
        color: #409EFF;
    }

    .project-db-statistics-item .title {
        font-size: 14px;
        color: #606266;
    }

    .project-db-statistics-item .el-icon {
        padding-right: 5px;
    }

    .project-db-statistics-item .desc {
        color: #909399;
        font-size: 12px;
    }

</style>
