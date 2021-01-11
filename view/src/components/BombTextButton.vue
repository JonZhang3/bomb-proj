<template>
    <el-button type="text" v-bind="$attrs" :class="[
        'bomb-text-btn',
        (type ? type : 'primary')
    ]"><slot></slot></el-button>
</template>

<script>

export default {
    name: 'bomb-text-button',
    props: {
        type: String,
        default: 'primary'
    },
    computed: {
        typeClass() {
            let type = this.type;
            let color = '#409EFF';// primary
            if (type === 'danger') color = '#F56C6C';
            else if(type === 'warning') color = '#E6A23C';
            else if(type === 'success') color = '#67C23A';
            else if(type === 'info') color = '#909399';
            return {
                color
            }
        }
    }
}

</script>

<style>

.bomb-text-btn.primary {
    color: #409EFF;
}

.bomb-text-btn.primary:focus,
.bomb-text-btn.primary:hover {
    color: #66b1ff;
}

.bomb-text-btn.primary:active {
    color: #3a8ee6;
}

.bomb-text-btn.danger {
    color: #F56C6C;
}

.bomb-text-btn.danger:focus,
.bomb-text-btn.danger:hover {
    color: #f78989;
}

.bomb-text-btn.danger:active {
    color: #dd6161;
}

</style>
