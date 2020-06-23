<template>
    <el-dialog width="30%" title="添加状态码" v-bind="$attrs" :before-close="handleCancel" :close-on-click-modal="false">
        <el-form :model="editCodeForm" :rules="editCodeFormRules" ref="editCodeForm" label-width="80px">
            <el-form-item label="状态码" prop="code">
                <el-input v-model="editCodeForm.code"></el-input>
            </el-form-item>
            <el-form-item label="描述" prop="message">
                <el-input v-model="editCodeForm.message"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleEdit">更 新</el-button>
        </span>
    </el-dialog>
</template>

<script>

    import apis from "../../../../../api/apis";

    export default {
        name: 'edit-project-code-dialog',
        props: {
            codeData: Object
        },
        data() {
            return {
                editCodeForm: {
                    id: '',
                    code: '',
                    message: ''
                },
                editCodeFormRules: {
                    code: [
                        {required: true, message: '请输入状态码', trigger: 'blur'},
                        {max: 50, message: '状态码最大长度为 50', trigger: 'blur'}
                    ],
                    message: [
                        {required: true, message: '请输入描述', trigger: 'blur'},
                        {max: 50, message: '描述最大长度为 50', trigger: 'blur'}
                    ]
                }
            }
        },
        computed: {
            projectId() {
                return this.$store.state.projectId;
            }
        },
        methods: {
            handleCancel() {
                this.$refs['editCodeForm'].resetFields();
                this.$emit('cancel');
            },
            handleEdit(e) {
                this.$refs['editCodeForm'].validate(valid => {
                    if(valid) {
                        apis.updateProjectCode(this.projectId, this.editCodeForm.id, {
                            code: this.editCodeForm.code,
                            message: this.editCodeForm.message
                        }).then(data => {
                            if(data.code === 1) {
                                this.$message.success('更新状态码成功');
                                this.$refs['editCodeForm'].resetFields();
                                this.$emit('edited', e);
                            } else {
                                this.$message.error(data.message);
                            }
                        });
                    } else {
                        return false;
                    }
                });
            }
        },
        watch: {
            'codeData.id'(val) {
                this.editCodeForm.id = val;
            },
            'codeData.code'(val) {
                this.editCodeForm.code = val;
            },
            'codeData.message'(val) {
                this.editCodeForm.message = val;
            }
        }
    }

</script>
