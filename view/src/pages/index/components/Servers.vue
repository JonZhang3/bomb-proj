<template>
    <div class="proj-container">
        <el-row style="text-align: right">
            <el-button-group>
                <el-button round size="mini" type="primary" @click="newServerGroupDialogVisible = true">新增主机组</el-button>
                <el-button round size="mini" type="primary" @click="newServerDialogVisible = true">新增主机</el-button>
            </el-button-group>
        </el-row>
        <el-row>
            <p class="proj-item-title">主机组</p>
            <el-table :show-header="false"
                      :row-style="{cursor: 'pointer'}"
                      @row-click="handleGroupItemClick"
                      :data="serverGroups">
                <el-table-column>
                    <template slot-scope="scope">
                        <div style="display: flex;flex-direction: row;align-items: center;">
                            <i class="el-icon-s-grid" style="font-size: 20px;"></i>
                            <div style="margin-left: 10px;">
                                <div style="font-size: 16px;">{{scope.row.groupName}}</div>
                                <div style="color: #909399;font-size: 12px;">{{scope.row.groupDesc}}</div>
                            </div>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column width="100">
                    <template slot-scope="scope">
                        <bomb-text-button>编辑</bomb-text-button>
                        <bomb-text-button type="danger">删除</bomb-text-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <el-row style="text-align: right;margin-top: 10px;">
            <el-pagination background layout="prev,pager,next" small
                           :page-size="groupPager.pageSize"
                           :current-page="groupPager.page" :total="groupPager.total"></el-pagination>
        </el-row>

        <el-row>
            <p class="proj-item-title">主机</p>
            <el-table :show-header="false" stripe
                      :row-style="{cursor: 'pointer'}"
                      :data="servers">
                <el-table-column>
                    <template slot-scope="scope">
                        <server-table-item :data="scope.row"></server-table-item>
                    </template>
                </el-table-column>
                <el-table-column width="180">
                    <template slot-scope="scope">
                        <bomb-text-button>编辑</bomb-text-button>
                        <bomb-text-button>安装软件</bomb-text-button>
                        <bomb-text-button type="danger">删除</bomb-text-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <el-row style="text-align: right;margin-top: 10px;">
            <el-pagination background layout="prev,pager,next" small
                           :page-size="serverPager.pageSize"
                           :current-page="serverPager.page" :total="serverPager.total"></el-pagination>
        </el-row>
        <new-server-group-dialog :visible.sync="newServerGroupDialogVisible"
                                 @added="handleServerGroupAdded"
                                 @cancel="newServerGroupDialogVisible = false"></new-server-group-dialog>
        <new-server-dialog :visible.sync="newServerDialogVisible"
                           @added="handleServerAdded"
                           @cancel="newServerDialogVisible = false"></new-server-dialog>
    </div>
</template>

<script>

import NewServerGroupDialog from "@/pages/index/components/servers/NewServerGroupDialog";
import NewServerDialog from "@/pages/index/components/servers/NewServerDialog";
import ServerTableItem from "@/pages/index/components/servers/ServerTableItem";
import BombTextButton from "@/components/BombTextButton";
import apis from "@/api/apis";

export default {
    name: 'servers',
    components: {
        NewServerGroupDialog,
        NewServerDialog,
        ServerTableItem,
        BombTextButton
    },
    data() {
        return {
            newServerGroupDialogVisible: false,
            newServerDialogVisible: false,
            groupPager: {pageSize: 10, page: 1, total: 0},
            serverPager: {pageSize: 10, page: 1, total: 0},
            serverGroups: [],
            servers: [
                {
                    serverName: 'Redis',
                    hostName: '192.168.1.1',
                    sshPort: '22',
                    username: 'root',
                    describe: 'Redis 安装服务器'
                },
                {
                    serverName: 'trtt',
                    hostName: '192.168.1.1',
                    sshPort: '22',
                    username: 'root',
                    describe: 'dsds'
                }
            ]
        }
    },
    mounted() {
        this.listServerGroups(null, 1);
    },
    methods: {
        handleServerGroupAdded() {
            this.newServerGroupDialogVisible = false;
            this.listServerGroups(null, 1)
        },
        handleServerAdded() {
            this.newServerDialogVisible = false;
        },
        handleGroupItemClick(row, column, e) {
            e.stopPropagation();
            e.preventDefault();
        },
        listServerGroups(name, page) {
            const params = {page};
            if(name) {
                params['groupName'] = name;
            }
            apis.listServerGroups(params).then(data => {
                if(data.code === 1) {
                    this.groupPager.pageSize = data.data.limit;
                    this.groupPager.page = data.data.page;
                    this.groupPager.total = data.data.total;
                    this.serverGroups = data.data.records;
                } else {
                    this.$message.error(data.message)
                }
            });
        }
    }
}

</script>
