<template>
    <div style="background-color: #ffffff">
        <el-row type="flex" justify="space-between">
            <el-col :span="6">
                <el-radio-group v-model="projectType" size="small">
                    <el-radio-button label="我的"></el-radio-button>
                    <el-radio-button label="我创建的"></el-radio-button>
                    <el-radio-button label="我加入的"></el-radio-button>
                </el-radio-group>
            </el-col>
            <el-col :span="6">
                <search-input placeholder="项目名称" button-text="搜索" style="width: 60%;"></search-input>
            </el-col>
        </el-row>
    </div>
</template>

<script>

    import SearchInput from "../../../components/SearchInput";

    export default {
        name: 'projects',
        components: {
            SearchInput
        },
        data() {
            return {
                projectType: '我的'
            }
        }
    }

</script>
