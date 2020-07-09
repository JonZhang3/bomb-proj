<template>
    <div ref="tableFooter"
         @transitionend="handleTransitionend" data-root="root"
         :class="['project-files-footer-wrapper', {'is-show': show }]">
        <div class="project-files-footer-wrapper-inner">
            <div style="display: flex;align-items: center;">
                <i class="el-icon-s-unfold" style="font-size: 18px;color: #409EFF;margin-right: 5px;"></i>
                <span style="font-size: 14px;">已选 {{selection.length}} 项</span>
                <el-divider direction="vertical"></el-divider>
                <el-button @click="handleZipDownload" icon="el-icon-download" size="mini">打包下载</el-button>
                <el-button @click="handleMove" size="mini">移动到...</el-button>
                <el-button @click="handleCopy" size="mini">复制到...</el-button>
                <el-button type="danger" size="mini">删除</el-button>
            </div>
            <el-button type="primary" icon="el-icon-s-operation" size="mini"
                       style="flex-shrink: 0" @click="tableRef.selectAll(false)">取消操作</el-button>
        </div>
    </div>
</template>

<script>

    export default {
        name: 'file-table-footer-operation',
        props: {
            show: Boolean,
            tableRef: Object,
            selection: Array
        },
        data() {
            return {

            }
        },
        methods: {
            handleCopy() {

            },
            handleMove() {

            },
            handleZipDownload() {

            },
            handleTransitionend(e) {
                if(e.target.dataset.root === 'root') {
                    this.$emit('on-transition-end');
                }
            }
        }
    }

</script>

<style>
    .project-files-footer-wrapper {
        height: 0;
        background-color: #ffffff;
        width: 100%;
        transition: height .3s linear;
    }
    .project-files-footer-wrapper-inner {
        opacity: 0;
        display: flex;
        justify-content: space-between;
        align-items: center;
        color: #303133;
        padding: 8px 25px;
        transition: opacity .3s linear;
    }
    .project-files-footer-wrapper.is-show {
        height: 44px;
    }
    .project-files-footer-wrapper.is-show .project-files-footer-wrapper-inner {
        opacity: 1;
    }
</style>
