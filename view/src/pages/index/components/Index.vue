<template>
    <el-container style="position: relative;bottom: 0;overflow-y: hidden">
        <el-aside width="200px">
            <el-menu default-active="/"
                     background-color="#303543"
                     text-color="#fff"
                     active-text-color="#409EFF"
                     :router="true"
                     style="height: 100%;">
                <el-menu-item index="/">
                    <template slot="title">
                        <i class="el-icon-s-cooperation"></i>
                        <span>项目管理</span>
                    </template>
                </el-menu-item>
                <el-menu-item index="2">
                    <template slot="title">
                        <i class="el-icon-s-custom"></i>
                        <span>个人资料</span>
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
        name: 'index'
    }

</script>
