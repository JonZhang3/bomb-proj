<template>
    <el-dialog title="新增主机组" v-bind="$attrs" :before-close="handleCancel" :close-on-click-modal="false">
        <el-form ref="newServerGroupForm" :model="newServerGroupForm" :rules="newServerGroupRule" label-width="80px">
            <el-form-item label="名称" prop="groupName">
                <el-input v-model="newServerGroupForm.groupName"></el-input>
            </el-form-item>
            <el-form-item label="描述信息" prop="desc">
                <el-input type="textarea" maxlength="255"
                          v-model="newServerGroupForm.desc"
                          show-word-limit
                          :autosize="{minRows: 4, maxRows: 6}"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleAdd">新 增</el-button>
        </span>
    </el-dialog>
</template>

<script>

import apis from "@/api/apis";

export default {
    name: 'new-server-group-dialog',
    data() {
        return {
            newServerGroupForm: {
                groupName: '',
                desc: ''
            },
            newServerGroupRule: {

            }
        }
    },
    methods: {
        handleCancel() {
            this.$refs['newServerGroupForm'].resetFields();
            this.$emit('cancel');
        },
        handleAdd(e) {
            this.$refs['newServerGroupForm'].validate(valid => {
                if(valid) {
                    this.addServerGroup(e);
                } else {
                    return false;
                }
            });
        },
        addServerGroup(e) {
            apis.addServerGroup({
                groupName: this.newServerGroupForm.groupName,
                groupDesc: this.newServerGroupForm.desc
            }).then(data => {
                if(data.code === 1) {
                    this.$message.success('新增组成功');
                    this.$refs['newServerGroupForm'].resetFields();
                    this.$emit('added', e);
                } else {
                    this.$message.error(data.message);
                }
            });
        }
    }
}
</script>
