<template>
    <el-dialog width="30%;" title="添加成员" v-bind="$attrs" :before-close="handleCancel" :close-on-click-modal="false">
        <el-select v-model="values"
                   style="width: 100%;"
                   placeholder="请搜索选择成员"
                   :remote-method="queryUsers"
                   :loading="loading"
                   multiple filterable remote default-first-option>
            <el-option v-for="item in data"
                       :key="item.id"
                       :label="item.userName"
                       :value="item.id">

            </el-option>
        </el-select>
        <span slot="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleAdd">增 加</el-button>
        </span>
    </el-dialog>
</template>

<script>

    import apis from "../../../../../api/apis";

    export default {
        name: 'new-project-member-dialog',
        data() {
            return {
                loading: false,
                values: [],
                data: []
            }
        },
        computed: {
            projectId() {
                return this.$store.state.projectId;
            }
        },
        methods: {
            handleCancel() {
                this.data = [];
                this.$emit('cancel');
            },
            queryUsers(searchText) {
                if(searchText) {
                    this.loading = true;
                    apis.queryProjectNotExistsUsers(this.projectId,
                        {name: searchText}, {useLoading: false}).then(data => {
                        this.loading = false;
                        if(data.code === 1) {
                            this.data = data.data;
                        } else {
                            this.$message.error(data.message);
                        }
                    });
                }
            },
            handleAdd() {

            }
        }
    }

</script>
