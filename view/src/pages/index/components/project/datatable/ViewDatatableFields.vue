<template>
    <el-row>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>数据库表</el-breadcrumb-item>
            <el-breadcrumb-item>查看【{{tableName}}】</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top: 10px;">
            <el-row style="margin-top: 10px;">
                <el-col :span="12">
                    <el-button-group>
                        <el-button @click="handleBack" icon="el-icon-arrow-left" size="mini" round>返回</el-button>
                        <el-button icon="el-icon-refresh" size="mini" round>刷新</el-button>
                    </el-button-group>
                </el-col>
                <el-col :span="12" style="text-align: right">
                    <search-input style="width: 40%;" v-model="searchText"
                                  placeholder="字段名" button-text="搜索" @search="handleSearch"></search-input>
                </el-col>
            </el-row>
        </el-row>
        <el-row style="margin-top: 10px;">
            <el-table :data="fields" stripe>

            </el-table>
        </el-row>
    </el-row>
</template>

<script>

    import apis from "../../../../../api/apis";

    import SearchInput from "../../../../../components/SearchInput";

    export default {
        name: 'view-datatable-fields',
        components: {
            SearchInput
        },
        data() {
            return {
                searchText: '',
                fields: []
            }
        },
        computed: {
            projectName() {
                return this.$store.state.projectName;
            },
            projectId() {
                return this.$store.state.projectId;
            },
            tableId() {
                return this.$route.params.tableId;
            },
            tableName() {
                return this.$route.params.tableName
            },
            dbType() {
                return this.$route.params.type;
            }
        },
        mounted() {
            if(!this.tableName) {
                this.handleBack();
            }
        },
        methods: {
            handleSearch() {

            },
            handleBack() {
                this.$router.replace({path: `/project/${this.projectId}/datatable`})
            }
        }
    }

</script>
