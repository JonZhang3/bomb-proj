<template>
    <div @transitionend="handleTransitionEnd" :class="['project-file-side', {'is-show': show}]">
        <div class="project-file-side-inner">
            <div style="width: 128px;height: 128px;line-height: 128px">
                <i class="file-item-type-icon icon css3-icon medium-blue"></i>
            </div>
            <div class="file-name">{{data.fileName}}</div>
            <el-divider></el-divider>
            <div class="file-info-title" style="">文件属性</div>
            <ul class="file-info-list">
                <li>
                    <div class="label">文件类型</div>
                    <div class="content">文件夹</div>
                </li>
                <li>
                    <div class="label">包含项数</div>
                    <div class="content">0</div>
                </li>
                <li>
                    <div class="label">创建者</div>
                    <div class="content">张三</div>
                </li>
                <li>
                    <div class="label">创建时间</div>
                    <div class="content">张三</div>
                </li>
                <li>
                    <div class="label">修改时间</div>
                    <div class="content">张三</div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>

    export default {
        name: 'project-file-detail-side',
        props: {
            show: Boolean,
            data: Object
        },
        data() {
            return {

            }
        },
        methods: {
            handleTransitionEnd(e) {
                this.$emit('on-transition-end')
            }
        }
    }

</script>

<style>
    .project-file-side {
        width: 240px;
        margin-right: -240px;
        border-top: 1px solid #DCDFE6;
        border-left: 1px solid #DCDFE6;
        transition: all .3s linear;
    }
    .project-file-side.is-show {
        margin-right: 0;
    }
    .project-file-side-inner {
        width: 100%;
        height: 100%;
        padding: 20px;
        background-color: #fff;
        display: flex;
        flex-direction: column;
        align-items: center;
        overflow: hidden;
    }
    .project-file-side .file-item-type-icon:before {
        font-size: 120px;
    }
    .project-file-side .file-name {
        padding: 16px 0 0;
        text-align: center;
        font-size: 16px;
        line-height: 24px;
        font-weight: 700;
        color: #303133;
    }
    .project-file-side .file-info-title {
        font-size: 15px;
        line-height: 24px;
        font-weight: 700;
        color: #303133;
        width: 100%;
    }
    .project-file-side .file-info-list {
        width: 100%;
        margin: 0;
        padding: 12px 0;
        font-size: 13px;
        line-height: 16px;
    }
    .project-file-side .file-info-list li {
        display: flex;
        align-items: center;
        width: 100%;
    }
    .project-file-side .file-info-list li:not(:first-child) {
        margin-top: 12px;
    }
    .project-file-side .file-info-list li .label {
        flex: 0 1 76px;
        color: #909399;
    }
    .project-file-side .file-info-list li .content {
        flex: 1 1;
        min-width: 0;
        display: flex;
        align-items: center;
        color: #606266;
    }
</style>
