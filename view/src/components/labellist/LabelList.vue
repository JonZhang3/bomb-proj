<template>
    <div :class="['bomb-label-list', {'no-border': noBorder}]">
        <slot></slot>
    </div>
</template>

<script>

    export default {
        name: 'LabelList',
        props: {
            labelWidth: String,
            noBorder: {
                type: Boolean,
                default: false
            }
        },
        provide() {
            return {
                root: this
            }
        }
    }

</script>
