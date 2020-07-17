<template>
    <el-container style="position: relative;bottom: 0;overflow-y: hidden">
        <el-aside width="auto" style="display: flex;flex-direction: column;">
            <el-menu :default-active="activeIndex"
                     background-color="#303543"
                     text-color="#fff"
                     active-text-color="#409EFF"
                     :router="true"
                     class="menu-can-collapse"
                     :collapse="menuCollapse"
                     style="position: relative; flex: 1;overflow-y: auto;overflow-x: hidden;">
                <el-menu-item index="/">
                    <i class="el-icon-arrow-left"></i>
                    <span slot="title">返回</span>
                </el-menu-item>
                <el-menu-item :index="`/project/${$route.params.id}`">
                    <i class="el-icon-s-home"></i>
                    <span slot="title">项目详情</span>
                </el-menu-item>
                <el-menu-item :index="`/project/${$route.params.id}/member`">
                    <i class="el-icon-user-solid"></i>
                    <span slot="title">项目成员</span>
                </el-menu-item>
                <el-menu-item :index="`/project/${$route.params.id}/code`">
                    <i class="el-icon-error"></i>
                    <span slot="title">状态码</span>
                </el-menu-item>
                <el-menu-item :index="`/project/${$route.params.id}/datatable`">
                    <i class="el-icon-s-grid"></i>
                    <span slot="title">数据库</span>
                </el-menu-item>
                <el-submenu index="1">
                    <template slot="title">
                        <i class="el-icon-location"></i>
                        <span>文档管理</span>
                    </template>
                    <el-menu-item :index="`/project/${$route.params.id}/files`">
                        <i class="el-icon-s-grid"></i>
                        <span slot="title">文件网盘</span>
                    </el-menu-item>
                </el-submenu>
            </el-menu>
            <div class="menu-collapse-root" @click="menuCollapse = !menuCollapse">
                <i :class="menuCollapse ? 'el-icon-arrow-right' : 'el-icon-arrow-left'"></i>
            </div>
        </el-aside>
        <el-main style="overflow-y: auto;padding: 0;">
            <router-view></router-view>
        </el-main>
    </el-container>
</template>

<script>

    import apis from "../../../../api/apis";

    export default {
        name: 'project-detail',
        props: {
            projectName: String
        },
        data() {
            return {
                activeIndex: '',
                menuCollapse: false
            }
        },
        computed: {
        },
        beforeMount() {
            this.$store.commit('setProjectId', this.$route.params.id);
            this.projectDetail();
        },
        mounted() {
            if(this.$route.meta.rootMenu) {
                this.activeIndex = this.$route.meta.rootMenu(this.$route.params.id);
            } else {
                this.activeIndex = this.$route.path;
            }
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
        },
        watch: {
            '$route.path'(val) {
                if(!this.$route.meta.hasParent) {
                    this.activeIndex = val;
                } else if(this.$route.meta.rootMenu) {
                    this.activeIndex = this.$route.meta.rootMenu(this.$route.params.id);
                }
            }
        }
    }

</script>

<style>

    .menu-can-collapse:not(.el-menu--collapse) {
        width: 200px;
    }

    .menu-collapse-root {
        /*position: fixed;*/
        /*bottom: 0;*/
        /*left: 0;*/
        /*right: 0;*/
        background-color: #1c1e26;
        color: #fff;
        text-align: center;
        padding: 6px 0;
        cursor: pointer;
        border-right: 1px solid #303543;
    }
</style>
