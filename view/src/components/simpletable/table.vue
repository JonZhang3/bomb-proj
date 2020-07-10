<template>
    <div :class="`${prefixCls}-wrapper`">
        <div
            :class="[
                `${prefixCls}`,
                {
                    [`${prefixCls}-${size}`]: !!size
                }
            ]">
            <div :class="`${prefixCls}-header`">

            </div>
            <div :class="`${prefixCls}-body`">

            </div>
        </div>
    </div>
</template>

<script>

    import {oneOf, getStyle, deepCopy, getScrollBarSize} from '../../common/utils';

    export default {
        name: 'BoTable',
        props: {
            size: {
                validator(value) {
                    return oneOf(value, ['small', 'large', 'default']);
                }
            }
        },
        data() {
            return {
                prefixCls: 'bo-table'
            }
        }
    }

</script>
