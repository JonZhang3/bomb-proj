<template>
    <el-input size="small" v-bind="$attrs" class="scoped-search-input">
        <el-button slot="suffix" type="primary" size="small">搜索</el-button>
    </el-input>
</template>

<script>

    export default {
        name: 'search-input',
        props: {

        },
        data() {
            return {

            }
        }
    }

</script>

<style scoped>
    .scoped-search-input .el-input__suffix {
        right: 0;
    }
</style>
