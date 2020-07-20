<template>
    <el-dialog width="30%" :title="edit ? '修改数据库' : '新增数据库'" v-bind="$attrs"
               class="add-project-member-dialog"
               :before-close="handleCancel" :close-on-click-modal="false">
        <el-form :model="dbForm" ref="dbForm" :rules="dbFormRules" label-width="100px">
            <el-form-item prop="databaseName" label="数据库名">
                <el-input v-model="dbForm.databaseName"></el-input>
            </el-form-item>
            <el-form-item prop="databaseDesc" label="描述">
                <el-input v-model="dbForm.databaseDesc"></el-input>
            </el-form-item>
            <el-form-item prop="type" label="数据库类型">
                <el-select v-model="dbForm.type" placeholder="请选择" style="width: 100%;">
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
        name: 'add-or-edit-project-database-dialog',
        props: {
            projectId: String,
            edit: {
                type: Boolean,
                default: false
            },
            dbData: Object
        },
        data() {
            return {
                dbForm: {
                    id: '',
                    databaseName: '',
                    databaseDesc: '',
                    type: ''
                },
                dbFormRules: {
                    databaseName: [
                        {required: true, message: '请输入数据库名', trigger: 'blur'},
                        {max: 50, message: '数据库名最大长度为 50', trigger: 'blur'}
                    ],
                    databaseDesc: [
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
                this.$refs['dbForm'].resetFields();
                this.$emit('cancel');
            },
            handleAdd(e) {
                this.$refs['dbForm'].validate(valid => {
                    if(valid) {
                        if(this.edit) {
                            this.updateProjectDatabase(e);
                        } else {
                            this.addProjectDatabase(e);
                        }
                    } else {
                        return false;
                    }
                });
            },
            addProjectDatabase(e) {
                apis.addProjectDatabase(this.projectId, {
                    databaseName: this.dbForm.databaseName,
                    databaseDesc: this.dbForm.databaseDesc,
                    type: this.dbForm.type
                }).then(data => {
                    if(data.code === 1) {
                        this.$message.success('新增数据库成功');
                        this.$refs['dbForm'].resetFields();
                        this.$emit('success', e);
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            updateProjectDatabase(e) {
                apis.updateProjectDatabase(this.projectId, this.dbForm.id, {
                    databaseName: this.dbForm.databaseName,
                    databaseDesc: this.dbForm.databaseDesc,
                    type: this.dbForm.type
                }).then(data => {
                    if(data.code === 1) {
                        this.$message.success('修改数据库成功');
                        this.$refs['dbForm'].resetFields();
                        this.$emit('success', e);
                    } else {
                        this.$message.error(data.message);
                    }
                });
            }
        },
        watch: {
            'dbData.id'(val) {
                this.dbForm.id = val ? val : '';
            },
            'dbData.databaseName'(val) {
                this.dbForm.databaseName = val ? val : '';
            },
            'dbData.databaseDesc'(val) {
                this.dbForm.databaseDesc = val ? val : '';
            },
            'dbData.type'(val) {
                this.dbForm.type = val ? val : '';
            }
        }
    }

</script>
