<template>
    <el-row style="padding: 0 10px">
        <el-tabs v-model="activeTab">
            <el-tab-pane label="列信息" name="columnsInfo">

            </el-tab-pane>
            <el-tab-pane label="索引" name="indexesInfo">索引</el-tab-pane>
        </el-tabs>
    </el-row>
</template>

<script>

    export default {
        name: 'ViewDataTable',
        data() {
            return {
                activeTab: 'columnsInfo'
            }
        }
    }

</script>

<style scoped>
    .el-tabs__item {
        height: 30px;
        line-height: 30px;
    }
</style>
