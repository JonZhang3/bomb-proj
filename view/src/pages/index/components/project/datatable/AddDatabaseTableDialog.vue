<template>
    <el-dialog width="30%" title="添加数据库表" v-bind="$attrs"
               :before-close="handleCancel" :close-on-click-modal="false">
        <el-form ref="form" :model="form" :rules="formRules" label-width="100px" size="medium">
            <el-form-item prop="tableName" label="表名">
                <el-input v-model="form.tableName"></el-input>
            </el-form-item>
            <el-form-item prop="tableDesc" label="描述">
                <el-input v-model="form.tableDesc"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleAdd">增 加</el-button>
        </span>
    </el-dialog>
</template>

<script>

    import apis from "../../../../../api/apis";

    export default {
        name: 'AddDatabaseTableDialog',
        props: {
            projectId: String,
            databaseId: String
        },
        data() {
            return {
                form: {
                    tableName: '',
                    tableDesc: ''
                },
                formRules: {
                    tableName: [
                        {required: true, message: '请输入数据库表名', trigger: 'blur'},
                        {max: 50, message: '表名最大长度为 50', trigger: 'blur'}
                    ],
                    tableDesc: [
                        {max: 255, message: '描述最大长度为 255', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            handleCancel() {
                this.$refs['form'].resetFields();
                this.$emit('cancel');
            },
            handleAdd(e) {
                this.$refs['form'].validate(valid => {
                    if(valid) {
                        apis.addProjectDataTable(this.projectId, this.databaseId, {
                            tableName: this.form.tableName,
                            tableDesc: this.form.tableDesc
                        }).then(data => {
                            if(data.code === 1) {
                                this.$message.success('新增数据库表成功');
                                this.$refs['form'].resetFields();
                                this.$emit('success', {
                                    tableId: data.data,
                                    tableName: this.form.tableName
                                });
                            } else {
                                this.$message.error(data.message);
                            }
                        });
                    } else {
                        return false;
                    }
                });
            }
        }
    }

</script>
