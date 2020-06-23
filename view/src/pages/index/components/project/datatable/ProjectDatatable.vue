<template>
    <el-row>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>数据库表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top: 10px;">
            <draggable-table v-model="fileds">
                <el-table-column label="字段名">
                    <template slot-scope="scope">
                        <el-input placeholder="字段名" v-model="scope.row.fieldName"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="类型">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.type"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="非空">
                    <template slot-scope="scope">
                        <el-checkbox v-model="scope.row.notNull"></el-checkbox>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button icon="el-icon-rank" circle size="small" style="cursor: cell"></el-button>
                        <el-button type="danger" icon="el-icon-delete" circle size="small"></el-button>
                    </template>
                </el-table-column>
            </draggable-table>
        </el-row>
    </el-row>
</template>

<script>

    import DraggableTable from "../../../../../components/draggabletable/DraggableTable";

    export default {
        name: 'project-datatable',
        components: {
            DraggableTable
        },
        data() {
            return {
                fileds: [
                    {fieldName: '1', type: '2', notNull: false}
                ]
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
