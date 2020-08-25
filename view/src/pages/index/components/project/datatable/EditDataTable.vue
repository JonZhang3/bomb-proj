<template>
    <el-row style="padding: 0 10px" class="project-table-info">
        <el-row style="display: flex;align-items: center;">
            <el-col :span="12">
                <span style="font-size: 14px;color: #606266">编辑表【{{root.currentTableData.tableName}}】</span>
            </el-col>
            <el-col :span="12" style="display: flex;align-items: center; justify-content: flex-end">
                <el-button-group>
                    <el-button icon="el-icon-receiving" @click="handleSaveTable"
                               type="primary" size="mini" round>保存</el-button>
                </el-button-group>
            </el-col>
        </el-row>
        <el-tabs v-model="activeTab" style="flex: 1;overflow: hidden;">
            <el-tab-pane label="基本信息" name="baseInfo">
                <el-form style="width: 40%" label-width="80px">
                    <el-form-item label="表名：">
                        <el-input size="small" v-model="tableData.tableName"></el-input>
                    </el-form-item>
                    <el-form-item label="注释：">
                        <el-input type="textarea" size="small" v-model="tableData.tableDesc"></el-input>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="列信息" name="columnsInfo">
                <div style="height: 100%;display: flex;flex-direction: column;">
                    <edit-table-fields v-model="fields" :field-types="fieldTypeOptions"></edit-table-fields>
                </div>
            </el-tab-pane>
            <el-tab-pane label="索引" name="indexesInfo">
                <div style="height: 100%;display: flex;flex-direction: column;">
                    <edit-table-indexes v-model="indexes" :fields="fields"></edit-table-indexes>
                </div>
            </el-tab-pane>
        </el-tabs>
    </el-row>
</template>

<script>

    import apis from "../../../../../api/apis";

    import EditTableFields from "./EditTableFields";
    import EditTableIndexes from "./EditTableIndexes";

    import utils, {deepCopy} from "../../../../../common/utils";

    export default {
        name: 'EditDataTable',
        inject: ['root'],
        components: {
            EditTableFields,
            EditTableIndexes
        },
        data() {
            return {
                activeTab: 'baseInfo',
                indexesOptions: [],
                fieldTypeOptions: [],
                tableData: deepCopy(this.root.currentTableData),
                fields: [],
                indexes: []
            }
        },
        computed: {
            projectId() {
                return this.$store.state.projectId;
            },
            databaseId() {
                return this.$route.params.dbId;
            },
            tableId() {
                return this.$route.params.tableId;
            }
        },
        mounted() {
            this.init(() => {
                this.getDataTableFields();
            });
        },
        // watch: {
        //     'root.currentTableData'(val) {
        //         this.tableData = val;
        //     }
        // },
        methods: {
            isArray(src) {
                return utils.isArray(src);
            },
            handleTableRowDelete(scope) {
                this.fields.splice(scope.$index, 1);
            },
            init(callback) {
                const loading = this.$loading({fullscreen: true});
                Promise.all([
                    apis.getDataTableIndexes(this.tableData.type, {useLoading: false}),
                    apis.getDataTableFieldTypes(this.tableData.type, {useLoading: false})
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
                apis.getDataTableFields(this.projectId, this.databaseId, this.tableId).then(data => {
                    if(data.code === 1) {
                        this.fields = data.data.fields;
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            handleSaveTable() {
                for(let i = 0, len = this.fields.length; i < len; i++) {
                    const item = this.fields[i];
                    if(!item.fieldName) {
                        this.$message.warning('表列信息 - 表名不能为空');
                        return;
                    }
                }
                for(let i = 0, len = this.indexes.length; i < len; i++) {
                    const item = this.indexes[i];
                    if(!item.indexName) {
                        this.$message.warning('表索引 - 索引名不能为空');
                        return;
                    }
                }
                apis.updateProjectDataTable(this.projectId, this.databaseId, this.tableId, {
                    tableName: '',
                    tableDesc: '',
                    fields: JSON.stringify(this.fields, (key, value) => {
                        return value;
                    }),
                    indexes: JSON.stringify(this.indexes, (key, value) => {
                        return value;
                    })
                }).then(data => {
                    if(data.code === 1) {
                        this.$message.success('保存成功');
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            saveDataTableFields() {
                if(this.fields.length <= 0) {
                    this.$message.warning('请添加至少一个字段');
                } else {
                    this.$refs['fieldsTable'].valid(() => {
                        apis.saveDataTableFields(this.projectId, this.tableId, {
                            fields: JSON.stringify(this.fields, (key, value) => {
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
