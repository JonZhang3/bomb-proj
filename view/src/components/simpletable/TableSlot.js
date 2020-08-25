export default {
    name: 'TableSlot',
    functional: true,
    inject: ['tableRoot'],
    props: {
        item: Object,
        column: Object,
        index: Number
    },
    render(h, ctx) {
        return h('div', ctx.injections.tableRoot.$scopedSlots[ctx.props.column.slot]({
            ...ctx.props.item,
            row: ctx.props.item,
            $index: ctx.props.index
        }))
    }
}
