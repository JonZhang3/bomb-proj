<template>
    <el-dialog width="30%" title="添加状态码" v-bind="$attrs" :before-close="handleCancel" :close-on-click-modal="false">
        <el-form :model="addCodeForm" :rules="addCodeFormRules" ref="addCodeForm" label-width="80px">
            <el-form-item label="状态码" prop="code">
                <el-input v-model="addCodeForm.code"></el-input>
            </el-form-item>
            <el-form-item label="描述" prop="message">
                <el-input v-model="addCodeForm.message"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleAdd">新 增</el-button>
        </span>
    </el-dialog>
</template>

<script>

    import apis from "../../../../../api/apis";

    export default {
        name: 'add-project-code-dialog',
        data() {
            return {
                addCodeForm: {
                    code: '',
                    message: ''
                },
                addCodeFormRules: {
                    code: [
                        {required: true, message: '请输入状态码', trigger: 'blur'},
                        {max: 50, message: '状态码最大长度为 50', trigger: 'blur'}
                    ],
                    message: [
                        {required: true, message: '请输入描述', trigger: 'blur'},
                        {max: 50, message: '描述最大长度为 50', trigger: 'blur'}
                    ]
                }
            };
        },
        computed: {
            projectId() {
                return this.$store.state.projectId;
            }
        },
        methods: {
            handleCancel() {
                this.$refs['addCodeForm'].resetFields();
                this.$emit('cancel');
            },
            handleAdd(e) {
                this.$refs['addCodeForm'].validate(valid => {
                    if(valid) {
                        apis.addProjectCode(this.projectId, {
                            code: this.addCodeForm.code,
                            message: this.addCodeForm.message
                        }).then(data => {
                            if(data.code === 1) {
                                this.$message.success('新增状态码成功');
                                this.$refs['addCodeForm'].resetFields();
                                this.$emit('added', e);
                            } else {
                                this.$message.error(data.message);
                            }
                        })
                    } else {
                        return false;
                    }
                });
            }
        }
    }

</script>
