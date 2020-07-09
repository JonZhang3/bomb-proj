<template>
    <div @transitionend="handleTransitionEnd" :class="['project-file-side', {'is-show': show}]">
        <div class="project-file-side-inner">
            <i class="icon css3-icon medium-blue"></i>
        </div>
    </div>
</template>

<script>

    export default {
        name: 'project-file-detail-side',
        props: {
            show: Boolean
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
</style>
