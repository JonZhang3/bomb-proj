<template>
    <el-row>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>状态码</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top: 10px;">
            <el-col :span="12">
                <el-button-group>
                    <el-button
                               icon="el-icon-refresh" size="mini" round>刷新</el-button>
                    <el-button type="primary"
                               icon="el-icon-plus" size="mini" round>新增状态码</el-button>
                </el-button-group>
            </el-col>
        </el-row>
        <el-row style="margin-top: 10px;">
            <el-table stripe style="width: 100%;" :data="tableData">
                <el-table-column prop="nickName" label="状态码"></el-table-column>
                <el-table-column prop="email" label="描述"></el-table-column>
                <el-table-column prop="email" label="创建时间"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-tooltip effect="dark" content="编辑" placement="top">
                            <el-button @click="" icon="el-icon-edit" circle size="small"></el-button>
                        </el-tooltip>
                        <el-tooltip effect="dark" content="删除" placement="top">
                            <el-button type="danger" icon="el-icon-delete" circle size="small"></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <el-row style="text-align: right;margin-top: 10px;">
            <el-pagination background layout="prev,pager,next"
                           :page-size="pager.pageSize"
                           :current-page="pager.page" :total="pager.total"></el-pagination>
        </el-row>
    </el-row>
</template>

<script>

    export default {
        name: 'project-code',
        data() {
            return {
                tableData: [],
                pager: {
                    pageSize: 0,
                    page: 1,
                    total: 0
                },
            }
        },
        computed: {
            projectName() {
                return this.$store.state.projectName;
            },
            projectId() {
                return this.$store.state.projectId;
            }
        },
    }

</script>
