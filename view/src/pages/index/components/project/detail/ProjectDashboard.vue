<template>
    <el-row>
        <el-row>
            <el-col :span="24">
                <div>
                    <span style="color: #409EFF;font-size: 20px;">{{projectName}}</span>
                    <el-button size="small">编辑</el-button>
                </div>
            </el-col>
        </el-row>
    </el-row>
</template>

<script>

    export default {
        name: 'project-dashboard',
        data() {
            return {

            }
        },
        computed: {
            projectName() {
                return this.$store.state.projectName;
            }
        }
    }

</script>
