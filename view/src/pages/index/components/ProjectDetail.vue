<template>
    <el-container style="position: relative;bottom: 0;overflow-y: hidden">
        <el-aside width="200px">
            <el-menu :default-active="`/project/${$route.params.id}`"
                     background-color="#303543"
                     text-color="#fff"
                     active-text-color="#409EFF"
                     :router="true"
                     style="height: 100%;">
                <el-menu-item :index="`/project/${$route.params.id}`">
                    <template slot="title">
                        <i class="el-icon-s-home"></i>
                        <span>项目详情</span>
                    </template>
                </el-menu-item>
                <el-menu-item index="/project/ds/ds">
                    <template slot="title">
                        <i class="el-icon-error"></i>
                        <span>状态码</span>
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

    export default {
        name: 'project-detail'
    }

</script>
