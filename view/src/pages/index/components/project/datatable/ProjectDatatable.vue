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
            <draggable-table v-model="formData.fields" draggable-element=".drag-ele"
                             :form-data="formData"
                             :row-style="(data) => {return data.row.marker ? {backgroundColor: data.row.marker} : {}}"
                             :default-data="defaultFieldData">
                <el-table-column label="字段名">
                    <template slot-scope="scope">
                        <el-form-item :prop="'fields.' + scope.$index + '.fieldName'" :rules="formData.rules.fieldName">
                            <el-input placeholder="必填" size="small" v-model="scope.row.fieldName"></el-input>
                        </el-form-item>
                    </template>
                </el-table-column>
                <el-table-column label="类型">
                    <template slot-scope="scope">
                        <el-form-item :prop="'fields.' + scope.$index + '.type'" :rules="formData.rules.type">
                            <el-input placeholder="必填" size="small" v-model="scope.row.type"></el-input>
                        </el-form-item>
                    </template>
                </el-table-column>
                <el-table-column label="长度" width="160">
                    <template slot-scope="scope">
                        <el-form-item :prop="'fields.' + scope.$index + '.length'" :rules="formData.rules.length">
                            <el-input-number v-model="scope.row.length" :precision="0"
                                             size="small" @blur="handleTableRowLengthBlur(scope.row)"
                                             controls-position="right" :min="0" :max="65532"></el-input-number>
                        </el-form-item>
                    </template>
                </el-table-column>
                <el-table-column width="50" label="非空">
                    <template slot-scope="scope">
                        <el-form-item>
                            <el-checkbox v-model="scope.row.notNull" size="small"></el-checkbox>
                        </el-form-item>
                    </template>
                </el-table-column>
                <el-table-column label="默认值">
                    <template slot-scope="scope">
                        <el-form-item>
                            <el-input size="small" v-model="scope.row.defaultValue"></el-input>
                        </el-form-item>
                    </template>
                </el-table-column>
                <el-table-column label="索引（Type/Method）">
                    <template slot-scope="scope">
                        <el-form-item>
                            <el-cascader size="small" v-model="scope.row.indexes" :options="indexesOptions"></el-cascader>
                        </el-form-item>
                    </template>
                </el-table-column>
                <el-table-column label="索引名称">
                    <template slot-scope="scope">
                        <el-form-item>
                            <el-input size="small" v-model="scope.row.notes"></el-input>
                        </el-form-item>
                    </template>
                </el-table-column>
                <el-table-column label="注释">
                    <template slot-scope="scope">
                        <el-form-item>
                            <el-input size="small" v-model="scope.row.notes"></el-input>
                        </el-form-item>
                    </template>
                </el-table-column>
                <el-table-column width="100" label="操作">
                    <template slot-scope="scope">
                        <el-button class="drag-ele" icon="el-icon-rank" circle size="mini" style="cursor: move"></el-button>
<!--                        <el-color-picker v-model="scope.row.marker"></el-color-picker>-->
                        <el-button @click="handleTableRowDelete(scope)" type="danger" icon="el-icon-delete" circle size="mini"></el-button>
                    </template>
                </el-table-column>
            </draggable-table>
        </el-row>
    </el-row>
</template>

<script>
    // #E5F2FF
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
                formData: {
                    fields: [
                        {index: 0, fieldName: '', type: '', length: 0, notNull: false, defaultValue: '', notes: '', indexes: '', indexesName: '', marker: ''}
                    ],
                    rules: {
                        fieldName: [
                            {required: true, message: '请输入字段名', trigger: 'blur'},
                        ],
                        type: [
                            {required: true, message: '请选择字段类型', trigger: 'blur'},
                        ],
                        length: [

                        ]
                    }
                },

                defaultFieldData: {
                    fieldName: '',
                    type: '',
                    length: 0,
                    notNull: false,
                    defaultValue: '',
                    notes: '',
                    indexes: '',
                    indexesName: '',
                    marker: ''
                },
                indexesOptions: [
                    {value: 'PRIMARY', label: 'PRIMARY KEY'},
                    {value: 'FULLTEXT', label: 'FULLTEXT', children: [{value: 'BTREE', label: 'BTREE'}, {value: 'HASH', label: 'HASH'}]},
                    {value: 'NORMAL', label: 'NORMAL', children: [{value: 'BTREE', label: 'BTREE'}, {value: 'HASH', label: 'HASH'}]},
                    {value: 'SPATIAL', label: 'SPATIAL', children: [{value: 'BTREE', label: 'BTREE'}, {value: 'HASH', label: 'HASH'}]},
                    {value: 'UNIQUE', label: 'UNIQUE', children: [{value: 'BTREE', label: 'BTREE'}, {value: 'HASH', label: 'HASH'}]}
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
