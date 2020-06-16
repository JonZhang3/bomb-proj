<template>
    <div id="app" style="height: 100%;overflow-y: hidden">
        <el-container style="height: 100%;">
            <el-header class="app-header-root">
                <div class="app-header-title-root">
                    <img style="width: 30px;height: 30px;" src="../../assets/logo.png" alt="logo">
                    <div style="display: flex;flex-direction: column;justify-content: center; padding-left: 5px;font-size: 18px;">
                        <span style="color: #409EFF;">Bomb.proj</span>
                        <span style="font-size: 10px;color: #fff;">项目/文档/BUG管理系统</span>
                    </div>
                </div>
                <div class="app-header-operation-root" style="color: #fff;">
                    <img style="width: 30px;height: 30px;border-radius: 50%;"
                         src="../../assets/avatar/avatar0.jpg" alt="avatar">
                    <p style="font-size: 14px;padding-left: 5px;">用户名</p>
                    <el-popover trigger="hover" width="200"
                                :visible-arrow="false"
                                popper-class="app-header-operation-list-root">
                        <div style="height: 100%;"></div>
                        <template slot="reference">
                            <i class="el-icon-arrow-down" style="padding-left: 5px;font-size: 14px;cursor: pointer;" ></i>
                        </template>
                    </el-popover>
                </div>
            </el-header>
            <router-view></router-view>
        </el-container>
    </div>
</template>

<script>

    export default {
        name: 'app'
    }
</script>
