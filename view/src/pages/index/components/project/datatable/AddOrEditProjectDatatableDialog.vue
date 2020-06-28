<template>
    <el-dialog width="30%" :title="edit ? '修改数据库表' : '新增数据库表'" v-bind="$attrs"
               class="add-project-member-dialog"
               :before-close="handleCancel" :close-on-click-modal="false">
        <el-form :model="tableForm" ref="tableForm" :rules="tableFormRules" label-width="100px">
            <el-form-item prop="tableName" label="表名">
                <el-input v-model="tableForm.tableName"></el-input>
            </el-form-item>
            <el-form-item prop="tableDesc" label="描述">
                <el-input v-model="tableForm.tableDesc"></el-input>
            </el-form-item>
            <el-form-item prop="type" label="数据库类型">
                <el-select v-model="tableForm.type" placeholder="请选择" style="width: 100%;">
                    <el-option key="mysql" value="mysql" label="MySQL"></el-option>
                    <el-option key="oracle" value="oracle" label="Oracle"></el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleAdd">{{edit ? '修 改' : '增 加'}}</el-button>
        </span>
    </el-dialog>
</template>

<script>

    import apis from "../../../../../api/apis";

    export default {
        name: 'add-or-edit-project-datatable-dialog',
        props: {
            projectId: String,
            edit: {
                type: Boolean,
                default: false
            },
            tableData: Object
        },
        data() {
            return {
                tableForm: {
                    id: '',
                    tableName: '',
                    tableDesc: '',
                    type: ''
                },
                tableFormRules: {
                    tableName: [
                        {required: true, message: '请输入表名', trigger: 'blur'},
                        {max: 50, message: '表名最大长度为 50', trigger: 'blur'}
                    ],
                    tableDesc: [
                        {max: 255, message: '描述最大长度为 255', trigger: 'blur'}
                    ],
                    type: [
                        {required: true, message: '请选择数据库类型', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            handleCancel() {
                this.$refs['tableForm'].resetFields();
                this.$emit('cancel');
            },
            handleAdd(e) {
                this.$refs['tableForm'].validate(valid => {
                    if(valid) {
                        if(this.edit) {
                            this.updateProjectDataTable(e);
                        } else {
                            this.addProjectDataTable(e);
                        }
                    } else {
                        return false;
                    }
                });
            },
            addProjectDataTable(e) {
                apis.addProjectDataTable(this.projectId, {
                    tableName: this.tableForm.tableName,
                    tableDesc: this.tableForm.tableDesc,
                    type: this.tableForm.type
                }).then(data => {
                    if(data.code === 1) {
                        this.$message.success('新增数据库表成功');
                        this.$refs['tableForm'].resetFields();
                        this.$emit('success', e);
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            updateProjectDataTable(e) {
                apis.updateProjectDataTable(this.projectId, this.tableForm.id, {
                    tableName: this.tableForm.tableName,
                    tableDesc: this.tableForm.tableDesc,
                    type: this.tableForm.type
                }).then(data => {
                    if(data.code === 1) {
                        this.$message.success('修改数据库表成功');
                        this.$refs['tableForm'].resetFields();
                        this.$emit('success', e);
                    } else {
                        this.$message.error(data.message);
                    }
                });
            }
        },
        watch: {
            'tableData.id'(val) {
                this.tableForm.id = val ? val : '';
            },
            'tableData.tableName'(val) {
                this.tableForm.tableName = val ? val : '';
            },
            'tableData.tableDesc'(val) {
                this.tableForm.tableDesc = val ? val : '';
            },
            'tableData.type'(val) {
                this.tableForm.type = val ? val : '';
            }
        }
    }

</script>
