<template>
    <el-row class="main-content-root">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
            <el-breadcrumb-item>数据库表</el-breadcrumb-item>
            <el-breadcrumb-item>编辑【{{tableName}}】</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top: 10px;">
            <el-row style="margin-top: 10px;">
                <el-col :span="12">
                    <el-button-group>
                        <el-button @click="handleBack" icon="el-icon-arrow-left" size="mini" round>返回</el-button>
                        <el-button icon="el-icon-receiving"
                                   @click="saveDataTableFields"
                                   type="primary" size="mini" round>保存</el-button>
                    </el-button-group>
                </el-col>
<!--                <el-col :span="12" style="text-align: right">-->
<!--                    <search-input style="width: 40%;" v-model="searchText"-->
<!--                                  placeholder="字段名" button-text="搜索" @search="handleSearch"></search-input>-->
<!--                </el-col>-->
            </el-row>
        </el-row>
        <el-row style="margin-top: 10px;">
            <el-tabs>
                <el-tab-pane label="列信息" name="first">
                    <draggable-table v-model="formData.fields"
                                     draggable-element=".drag-ele"
                                     :form-data="formData" ref="fieldsTable" size="small"
                                     :row-style="(data) => {return data.row.marker ? {backgroundColor: data.row.marker} : {}}"
                                     :default-data="defaultFieldData">
                        <el-table-column label="列名">
                            <template slot-scope="scope">
                                <el-form-item :prop="'fields.' + scope.$index + '.fieldName'" :rules="formData.rules.fieldName">
                                    <el-input placeholder="必填" size="small" v-model="scope.row.fieldName"></el-input>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="类型">
                            <template slot-scope="scope">
                                <el-form-item :prop="'fields.' + scope.$index + '.type'" :rules="formData.rules.type">
                                    <!--                            <el-input placeholder="必填" size="small" v-model="scope.row.type"></el-input>-->
                                    <el-select placeholder="请选择字段类型"
                                               filterable size="small" v-model="scope.row.type">
                                        <el-option v-for="item in fieldTypeOptions" :key="item" :value="item" :label="item"></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="长度" width="160">
                            <template slot-scope="scope">
                                <el-form-item :prop="'fields.' + scope.$index + '.length'" :rules="formData.rules.length">
                                    <el-input-number v-model="scope.row.length" :precision="0"
                                                     size="mini" @blur="handleTableRowLengthBlur(scope.row)"
                                                     controls-position="right" :min="0" :max="65532"></el-input-number>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column width="50" label="非空">
                            <template slot-scope="scope">
                                <el-form-item style="text-align: center;">
                                    <el-checkbox v-model="scope.row.notNull" size="small" true-label="1" false-label="0"></el-checkbox>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column width="50" label="主键">
                            <template slot-scope="scope">
                                <el-form-item style="text-align: center;">
                                    <el-checkbox v-model="scope.row.pk" size="small" true-label="1" false-label="0"></el-checkbox>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column width="50" label="自增">
                            <template slot-scope="scope">
                                <el-form-item style="text-align: center;">
                                    <el-checkbox v-model="scope.row.autoIncrement" size="small" true-label="1" false-label="0"></el-checkbox>
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
                        <el-table-column label="索引" min-width="120">
                            <template slot-scope="scope">
                                <el-form-item>
                                    <el-cascader size="small" v-model="scope.row.indexes" :clearable="true" :options="indexesOptions"></el-cascader>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="索引名称">
                            <template slot-scope="scope">
                                <el-form-item>
                                    <el-input size="small" v-model="scope.row.indexesName"></el-input>
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
                </el-tab-pane>
                <el-tab-pane label="索引" name="second">索引</el-tab-pane>
                <el-tab-pane label="外键" name="third">外键</el-tab-pane>
            </el-tabs>
        </el-row>
    </el-row>
</template>

<script>

    import apis from "../../../../../api/apis";

    import DraggableTable from "../../../../../components/DraggableTable";
    import SearchInput from "../../../../../components/SearchInput";
    import utils from "../../../../../common/utils";

    export default {
        name: 'edit-datatable-fields',
        components: {
            DraggableTable,
            SearchInput
        },
        data() {
            return {
                searchText: '',
                formData: {
                    fields: [
                        {index: 0, fieldName: '', type: '', length: 0, notNull: '0', pk: '0', autoIncrement: '0', defaultValue: '', notes: '', indexes: '', indexesName: '', marker: ''}
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
                    notNull: '0',
                    pk: '0',
                    autoIncrement: '0',
                    defaultValue: '',
                    notes: '',
                    indexes: '',
                    indexesName: '',
                    marker: ''
                },
                indexesOptions: [],
                fieldTypeOptions: []
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
                return;
            }
            this.init(() => {
                this.getDataTableFields();
            });
        },
        methods: {
            isArray(src) {
                return utils.isArray(src);
            },
            handleBack() {
                this.$router.replace({path: `/project/${this.projectId}/datatable`})
            },
            handleTableRowDelete(scope) {
                this.formData.fields.splice(scope.$index, 1);
            },
            handleTableRowLengthBlur(row) {
                if(!row.length) {
                    row.length = 0;
                }
            },
            init(callback) {
                const loading = this.$loading({fullscreen: true});
                Promise.all([
                    apis.getDataTableIndexes(this.dbType, {useLoading: false}),
                    apis.getDataTableFieldTypes(this.dbType, {useLoading: false})
                ]).then(datas => {
                    loading.close();
                    if(datas && datas.length === 2) {
                        let initSuccess = true;
                        if(datas[0].code === 1) {
                            this.indexesOptions = datas[0].data;
                        } else {
                            initSuccess = false;
                            this.$message.error(datas[0].message);
                        }
                        if(datas[1].code === 1) {
                            this.fieldTypeOptions = datas[1].data;
                        } else {
                            initSuccess = false;
                            this.$message.error(datas[1].message);
                        }
                        if(initSuccess) {
                            callback && callback();
                        }
                    } else {
                        this.$message.error("获取初始化信息失败");
                    }
                });
            },
            getDataTableFields() {
                apis.getDataTableFields(this.projectId, this.tableId).then(data => {
                    if(data.code === 1) {
                        this.formData.fields = data.data.fields;
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            saveDataTableFields() {
                if(this.formData.fields.length <= 0) {
                    this.$message.warning('请添加至少一个字段');
                } else {
                    this.$refs['fieldsTable'].valid(() => {
                        apis.saveDataTableFields(this.projectId, this.tableId, {
                            fields: JSON.stringify(this.formData.fields, (key, value) => {
                                if(key === 'indexes' && utils.isArray(value)) {
                                    return value.join(',');
                                }
                                return value;
                            })
                        }).then(data => {
                            if(data.code === 1) {
                                this.$message.success('保存成功');
                            } else {
                                this.$message.error(data.message);
                            }
                        });
                    });
                }
            }
        }
    }

</script>
