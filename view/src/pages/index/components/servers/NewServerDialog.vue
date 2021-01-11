<template>
    <el-dialog title="新增主机" v-bind="$attrs" :before-close="handleCancel"
               @open="handleOpen"
               :close-on-click-modal="false">
        <el-form ref="newServerForm" :model="newServerForm" :rules="newServerFormRule"
                 label-width="80px" size="small" :inline="true">
            <el-form-item label="名称" prop="serverName">
                <el-input v-model="newServerForm.serverName" size="small"></el-input>
            </el-form-item>
            <el-form-item label="所属组" prop="group">
                <el-select placeholder="输入组名称搜索" remote :remote-method="handleSeatchServerGroups"
                           :loading="groupSearchLoading"
                           loading-text="正在搜索"
                           v-model="newServerForm.serverGroup" filterable>
                    <el-option v-for="item in serverGroups" :key="item.id"
                               :label="item.groupName" :value="item.id"></el-option>
                </el-select>
            </el-form-item>
            <br/>
            <el-form-item label="描述" prop="describe">
                <el-input type="textarea" show-word-limit
                          :autosize="{minRows: 4, maxRows: 6}"
                          v-model="newServerForm.describe"></el-input>
            </el-form-item>
            <br/>
            <el-form-item label="HostName" prop="hostName">
                <el-input v-model="newServerForm.hostName" size="small"></el-input>
            </el-form-item>
            <el-form-item label="SSH Port" prop="sshPort">
                <el-input-number size="mini" v-model="newServerForm.sshPort" :min="1" :max="65535"></el-input-number>
            </el-form-item>
            <br/>
            <el-form-item label="用户名" prop="username">
                <el-input v-model="newServerForm.username" size="small"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="pass">
                <el-input type="password" v-model="newServerForm.pass" size="small"></el-input>
            </el-form-item>
            <el-form-item>
                <el-checkbox v-model="newServerForm.rememberPass" size="small">记住密码</el-checkbox>
            </el-form-item>
            <br/>
            <el-form-item label="标签" prop="tags">
                <el-select v-model="newServerForm.tags" multiple filterable allow-create default-first-option></el-select>
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
import {debounce} from 'throttle-debounce'

export default {
    name: 'new-server-dialog',
    data() {
        return {
            serverGroups: [],
            groupSearchLoading: false,
            newServerForm: {
                serverName: '',
                describe: '',
                serverGroup: '',
                hostName: '',
                sshPort: 22,
                username: '',
                pass: '',
                rememberPass: '',
                tags: []
            },
            newServerFormRule: {}
        }
    },
    methods: {
        handleCancel() {
            this.$refs['newServerForm'].resetFields();
            this.$emit('cancel');
        },
        handleAdd(e) {
            this.$refs['newServerGroupForm'].validate(valid => {
                if(valid) {
                    this.addServer(e);
                } else {
                    return false;
                }
            });
        },
        addServer(e) {

        },
        handleOpen() {
            this.listServerGroups();
        },
        handleSeatchServerGroups(name) {
            if(name) {
                this.groupSearchLoading = true;
                debounce(1000, false, () => this.listServerGroups(name))();
            }
        },
        listServerGroups(name) {
            const params = {page: 1};
            if(name) {
                params['groupName'] = name;
            }
            apis.listServerGroups(params, {useLoading: false}).then(data => {
                this.groupSearchLoading = false;
                if(data.code === 1) {
                    this.serverGroups = data.data.records;
                } else {
                    this.$message.error(data.message)
                }
            });
        }
    }
}

</script>
