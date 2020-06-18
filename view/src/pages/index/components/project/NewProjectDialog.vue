<template>
    <el-dialog title="新增项目" v-bind="$attrs" :before-close="handleCancel" :close-on-click-modal="false">
        <el-form :model="newProjectForm" :rules="newProjectFormRules" ref="newProjectForm" label-width="80px">
            <el-form-item label="项目名称" prop="name">
                <el-input v-model="newProjectForm.name"></el-input>
            </el-form-item>
            <el-form-item label="是否公开" prop="type">
                <el-radio-group size="small" v-model="newProjectForm.type">
                    <el-radio-button :label="2">公开</el-radio-button>
                    <el-radio-button :label="1">私有</el-radio-button>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="项目简介" prop="desc">
                <el-input type="textarea" maxlength="255"
                          v-model="newProjectForm.desc"
                          show-word-limit
                          :autosize="{minRows: 2, maxRows: 4}"></el-input>
            </el-form-item>
            <el-form-item label="Git 地址" prop="gitAddr">
                <el-input v-model="newProjectForm.gitAddr"></el-input>
            </el-form-item>
            <el-form-item label="封面" prop="cover">
                <el-upload :action="coverUploadAction" name="cover"
                           accept="image/png,image/jpeg"
                           class="new-project-cover-uploader"
                           :show-file-list="false"
                           :on-success="handleUploadSuccess"
                           :before-upload="handleBeforeUpload">
                    <img v-if="newProjectForm.cover" :src="coverBaseUrl + newProjectForm.cover" class="uploader-preview" alt="cover">
                    <i v-else class="el-icon-plus uploader-icon"></i>
                </el-upload>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleAdd">新 增</el-button>
        </span>
    </el-dialog>
</template>

<script>

    import apis from "../../../../api/apis";

    export default {
        name: 'new-project-dialog',
        data() {
            return {
                newProjectForm: {
                    name: '',
                    desc: '',
                    type: '2',
                    cover: '',
                    gitAddr: ''
                },
                newProjectFormRules: {
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

        methods: {
            handleUploadSuccess(data) {
                if(data.code === 1) {
                    this.newProjectForm.cover = data.data;
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
            handleCancel(done) {
                this.$refs['newProjectForm'].resetFields();
                this.$emit('cancel');
            },
            handleAdd(e) {
                this.$refs['newProjectForm'].validate(valid => {
                    if(valid) {
                        apis.newProject({
                            name: this.newProjectForm.name,
                            desc: this.newProjectForm.desc,
                            type: this.newProjectForm.type,
                            cover: this.newProjectForm.cover,
                            gitAddr: this.newProjectForm.gitAddr
                        }).then(data => {
                            if(data.code === 1) {
                                this.$message.success('新增项目成功');
                                this.$emit('added', e);
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

<style>

    .new-project-cover-uploader {

        width: 64px;
        height: 64px;
    }

    .new-project-cover-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        width: 100%;
        height: 100%;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .new-project-cover-uploader .uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 64px;
        height: 64px;
        line-height: 64px;
        text-align: center;
    }

    .new-project-cover-uploader .uploader-preview {
        width: 100%;
        height: 100%;
        display: inline-block;
    }

</style>
