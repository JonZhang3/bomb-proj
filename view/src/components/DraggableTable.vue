<template>
    <el-form :status-icon="false" :model="formData" ref="form">
        <bomb-table :columns="columns" :data="data" v-bind="$attrs" fix-header>
            <slot></slot>
            <div slot="footer" v-if="showAddRow" style="text-align: center;">
                <el-button @click="handleAddRow" icon="el-icon-plus" type="text">添加一行</el-button>
            </div>
        </bomb-table>
<!--        <el-table :data="data" v-bind="$attrs" border stripe highlight-current-row :row-key="handleRowKey" style="width: 100%;">-->
<!--            <slot></slot>-->
<!--            <div slot="append" v-if="showAddRow" style="text-align: center;">-->
<!--                <el-button @click="handleAddRow" icon="el-icon-plus" type="text">添加一行</el-button>-->
<!--            </div>-->
<!--        </el-table>-->
    </el-form>
</template>

<script>

    import settings from '../common/settings';

    import Sortable from 'sortablejs';

    export default {
        name: 'draggable-table',
        props: {
            value: Array,
            showAddRow: {
                type: Boolean,
                default: true
            },
            draggableElement: String,
            defaultData: {
                type: Object,
                default: {}
            },
            rules: Object,
            formData: Object,
            columns: Array
        },
        data() {
            return {
                data: this.value,
                index: 0
            }
        },
        mounted() {
            console.log(this);
            this.drag();
        },
        methods: {
            valid(callback) {
                this.$refs['form'].validate(valid => {
                    if(valid) {
                        callback && callback();
                    } else {
                        return false;
                    }
                });
            },
            handleAddRow() {
                if(this.data.length >= settings.maxDataTableFieldsLenght) {
                    this.$message.warning('添加了太多行，最多添加 200 行哦');
                    return;
                }
                const newObj = {};
                Object.assign(newObj, this.defaultData);
                newObj.index = ++this.index;
                this.data.push(newObj);
                this.$emit('input', this.data);
            },
            handleRowKey(row) {
                return row.index;
            },
            drag() {
                const that = this;
                const el = document.querySelectorAll('.at-table__body > table tbody')[0];
                Sortable.create(el, {
                    disabled: false,
                    ghostClass: 'sortable-ghost',
                    animation: 150,
                    handle: that.draggableElement,
                    group: {
                        pull: false,
                        put: false
                    },
                    onEnd(e) {
                        if(e.newIndex !== e.oldIndex) {
                            const targetObj = that.data.splice(e.oldIndex, 1)[0];
                            that.data.splice(e.newIndex, 0, targetObj);
                            that.$emit('input', that.data);
                        }
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
