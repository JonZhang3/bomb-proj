<template>
    <li :key="index"
        class="el-list-menu-item"
        :class="{'is-active': active, 'is-disabled': disabled}"
        :style="itemStyle"
        @click="handleClick"
        @mouseenter="handleMouseEnter"
        @mouseleave="handleMouseLeave">
        <slot></slot>
    </li>
</template>

<script>

    import Emitter from "../../common/mixins/emitter";

    export default {
        name: 'list-menu-item',
        inject: ['rootMenu'],
        mixins: [Emitter],
        props: {
            index: {
                default: null,
                validator: val => typeof val === 'string' || val === null
            },
            disabled: Boolean
        },
        methods: {
            handleClick(e) {
                if(!this.disabled) {
                    this.dispatch('list-menu', 'item-click', this);
                    this.$emit('click', this);
                }
            },
            handleMouseEnter() {
                this.$el.style.backgroundColor = this.hoverBackgroundColor;
            },
            handleMouseLeave() {

            }
        },
        computed: {
            active() {
                return this.index === this.rootMenu.activeIndex;
            },
            hoverBackgroundColor() {
                return this.rootMenu.hoverBackgroundColor || '';
            },

            itemStyle() {

            }
        },
        mounted() {
            this.rootMenu.addItem(this);
        },
        beforeDestroy() {
            this.rootMenu.removeItem(this);
        }
    }

</script>

<style>
    .el-list-menu-item {
        position: relative;
        display: flex;
        font-size: 14px;
        cursor: pointer;
        padding: 0 20px 0 24px;
        align-items: center;
        height: 36px;
        color: #455166;
        transition: background-color .3s, color .3s;
    }
    .el-list-menu-item:hover {
        background-color: #f2f4f6;
    }
    .el-list-menu-item [class^=el-icon-] {
        font-size: 16px;
        margin-right: 10px;
        text-align: center;
    }
    .el-list-menu-item.is-active {
        color: #409EFF;
        background-color: #eaf4ff;
    }
    .el-list-menu-item.is-disabled {
        color: #9b9da1;
    }
    .el-list-menu-item.is-disabled:hover {
        background-color: #ffffff;
    }
</style>
