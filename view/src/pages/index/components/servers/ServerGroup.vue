<template>
    <div class="proj-container">
        <el-row>
            <el-col :span="12">
                <i class="el-icon-arrow-left bomb-back-menu" @click="$router.push('/servers')"></i>
                <span>主机组【{{groupName}}】</span>
            </el-col>
            <el-col :span="12" style="text-align: right;">
                <el-button-group>
                    <el-button round size="mini" type="primary" @click="newServerDialogVisible = true">新增主机</el-button>
                    <el-button round size="mini" type="primary" @click="">安装软件</el-button>
                </el-button-group>
            </el-col>
        </el-row>
        <el-row>
            <el-table :show-header="false" stripe :data="servers">
                <el-table-column>
                    <template slot-scope="scope">
                        <server-table-item :data="scope.row"></server-table-item>
                    </template>
                </el-table-column>
                <el-table-column width="260">
                    <template slot-scope="scope">
                        <el-link :underline="false" type="primary">查看</el-link>
                        <el-link :underline="false" type="primary">编辑</el-link>
                        <el-link :underline="false" type="primary">安装软件</el-link>
                        <el-link :underline="false" type="danger">移出组</el-link>
                        <el-link :underline="false" type="danger">删除</el-link>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <el-row style="text-align: right;margin-top: 10px;">
            <el-pagination background layout="prev,pager,next" small
                           :page-size="pageSize" :current-page="page" :total="total"></el-pagination>
        </el-row>
        <new-server-dialog :visible.sync="newServerDialogVisible"
                           @added="handleServerAdded" :lock-group="true" :static-server-group="{id: groupId, groupName}"
                           @cancel="newServerDialogVisible = false"></new-server-dialog>
    </div>
</template>

<script>
import ServerTableItem from "@/pages/index/components/servers/ServerTableItem";
import NewServerDialog from "@/pages/index/components/servers/NewServerDialog";

import apis from "@/api/apis";

export default {
    name: 'server-group',
    components: {
        ServerTableItem,
        NewServerDialog
    },
    data() {
        return {
            newServerDialogVisible: false,
            servers: [],
            pageSize: 10,
            page: 1,
            total: 0
        }
    },
    mounted() {
        this.pageListServers(null, 1);
    },
    methods: {
        handleServerAdded() {

        },
        pageListServers(name, page) {
            const params = {page, serverGroup: this.groupId};
            if(name) {
                params['name'] = name;
            }
            apis.servers.pageListServers(params).then(data => {
                if(data.code === 1) {
                    this.pageSize = data.data.limit;
                    this.page = data.data.page;
                    this.total = data.data.total;
                    this.servers = data.data.records;
                } else {
                    this.$message.error(data.message)
                }
            });
        }
    },
    computed: {
        groupName() {
            return this.$route.params.groupName
        },
        groupId() {
            return this.$route.params.groupId
        }
    }
}
</script>
