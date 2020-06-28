<template>
    <el-container style="position: relative;bottom: 0;overflow-y: hidden">
        <el-aside width="200px">
            <el-menu :default-active="activeIndex"
                     background-color="#303543"
                     text-color="#fff"
                     active-text-color="#409EFF"
                     :router="true"
                     style="height: 100%;">
                <el-menu-item index="/">
                    <template slot="title">
                        <i class="el-icon-arrow-left"></i>
                        <span>返回</span>
                    </template>
                </el-menu-item>
                <el-menu-item :index="`/project/${$route.params.id}`">
                    <template slot="title">
                        <i class="el-icon-s-home"></i>
                        <span>项目详情</span>
                    </template>
                </el-menu-item>
                <el-menu-item :index="`/project/${$route.params.id}/member`">
                    <template slot="title">
                        <i class="el-icon-user-solid"></i>
                        <span>项目成员</span>
                    </template>
                </el-menu-item>
                <el-menu-item :index="`/project/${$route.params.id}/code`">
                    <template slot="title">
                        <i class="el-icon-error"></i>
                        <span>状态码</span>
                    </template>
                </el-menu-item>
                <el-menu-item :index="`/project/${$route.params.id}/datatable`">
                    <template slot="title">
                        <i class="el-icon-s-grid"></i>
                        <span>数据库表</span>
                    </template>
                </el-menu-item>
            </el-menu>
        </el-aside>
        <!--        background-color: #F3F4F4;-->
        <el-main style="overflow-y: auto;">
            <router-view></router-view>
        </el-main>
    </el-container>
</template>

<script>

    import apis from "../../../api/apis";

    export default {
        name: 'project-detail',
        props: {
            projectName: String
        },
        data() {
            return {
                activeIndex: this.$route.path,
            }
        },
        beforeMount() {
            this.$store.commit('setProjectId', this.$route.params.id);
            this.projectDetail();
        },
        methods: {
            projectDetail() {
                const projectId = this.$route.params.id;
                apis.getProjectDetail(projectId).then(data => {
                    if(data.code === 1) {
                        this.$store.commit('setProjectName', data.data.projectName);
                    } else {
                        this.$message.error(data.message);
                    }
                }).catch(err => {
                    if(err.response.status === 404) {
                        this.$router.replace('/404');
                    }
                });
            }
        }
    }

</script>
