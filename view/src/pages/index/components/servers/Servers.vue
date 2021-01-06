<template>
    <div class="proj-container">
        <el-row style="text-align: right">
            <el-button-group>
                <el-button round size="mini" type="primary" @click="newServerGroupDialogVisible = true">新增主机组</el-button>
                <el-button round size="mini" type="primary">新增主机</el-button>
            </el-button-group>
        </el-row>
        <el-row>
            <p class="proj-item-title">主机组</p>
            <el-table :show-header="false" :data="serverGroups">
                <el-table-column slot-scope="scope">
                    <template>
                        <div style="display: flex;flex-direction: row;align-items: center;">
                            <i class="el-icon-s-grid" style="font-size: 20px;"></i>
                            <div style="margin-left: 10px;">
                                <div style="color: #303133;font-size: 16px;">组名</div>
                                <div style="color: #909399;font-size: 12px;">描述</div>
                            </div>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column width="100">
                    <template slot-scope="scope">
                        <el-button type="text" size="small">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>

        </el-row>
        <el-row>
            <p class="proj-item-title">主机</p>
            <el-table :show-header="false" :data="serverGroups">
                <el-table-column slot-scope="scope">
                    <template>
                        <span>组名</span>
                        <span>描述</span>
                    </template>
                </el-table-column>
                <el-table-column width="100">
                    <template slot-scope="scope">
                        <el-button type="text" size="small">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <new-server-group-dialog :visible.sync="newServerGroupDialogVisible"
                                 @added="handleServerGroupAdded"
                                 @cancel="newServerGroupDialogVisible = false"></new-server-group-dialog>
    </div>
</template>

<script>

import NewServerGroupDialog from "@/pages/index/components/servers/NewServerGroupDialog";

export default {
    name: 'servers',
    components: {
        NewServerGroupDialog
    },
    data() {
        return {
            newServerGroupDialogVisible: false,
            serverGroups: [
                {name: '123'}
            ],
            servers: [
                {name: '456'}
            ]
        }
    },
    methods: {
        handleServerGroupAdded() {
            this.newServerGroupDialogVisible = false;
        }
    }
}

</script>
