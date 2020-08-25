<template>
    <vxe-table :data="indexes" style="width: 100%;"
               highlight-current-row auto-resize max-height="100%"  border="inner"
               :edit-config="{trigger: 'click', mode: 'row'}">
        <vxe-table-column type="seq" width="40"></vxe-table-column>
        <vxe-table-column field="indexName" title="索引名" width="25%" show-overflow
                          :edit-render="{name: 'input', immediate: true, autoselect: true, attrs: {type: 'text'}}"></vxe-table-column>
        <vxe-table-column field="fieldNames" title="包含列" show-overflow :edit-render="{}">
            <template v-slot:edit="{row}">
                <el-select v-model="row.fieldNames" filterable multiple size="small">
                    <el-option v-for="(item, index) in fields" :key="index" :label="item.fieldName" :value="item.fieldName"></el-option>
                </el-select>
            </template>
        </vxe-table-column>
        <vxe-table-column field="indexType" title="索引类型" :edit-render="{}">
            <template v-slot:edit="{row}">
                <el-cascader v-model="row.indexType" :options="tableIndexes" size="small"></el-cascader>
            </template>
        </vxe-table-column>
        <vxe-table-column field="notes" title="注释" show-overflow
                          :edit-render="{name: 'input', immediate: true, autoselect: true, attrs: {type: 'text'}}"></vxe-table-column>
        <vxe-table-column title="操作" width="70px">
            <template v-slot:header>
                <el-button style="padding: 0;" type="text" @click="handleAddRow">添加一行</el-button>
            </template>
            <template v-slot="{row, rowIndex, $rowIndex}">
                <el-button type="text" @click.stop="handleDeleteRow(row, rowIndex, $rowIndex)"
                           size="small" style="color: #F56C6C;">删除</el-button>
            </template>
        </vxe-table-column>
    </vxe-table>
</template>

<script>

    import settings from '../../../../../common/settings';

    export default {
        name: 'EditTableIndexes',
        props: {
            value: Array,
            fields: Array
        },
        data() {
            return {
                indexes: this.value,
                tableIndexes: [],
                defaultIndexData: {
                    indexName: '',
                    fieldNames: [],
                    indexType: [],
                    notes: ''
                }
            }
        },
        mounted() {
            this.tableIndexes = this.formatTableIndexes(JSON.parse('{"FULLTEXT":{"methods":["BTREE","HASH"],"sort":null},"NORMAL":{"methods":["BTREE","HASH"],"sort":null},"SPATIAL":{"methods":["BTREE","HASH"],"sort":null},"UNIQUE":{"methods":["BTREE","HASH"],"sort":null}}'));
        },
        methods: {
            formatTableIndexes(data) {
                const result = [];
                for(let key in data) {
                    const item = {};
                    item.value = key;
                    item.label = key;
                    const methods = data[key].methods;
                    if(methods) {
                        item.children = []
                        methods.forEach((val, index) => {
                            item.children[index] = {value: val, label: val};
                        })
                    }
                    result.push(item);
                }
                return result;
            },
            handleAddRow() {
                if(this.indexes.length >= settings.maxDataTableIndexesLength) {
                    this.$message.warning('添加了太多行，最多添加 ' + settings.maxDataTableIndexesLength + ' 行哦');
                    return;
                }
                const newObj = {};
                Object.assign(newObj, this.defaultIndexData);
                this.indexes.push(newObj);
                this.$emit('input', this.indexes);
            },
            handleDeleteRow(row, rowIndex, $rowIndex) {
                this.indexes.splice($rowIndex, 1);
                this.$emit('input', this.indexes);
            }
        },
        watch: {
            value(val) {
                this.indexes = val;
            }
        }
    }

</script>
