<template>
    <el-dialog title="新增主机组" v-bind="$attrs" :before-close="handleCancel" :close-on-click-modal="false">
        <el-form ref="editServerGroupForm" :model="editServerGroupForm" :rules="editServerGroupRule" label-width="80px">
            <el-form-item label="名称" prop="groupName">
                <el-input v-model="editServerGroupForm.groupName"></el-input>
            </el-form-item>
            <el-form-item label="描述信息" prop="desc">
                <el-input type="textarea" maxlength="255"
                          v-model="editServerGroupForm.desc"
                          show-word-limit
                          :autosize="{minRows: 4, maxRows: 6}"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleEdit">修 改</el-button>
        </span>
    </el-dialog>
</template>

<script>

import apis from "@/api/apis";

export default {
    name: 'edit-server-group-dialog',
    props: {
        data: {
            type: Object,
            default() {
                return {
                    id: '',
                    groupName: '',
                    desc: ''
                }
            }
        }
    },
    data() {
        return {
            editServerGroupForm: {
                groupName: '',
                desc: ''
            },
            editServerGroupRule: {

            }
        }
    },
    methods: {
        handleCancel() {
            this.$refs['editServerGroupForm'].resetFields();
            this.$emit('cancel');
        },
        handleEdit(e) {
            this.$refs['editServerGroupForm'].validate(valid => {
                if(valid) {
                    this.editServerGroup(e);
                } else {
                    return false;
                }
            });
        },
        editServerGroup(e) {
            apis.servers.updateServerGroup(this.data.id, {
                groupName: this.editServerGroupForm.groupName,
                groupDesc: this.editServerGroupForm.desc
            }).then(data => {
                if(data.code === 1) {
                    this.$message.success('修改组成功');
                    this.$refs['editServerGroupForm'].resetFields();
                    this.$emit('edited', e);
                } else {
                    this.$message.error(data.message);
                }
            });
        }
    },
    watch: {
        data(val) {
            this.editServerGroupForm = {
                groupName: val.groupName,
                desc: val.desc
            }
        }
    }
}
</script>
