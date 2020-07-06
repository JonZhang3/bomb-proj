<template>
    <el-row class="main-content-root"
            style="display: flex;flex-direction: column;height: 100%;padding:20px 0;">
        <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 20px;">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>文档管理</el-breadcrumb-item>
            <el-breadcrumb-item>文件网盘</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top: 10px;flex: 1;display: flex">
            <div style="min-width: 200px;border-right: 1px solid #DCDFE6;height: 100%">
                <list-menu :default-active="activeIndex" router>
                    <list-menu-item :index="`/project/${$route.params.id}/files`">
                        <i class="el-icon-folder"></i>
                        <span>全部文件</span>
                    </list-menu-item>
                    <list-menu-item :index="`/project/${$route.params.id}/files/recent`">
                        <i class="el-icon-time"></i>
                        <span>最近文件</span>
                    </list-menu-item>
                    <list-menu-item :index="`/project/${$route.params.id}/files/starred`">
                        <i class="el-icon-star-off"></i>
                        <span>星标文件</span>
                    </list-menu-item>
                    <list-menu-item :index="`/project/${$route.params.id}/files/shared`">
                        <i class="el-icon-share"></i>
                        <span>分享中的</span>
                    </list-menu-item>
                    <list-menu-item :index="`/project/${$route.params.id}/files/recycle-bin`">
                        <i class="el-icon-receiving"></i>
                        <span>回收站</span>
                    </list-menu-item>
                </list-menu>
            </div>
            <div style="flex: 1;height: 100%;">
                <router-view></router-view>
            </div>
        </el-row>
    </el-row>
</template>

<script>

    import ListMenu from "../../../../../components/listmenu/ListMenu";
    import ListMenuItem from "../../../../../components/listmenu/ListMenuItem";

    export default {
        name: 'project-files',
        components: {
            ListMenu,
            ListMenuItem
        },
        data() {
            return {

            }
        },
        computed: {
            projectName() {
                return this.$store.state.projectName;
            },
            projectId() {
                return this.$store.state.projectId;
            },
            activeIndex() {
                return this.$route.path;
            }
        },
        watch: {
        }
    }

</script>
