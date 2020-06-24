<template>
    <el-row>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>数据库表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row>
            <el-col :span="6">
                <list-menu></list-menu>
            </el-col>
        </el-row>
        <el-row style="margin-top: 10px;">
            <draggable-table v-model="fields" draggable-element=".drag-ele"
                             :default-data="defaultFieldData">
                <el-table-column label="字段名">
                    <template slot-scope="scope">
                        <el-input placeholder="必填" v-model="scope.row.fieldName"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="类型">
                    <template slot-scope="scope">
                        <el-input placeholder="必填" v-model="scope.row.type"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="长度" width="160">
                    <template slot-scope="scope">
                        <el-input-number v-model="scope.row.length" :precision="0"
                                         size="small" @blur="handleTableRowLengthBlur(scope.row)"
                                         controls-position="right" :min="0" :max="65532"></el-input-number>
                    </template>
                </el-table-column>
                <el-table-column width="50" label="非空">
                    <template slot-scope="scope">
                        <el-checkbox v-model="scope.row.notNull"></el-checkbox>
                    </template>
                </el-table-column>
                <el-table-column label="默认值">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.defaultValue"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="索引">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.defaultValue"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="注释">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.notes"></el-input>
                    </template>
                </el-table-column>
                <el-table-column width="100" label="操作">
                    <template slot-scope="scope">
                        <el-button class="drag-ele" icon="el-icon-rank" circle size="small" style="cursor: move"></el-button>
                        <el-button @click="handleTableRowDelete(scope)" type="danger" icon="el-icon-delete" circle size="small"></el-button>
                    </template>
                </el-table-column>
            </draggable-table>
        </el-row>
    </el-row>
</template>

<script>

    import DraggableTable from "../../../../../components/DraggableTable";
    import ListMenu from "../../../../../components/ListMenu";

    export default {
        name: 'project-datatable',
        components: {
            DraggableTable,
            ListMenu
        },
        data() {
            return {
                fields: [
                    {index: 0, fieldName: '', type: '', length: 0, notNull: false, defaultValue: '', notes: ''}
                ],
                defaultFieldData: {
                    fieldName: '',
                    type: '',
                    length: 0,
                    notNull: false,
                    defaultValue: '',
                    notes: ''
                }
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
        methods: {
            handleTableRowDelete(scope) {
                this.fields.splice(scope.$index, 1);
            },
            handleTableRowLengthBlur(row) {
                if(!row.length) {
                    row.length = 0;
                }
            }
        }
    }

</script>
