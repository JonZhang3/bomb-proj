<template>
    <div class="server-table-item" style="display: flex;flex-direction: row;align-items: center;">
        <i class="el-icon-s-platform" style="font-size: 20px;"></i>
        <div style="margin-left: 10px;">
            <div>
                <span style="font-size: 16px;">{{data.serverName}}</span>
                <div style="display: inline-block;color: #909399;font-size: 14px;margin-left: 10px;">
                    <span><span class="underline">{{data.hostName}}</span></span>
<!--                    <span style="margin-left: 10px;">Port: <span class="underline">{{data.sshPort}}</span></span>-->
                    <span style="margin-left: 10px;"><span class="underline">{{data.username}}</span></span>
                </div>
            </div>
            <div>
                <el-tag v-for="(item, index) in (data.tags ? data.tags.split(','): [])" :key="index"
                        size="mini"effect="plain">{{item}}</el-tag>
            </div>
            <div style="color: #909399;font-size: 12px;">{{data.description}}</div>
        </div>
    </div>
</template>

<script>

export default {
    name: 'server-table-item',
    props: {
        data: {
            type: Object,
            default: () => {
                return {
                    serverName: '',
                    hostName: '',
                    sshPort: '',
                    username: '',
                    description: '',
                    tags: ''
                }
            }
        }
    },
    data() {
        return {

        }
    }
}

</script>

<style>
.server-table-item .el-tag:not(:first-child) {
    margin-left: 5px;
}
.server-table-item .underline {
    text-decoration: underline;
}
</style>
