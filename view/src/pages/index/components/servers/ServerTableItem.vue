<template>
    <div style="display: flex;flex-direction: row;align-items: center;">
        <i class="el-icon-s-platform" style="font-size: 20px;"></i>
        <div style="margin-left: 10px;">
            <div>
                <span style="font-size: 16px;">{{data.serverName}}</span>
                <div style="display: inline-block;color: #909399;font-size: 14px;margin-left: 10px;">
                    <span>Host: {{data.hostName}}</span>
                    <span style="margin-left: 10px;">Port: {{data.sshPort}}</span>
                    <span style="margin-left: 10px;">User: {{data.username}}</span>
                </div>
            </div>
            <div style="color: #909399;font-size: 12px;">{{data.describe}}</div>
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
                    describe: ''
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
