<template>
    <div>
        <template v-if="column.slot">
            
        </template>
    </div>
</template>

<script>

    export default {
        name: 'TableCell',
        functional: true,
        inject: ['tableRoot'],
        props: {
            item: Object,
            column: Object,
            index: Number,
            render: Function
        },
        render(h, ctx) {
            return h('div', {

            }, ctx.injections.tableRoot.$slots.abc({}))
        }
    }

</script>
