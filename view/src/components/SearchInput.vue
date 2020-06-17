<template>
    <el-input :size="size" v-bind="$attrs" class="scoped-search-input" v-model="searchText">
        <el-button slot="suffix" type="primary"
                   @click="handleClick"
                   size="small" :style="buttonStyle">{{buttonText}}</el-button>
    </el-input>
</template>

<script>

    export default {
        name: 'search-input',
        props: {
            buttonText: String,
            size: {
                type: String,
                default: 'small'
            },
            buttonStyle: {
                type: Object,
                default: () => {return {}}
            }
        },
        data() {
            return {
                searchText: ''
            }
        },
        methods: {
            handleClick(evt) {
                this.$emit('search', evt);
            }
        }
    }

</script>

<style scoped>
    .scoped-search-input >>> .el-input__suffix {
        right: 0;
    }

    .scoped-search-input >>> .el-button--primary {
        border: 1px solid transparent;
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
    }

</style>
