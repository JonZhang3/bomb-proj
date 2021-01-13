<template>
    <div class="proj-container">
        <el-row>
            <el-col :span="12"><span>主机组：{{groupName}}</span></el-col>
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
                <el-table-column width="220">
                    <template slot-scope="scope">
                        <bomb-text-button>查看</bomb-text-button>
                        <bomb-text-button>编辑</bomb-text-button>
                        <bomb-text-button>安装软件</bomb-text-button>
                        <bomb-text-button type="danger">移出组</bomb-text-button>
                        <bomb-text-button type="danger">删除</bomb-text-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <el-row style="text-align: right;margin-top: 10px;">
            <el-pagination background layout="prev,pager,next" small
                           :page-size="pageSize" :current-page="page" :total="total"></el-pagination>
        </el-row>
    </div>
</template>

<script>
import ServerTableItem from "@/pages/index/components/servers/ServerTableItem";
import BombTextButton from "@/components/BombTextButton";

import apis from "@/api/apis";

export default {
    name: 'server-group-view',
    components: {
        ServerTableItem,
        BombTextButton
    },
    props: {
        groupName: String
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
        pageListServers(name, page) {
            const params = {page};
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
    }
}
</script>
