<template>
    <el-dialog :title="`修改[${oldProjectName}]`" v-bind="$attrs" :before-close="handleCancel" :close-on-click-modal="false">
        <el-form :model="editProjectForm" :rules="editProjectFormRules" ref="editProjectForm" label-width="80px">
            <el-form-item label="项目名称" prop="name">
                <el-input v-model="editProjectForm.name"></el-input>
            </el-form-item>
            <el-form-item label="是否公开" prop="type">
                <el-radio-group size="small" v-model="editProjectForm.type">
                    <el-radio-button :label="2">公开</el-radio-button>
                    <el-radio-button :label="1">私有</el-radio-button>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="项目简介" prop="desc">
                <el-input type="textarea" maxlength="255"
                          v-model="editProjectForm.desc"
                          show-word-limit
                          :autosize="{minRows: 2, maxRows: 4}"></el-input>
            </el-form-item>
            <el-form-item label="Git 地址" prop="gitAddr">
                <el-input v-model="editProjectForm.gitAddr"></el-input>
            </el-form-item>
            <el-form-item label="封面" prop="cover">
                <el-upload :action="coverUploadAction" name="cover"
                           accept="image/png,image/jpeg"
                           class="new-project-cover-uploader"
                           :show-file-list="false"
                           :on-success="handleUploadSuccess"
                           :before-upload="handleBeforeUpload">
                    <img v-if="editProjectForm.cover" :src="coverBaseUrl + editProjectForm.cover" class="uploader-preview" alt="cover">
                    <i v-else class="el-icon-plus uploader-icon"></i>
                </el-upload>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleEdit">更 新</el-button>
        </span>
    </el-dialog>
</template>

<script>

    import apis from "../../../../api/apis";

    export default {
        name: 'edit-project-dialog',
        data() {
            return {
                oldProjectName: '',
                editProjectForm: {
                    id: '',
                    name: '',
                    desc: '',
                    type: '1',
                    cover: '',
                    gitAddr: ''
                },
                editProjectFormRules: {
                    name: [
                        {required: true, message: '请输入项目名', trigger: 'blur'},
                        {max: 50, message: '项目名最大长度为 50', trigger: 'blur'}
                    ],
                    desc: [
                        {max: 255, message: '项目简介最大长度为 255', trigger: 'blur'}
                    ]
                },
                coverUploadAction: apis.coverUploadUrl,
                coverBaseUrl: apis.coverBaseUrl
            }
        },
        props: {
            projectData: Object
        },
        methods: {
            handleUploadSuccess(data) {
                if(data.code === 1) {
                    this.editProjectForm.cover = data.data;
                } else {
                    this.$message.error(data.message);
                }
            },
            handleBeforeUpload(file) {
                const fileType = file.type;
                if(fileType !== 'image/png' && fileType !== 'image/jpeg') {
                    this.$message.error('封面只允许 png, jpg 格式的图片！');
                    return false;
                }
                if(file.size / 1024 / 1024 > 1) {
                    this.$message.error('封面图片大小不能超过 1 MB！');
                    return false;
                }
                return true;
            },
            handleCancel() {
                this.$refs['editProjectForm'].resetFields();
                this.$emit('cancel');
            },
            handleEdit() {
                this.$refs['editProjectForm'].validate(valid => {
                    if(valid) {
                        apis.updateProject(this.editProjectForm.id, {
                            name: this.editProjectForm.name,
                            desc: this.editProjectForm.desc,
                            type: this.editProjectForm.type,
                            cover: this.editProjectForm.cover,
                            gitAddr: this.editProjectForm.gitAddr
                        }).then(data => {
                            if(data.code === 1) {
                                this.$message.success('修改项目成功');
                                this.$emit('edited');
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
            'projectData.id'(val) {
                this.editProjectForm.id = val;
            },
            'projectData.name'(val) {
                this.oldProjectName = this.editProjectForm.name = val;
            },
            'projectData.desc'(val) {
                this.editProjectForm.desc = val;
            },
            'projectData.type'(val) {
                this.editProjectForm.type = val + "";
            },
            'projectData.cover'(val) {
                this.editProjectForm.cover = val;
            },
            'projectData.gitAddr'(val) {
                this.editProjectForm.gitAddr = val;
            }
        }
    }

</script>
