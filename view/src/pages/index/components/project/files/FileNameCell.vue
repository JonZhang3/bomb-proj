<template>
    <div style="display: flex;align-items: center;">
        <div style="width: 24px;height: 24px;margin-right: 5px;line-height: 24px;text-align: center;">
            <i class="file-item-type-icon icon css3-icon medium-blue"></i>
        </div>
        <div style="display: inline-block;flex: 1;">
            <span style="vertical-align: middle;">{{data.fileName}}</span>
            <el-tooltip effect="dark" :content="data.starred ? '取消星标' : '添加星标'" placement="top">
                <i v-show="data.starred" :class="['file-item-icon', data.starred ? 'el-icon-star-on' : 'el-icon-star-off']"></i>
            </el-tooltip>
        </div>
        <el-popover placement="bottom" title="标题" width="200" trigger="click" content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">
            <i slot="reference" class="file-item-icon el-icon-more" style="display: none;"></i>
        </el-popover>
        <i v-show="data.shared"
           :class="['file-item-icon', 'el-icon-share', {'shared': data.shared}]"></i>
    </div>
</template>

<script>

    export default {
        name: 'file-name-cell',
        props: {
            data: Object
        },
        data() {
            return {

            }
        }
    }

</script>

<style>
    .file-item-type-icon:before {
        font-size: 16px;
    }
    .file-item-icon {
        text-align: center;
        font-size: 16px;
        display: inline-block;
        width: 24px;
        height: 24px;
        line-height: 24px;
        vertical-align: middle;
    }
    .file-item-icon:hover {
        cursor: pointer;
        background-color: rgba(226, 228, 230, .4);
        border-radius: 5px;
    }
    .file-item-icon.el-icon-star-off:hover {
        background-color: transparent;
        border-radius: 0;
        color: #606266;
    }
    .file-item-icon.el-icon-star-on {
        font-size: 20px;
        color: #F8C540;
    }
    .file-item-icon.el-icon-star-on:hover {
        background-color: transparent;
        border-radius: 0;
        color: #F8C540;
    }

    .file-item-icon.el-icon-share.shared {
        color: #409EFF;
    }

    .ivu-table-row-highlight .el-icon-star-off,
    .ivu-table-row-highlight .el-icon-more,
    .ivu-table-row-highlight .el-icon-share,
    .ivu-table-row-hover .el-icon-star-off,
    .ivu-table-row-hover .el-icon-more,
    .ivu-table-row-hover .el-icon-share {
        display: inline-block !important;
    }
</style>
