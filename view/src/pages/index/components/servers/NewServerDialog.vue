<template>
    <el-dialog title="新增主机" :bind="$attrs" :before-close="handleClose" :close-on-click-modal="false">
        <el-form :model="newServerForm" :rules="newServerFormRule" label-width="80px">
            <el-form-item label="名称" prop="name">

            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>

export default {
    name: 'new-server-dialog',
    data() {
        return {
            newServerForm: {

            },
            newServerFormRule: {}
        }
    },
    methods: {
        handleClose() {

        }
    }
}

</script>
