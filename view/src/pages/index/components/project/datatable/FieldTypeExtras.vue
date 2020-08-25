<template>
    <el-form ref="extrasForm" label-width="auto">
        <el-form-item v-for="item in extras" :label="allExtras[item]['name']">
            <el-input v-model="value[allExtras[item]['key']]" v-if="allExtras[item]['type'] === 'input'" size="small"></el-input>
            <el-checkbox v-model="value[allExtras[item]['key']]"
                         v-else-if="allExtras[item]['type'] === 'checkbox'" true-label="1" false-label="0"></el-checkbox>
            <el-select v-model="value[allExtras[item]['key']]" multiple allow-create filterable default-first-option size="small"
                       v-else-if="allExtras[item]['type'] === 'iteminput'"></el-select>
            <el-time-picker v-model="value[allExtras[item]['key']]" v-else-if="allExtras[item]['type'] === 'timepicker'" arrow-control size="small"></el-time-picker>
            <el-select v-model="value[allExtras[item]['key']]" v-else-if="allExtras[item]['type'] === 'select'" size="small"></el-select>
            <el-input-number v-model="value[allExtras[item]['key']]" v-else-if="allExtras[item]['type'] === 'numberinput'" size="small" :precision="0"></el-input-number>
            <el-date-picker v-model="value[allExtras[item]['key']]" v-else-if="allExtras[item]['type'] === 'datepicker'"
                            type="date" size="small" placeholder="选择日期"></el-date-picker>
            <el-date-picker v-model="value[allExtras[item]['key']]" v-else-if="allExtras[item]['type'] === 'datetimepicker'"
                            type="datetime" size="small" placeholder="选择日期"></el-date-picker>
        </el-form-item>
    </el-form>
</template>

<script>

    export default {
        name: 'field-type-extras',
        props: {
            extras: Array,
            allExtras: Object,
            value: Object
        },
        data() {
            return {
            }
        },
        methods: {
            resetForm() {
                this.$refs['extrasForm'].resetFields();
            }
        },
        watch: {
            'extras'(val) {
                this.extras = val ? val : [];
            },
            value(val) {
                console.log(val);
                this.value = val;
            }
        }
    }

</script>
