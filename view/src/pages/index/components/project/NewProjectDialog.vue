<template>
    <el-dialog title="新增项目" v-bind="$attrs">
        <el-form :model="newProjectForm" ref="newProjectForm" label-width="80px">
            <el-form-item label="项目名称">
                <el-input v-model="newProjectForm.name"></el-input>
            </el-form-item>
            <el-form-item label="是否公开">
                <el-radio-group size="small" v-model="newProjectForm.type">
                    <el-radio-button :label="2">公开</el-radio-button>
                    <el-radio-button :label="1">私有</el-radio-button>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="项目简介">
                <el-input type="textarea" maxlength="255"
                          v-model="newProjectForm.desc"
                          show-word-limit
                          :autosize="{minRows: 2, maxRows: 4}"></el-input>
            </el-form-item>
            <el-form-item label="封面">
                <el-upload :action="coverUploadAction" name="cover"
                           class="new-project-cover-uploader"
                           :show-file-list="false"
                           :on-success="handleUploadSuccess"
                           :before-upload="handleBeforeUpload">
                    <img v-if="coverName" :src="coverBaseUrl + coverName" class="uploader-preview" alt="cover">
                    <i v-else class="el-icon-plus uploader-icon"></i>
                </el-upload>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button @click="handleCancle">取 消</el-button>
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
                    cover: ''
                },
                newProjectFormRules: {

                },
                coverUploadAction: apis.coverUploadUrl,
                coverBaseUrl: apis.coverBaseUrl,
                coverName: ''
            }
        },

        methods: {
            handleUploadSuccess(data) {
                console.log(data);
                if(data.code === 1) {
                    this.coverName = data.data;
                } else {
                    this.$message.error(data.message);
                }
            },
            handleBeforeUpload(file) {

            },
            handleCancle(e) {
                this.$emit('cancle', e);
            },
            handleAdd(e) {

                this.$emit('add', e);
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
