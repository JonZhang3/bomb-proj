<template>
    <el-dialog title="新增主机" v-bind="$attrs" :before-close="handleCancel" width="40%"
               @open="handleOpen"
               :close-on-click-modal="false">
        <el-form ref="editServerForm" :model="editServerForm" :rules="editServerFormRule"
                 label-width="100px" size="small" :inline="true">
            <el-form-item label="名称" prop="serverName" required>
                <el-input v-model="editServerForm.serverName" size="small"></el-input>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>
export default {
    name: 'edit-server-dialog',
    props: {
        id: String,
        data: Object
    },
    data() {
        return {
            editServerForm: {
                serverName: ''
            },
            editServerFormRule: {}
        }
    },
    methods: {
        handleCancel() {

        },
        handleOpen() {

        }
    },
    watch: {

    }
}
</script>

<style>

</style>
