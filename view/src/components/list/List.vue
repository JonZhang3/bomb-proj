<template>
    <div :class="classes">
        <div class="bomb-list-header" v-if="header || $slots.header"><slot name="header">{{ header }}</slot></div>
        <div class="bomb-list-container">
            <ul class="bomb-list-items"><slot></slot></ul>
        </div>
        <div class="bomb-list-footer" v-if="footer || $slots.footer"><slot name="footer">{{ footer }}</slot></div>
    </div>
</template>

<script>
    import { oneOf } from '../../common/utils';
    const prefixCls = 'bomb-list';
    export default {
        name: 'List',
        provide () {
            return {
                ListInstance: this
            };
        },
        props: {
            border: {
                type: Boolean,
                default: false
            },
            itemLayout: {
                validator (value) {
                    return oneOf(value, ['horizontal', 'vertical']);
                },
                default: 'horizontal'
            },
            // 或 slot
            header: {
                type: String,
                default: ''
            },
            // 或 slot
            footer: {
                type: String,
                default: ''
            },
            size: {
                validator (value) {
                    return oneOf(value, ['small', 'large', 'default']);
                },
                default: 'default'
            },
            split: {
                type: Boolean,
                default: true
            }
        },
        data () {
            return {
            };
        },
        computed: {
            classes () {
                return [
                    `${prefixCls}`,
                    `${prefixCls}-${this.size}`,
                    `${prefixCls}-${this.itemLayout}`,
                    {
                        [`${prefixCls}-bordered`]: this.border,
                        [`${prefixCls}-split`]: this.split
                    }
                ];
            },
        },
        methods: {
        }
    };
</script>
