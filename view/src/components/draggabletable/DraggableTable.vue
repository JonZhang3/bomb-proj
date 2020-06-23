<template>
    <el-table :data="data" v-bind="$attrs" border stripe style="width: 100%;">
        <slot></slot>
        <div slot="append" v-if="showAddRow" style="text-align: center;">
            <el-button @click="handleAddRow" icon="el-icon-plus" type="text">添加一行</el-button>
        </div>
    </el-table>
</template>

<script>

    import Sortable from 'sortablejs';

    export default {
        name: 'draggable-table',
        props: {
            value: Array,
            showAddRow: {
                type: Boolean,
                default: true
            }
        },
        data() {
            return {
                data: this.value
            }
        },
        mounted() {
            this.drag();
        },
        methods: {
            handleAddRow() {
                this.data.push( {fieldName: '5', type: '6'});
                this.$emit('input', this.data);
            },
            drag() {
                const that = this;
                const el = document.querySelectorAll('.el-table__body-wrapper > table > tbody')[0];
                Sortable.create(el, {
                    disabled: false,
                    ghostClass: 'sortable-ghost',
                    animation: 150,
                    group: {
                        pull: false,
                        put: false
                    },
                    onEnd(e) {
                        if(e.newIndex !== e.oldIndex) {
                            const targetObj = that.data.splice(e.oldIndex, 1)[0];
                            that.data.splice(e.newIndex, 0, targetObj);
                            // console.log(that.data);
                            that.$emit('input', that.data);
                        }
                    },
                    onMove(evt, originalEvent) {
                        console.log(evt);
                        console.log(originalEvent);
                    }
                });
            }
        },
        watch: {
            'value'(val) {
                this.data = val;
            }
        }
    }

</script>

<style>
    .sortable-ghost {
        opacity: 0.4;
        background-color: #F4E2C9;
    }
</style>
