<template>
    <el-form :status-icon="false" :model="formData"
             style="height: 100%;overflow: hidden;width: 100%;"
             ref="form" class="edit-table-field-form">
        <vxe-table :data="formData.fields"
                   highlight-current-row auto-resize
                   ref="xTable" max-height="100%"
                   style="width: 100%;" class="edit-table-field-table"
                   :radio-config="{trigger: 'row'}"
                   @radio-change="handleRowChange" border="inner"
                   @edit-actived="handleRowChange"
                   :edit-config="{trigger: 'click', mode: 'row'}">
            <vxe-table-column type="radio" width="30"></vxe-table-column>
            <vxe-table-column type="seq" width="40"></vxe-table-column>
            <vxe-table-column field="fieldName" title="列名" width="25%" show-overflow
                              :edit-render="{name: 'input', immediate: true, autoselect: true, attrs: {type: 'text'}}"></vxe-table-column>
            <vxe-table-column field="type" title="类型" :edit-render="{}">
                <template v-slot:edit="scope">
                    <el-select placeholder="请选择字段类型"
                               @change="val => handleFieldTypeChange(val, scope.row)"
                               filterable size="small" v-model="scope.row.type">
                        <el-option v-for="(value, key) in fieldTypes.types" :key="key" :value="key" :label="key"></el-option>
                    </el-select>
                </template>
            </vxe-table-column>
            <vxe-table-column field="length" title="长度"
                              :edit-render="{name: '$input', props: {type: 'integer', min: 0, max: 65535}}"></vxe-table-column>
            <vxe-table-column field="notes" title="注释" width="18%" show-overflow
                              :edit-render="{name: 'input', immediate: true, autoselect: true, attrs: {type: 'text'}}"></vxe-table-column>
            <vxe-table-column field="notNull" title="非空" :edit-render="{}">
                <template v-slot="{row}">
                    <el-checkbox v-model="row.notNull" disabled size="small" true-label="1" false-label="0"></el-checkbox>
                </template>
                <template v-slot:edit="{row}">
                    <el-checkbox v-model="row.notNull" size="small" true-label="1" false-label="0"></el-checkbox>
                </template>
            </vxe-table-column>
            <vxe-table-column field="pk" title="主键" :edit-render="{}">
                <template v-slot="{row}">
                    <el-checkbox v-model="row.pk" disabled size="small" true-label="1" false-label="0"></el-checkbox>
                </template>
                <template v-slot:edit="{row}">
                    <el-checkbox v-model="row.pk" size="small" true-label="1" false-label="0"></el-checkbox>
                </template>
            </vxe-table-column>
            <vxe-table-column title="操作" width="70px">
                <template v-slot:header>
                    <el-button style="padding: 0;" type="text" @click="handleAddRow">添加一行</el-button>
                </template>
                <template v-slot="{row, rowIndex, $rowIndex}">
                    <el-button type="text" @click.stop="handleTableRowDelete(row, rowIndex, $rowIndex)"
                               size="small" style="color: #F56C6C;">删除</el-button>
                </template>
            </vxe-table-column>
        </vxe-table>
        <bomb-drawer title="其他信息" :visible.sync="drawerVisible"
                     align="left" width="300px" :modal="false">
            <field-type-extras :all-extras="fieldTypes.extras"
                               :extras="fieldExtras" :value="fieldExtrasValue"></field-type-extras>
        </bomb-drawer>
    </el-form>
</template>

<script>

    import settings from '../../../../../common/settings';
    import FieldTypeExtras from "./FieldTypeExtras";

    export default {
        name: 'EditTableFields',
        components: {
            FieldTypeExtras
        },
        props: {
            value: Array
        },
        data() {
            return {
                fieldTypes: [],
                fieldExtras: [],
                fieldExtrasValue: [],
                drawerVisible: false,
                index: 0,
                formData: {
                    fields: [],
                    rules: {
                        fieldName: [
                            {required: true, message: '请输入字段名', trigger: 'blur'},
                        ],
                        type: [
                            {required: true, message: '请选择字段类型', trigger: 'blur'},
                        ],
                        length: [
                        ]
                    }
                },
                defaultFieldData: {
                    fieldName: '', type: '', length: 0,
                    notNull: '0', pk: '0', autoIncrement: '0',
                    defaultValue: '', notes: '', indexes: '',
                    indexesName: '', marker: '', onUpdateCT: '0',
                    valueList: [], _binary: '0', _charset: '',
                    _collation: '', _decimal: '', _unsigned: '0', _zerofill: '0'
                },
            }
        },
        mounted() {
            this.fieldTypes = JSON.parse('{"extras":{"defaultValue":{"name":"默认值","key":"defaultValue","type":"input"},"dpDefaultValue":{"name":"默认值","key":"defaultValue","type":"datepicker","mutexType":"checkbox","mutexName":"CURRENT_TIMESTAMP"},"dtpDefaultValue":{"name":"默认值","key":"defaultValue","type":"datetimepicker"},"tpDefaultValue":{"name":"默认值","key":"defaultValue","type":"timepicker"},"autoIncrement":{"name":"自动增长","key":"autoIncrement","kw":"AUTO_INCREMENT","type":"checkbox"},"_unsigned":{"name":"无符号","key":"_unsigned","kw":"UNSIGNED","type":"checkbox"},"_zerofill":{"name":"ZEROFILL","key":"_zerofill","kw":"ZEROFILL","type":"checkbox"},"_charset":{"name":"字符集","key":"_charset","kw":"CHARACTER SET","type":"select"},"_collation":{"name":"排序规则","key":"_collation","kw":"COLLATE","type":"select"},"_binary":{"name":"二进制","key":"_binary","kw":"BINARY","type":"checkbox"},"onUpdateCT":{"name":"根据当前时间更新","key":"onUpdateCT","kw":"ON UPDATE CURRENT_TIMESTAMP","type":"checkbox"},"_decimal":{"name":"小数位","key":"_decimal","type":"numberinput"},"valueList":{"name":"值列表","key":"valueList","type":"iteminput"}},"types":{"bigint":{"length":false,"extras":["defaultValue","autoIncrement","_unsigned","_zerofill"]},"binary":{"length":true,"extras":["defaultValue"]},"bit":{"length":true,"extras":["defaultValue"]},"blob":{"length":false,"extras":null},"char":{"length":true,"extras":["defaultValue","_charset","_collation","_binary"]},"date":{"length":true,"extras":["dpDefaultValue"]},"datetime":{"length":false,"extras":["dtpDefaultValue","onUpdateCT"]},"decimal":{"name":"decimal","length":true,"extras":["_decimal","defaultValue","_unsigned","_zerofill"]},"double":{"length":true,"extras":["_decimal","defaultValue","_unsigned","_zerofill"]},"enum":{"length":false,"extras":["valueList","defaultValue","_charset","_collation"]},"float":{"length":true,"extras":["_decimal","defaultValue","_unsigned","_zerofill"]},"geometry":{"length":false,"extras":["defaultValue"]},"geometrycollection":{"length":false,"extras":["defaultValue"]},"int":{"length":true,"extras":["defaultValue","autoIncrement","_unsigned","_zerofill"]},"integer":{"length":true,"extras":["defaultValue","autoIncrement","_unsigned","_zerofill"]},"json":{"length":false,"extras":["defaultValue"]},"linestring":{"length":false,"extras":["defaultValue"]},"longblob":{"length":false,"extras":null},"longtext":{"length":false,"extras":["_charset","_collation","_binary"]},"mediumblob":{"length":false,"extras":null},"mediumint":{"length":true,"extras":["defaultValue","autoIncrement","_unsigned","_zerofill"]},"mediumtext":{"length":false,"extras":["_charset","_collation","_binary"]},"multilinestring":{"length":false,"extras":["defaultValue"]},"multipoint":{"length":false,"extras":["defaultValue"]},"multipolygon":{"length":false,"extras":["defaultValue"]},"point":{"length":false,"extras":["defaultValue"]},"polygon":{"length":false,"extras":["defaultValue"]},"real":{"length":true,"extras":["_decimal","defaultValue","_unsigned","_zerofill"]},"set":{"name":"set","length":false,"extras":["valueList","defaultValue","_charset","_collation"]},"smallint":{"name":"smallint","length":true,"extras":["defaultValue","autoIncrement","_unsigned","_zerofill"]},"text":{"name":"text","length":false,"extras":["_charset","_collation","_binary"]},"time":{"name":"time","length":false,"extras":["tpDefaultValue"]},"timestamp":{"name":"timestamp","length":false,"extras":["dtpDefaultValue","onUpdateCT"]},"tinyblob":{"name":"tinyblob","length":false,"extras":null},"tinyint":{"name":"tinyint","length":true,"extras":["defaultValue","autoIncrement","_unsigned","_zerofill"]},"tinytext":{"name":"tinytext","length":false,"extras":["_charset","_collation","_binary"]},"varbinary":{"name":"varbinary","length":true,"extras":["defaultValue"]},"varchar":{"name":"varchar","length":true,"extras":["defaultValue","_charset","_collation","_binary"]},"year":{"name":"year","length":false,"extras":["defaultValue"]}}}');
        },
        methods: {
            handleFieldTypeChange(val, row) {
                row.defaultValue = '';
                row.onUpdateCT = '0';
                row.valueList = [];
                row._binary = '0';
                row._charset = '';
                row._collation = '';
                row._decimal = '';
                row._unsigned = '0';
                row._zerofill = '0';
                this.fieldExtras = this.fieldTypes.types[val].extras;
                this.drawerVisible = !!this.fieldExtras;
            },
            handleAddRow() {
                if(this.formData.fields.length >= settings.maxDataTableFieldsLenght) {
                    this.$message.warning('添加了太多行，最多添加 ' + settings.maxDataTableFieldsLenght + ' 行哦');
                    return;
                }
                const newObj = {};
                Object.assign(newObj, this.defaultFieldData);
                this.formData.fields.push(newObj);
                this.$emit('input', this.formData.fields);
            },
            handleTableRowDelete(row, rowIndex, $rowIndex) {
                this.formData.fields.splice($rowIndex, 1);
                this.$emit('input', this.formData.fields);
            },
            handleRowChange({row}) {
                const type = this.fieldTypes.types[row.type];
                const extras = type ? this.fieldTypes.types[row.type].extras : null;
                if(type && extras) {
                    this.drawerVisible = true;
                    this.fieldExtras = extras;
                } else {
                    this.drawerVisible = false;
                }
                this.fieldExtrasValue = row;
            }
        },
        watch: {
            value(val) {
                this.formData.fields = val;
            }
        }
    }

</script>

<style>
    .sortable-ghost {
        opacity: 0.4;
        background-color: #F4E2C9;
    }
    .edit-table-field-form .el-form-item {
        margin-bottom: 14px;
    }
    .edit-table-field-form .el-form-item__error {
        padding: 0;
    }
    .edit-table-field-table .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner {
        background-color: #409EFF;
        border-color: #409EFF;
    }
    .edit-table-field-table .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner:after {
        border-color: #fff;
    }
</style>
