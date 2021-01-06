<template>
    <el-dialog title="新增主机组" v-bind="$attrs" :before-close="handleCancel" :close-on-click-modal="false">
        <el-form ref="newServerGroupForm" :model="newServerGroupForm" :rules="newServerGroupRule" label-width="80px">
            <el-form-item label="组名" prop="groupName">
                <el-input v-model="newServerGroupForm.groupName"></el-input>
            </el-form-item>
            <el-form-item label="简介" prop="desc">
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
        handleAdd() {

        }
    }
}
</script>
