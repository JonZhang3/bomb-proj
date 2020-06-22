<template>
    <el-dialog width="30%" title="添加成员" v-bind="$attrs" class="add-project-member-dialog" :before-close="handleCancel" :close-on-click-modal="false">
        <p>请选择成员：</p>
        <el-select v-model="memberValues"
                   style="width: 100%;"
                   placeholder="请搜索选择成员"
                   :remote-method="queryUsers"
                   :loading="loading"
                   multiple filterable remote default-first-option>
            <el-option v-for="item in data"
                       :key="item.id"
                       :label="item.userName"
                       :value="item.id">
                <span style="font-weight: bold;">{{ item.nickName }}</span>
                <br/>
                <span style="">@{{ item.userName }}</span>
            </el-option>
        </el-select>
        <p>请赋予权限：</p>
        <el-select v-model="permissionValues"
                   multiple filterable
                   style="width: 100%;" placeholder="请选择权限">
            <el-option v-for="item in permissionData" :key="item.id" :label="item.name" :value="item.id"></el-option>
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
                memberValues: [],
                permissionValues: [],
                data: [],
                permissionData: []
            }
        },
        mounted() {
            this.listProjectPermissions();
        },
        computed: {
            projectId() {
                return this.$store.state.projectId;
            }
        },
        methods: {
            reset() {
                this.memberValues = [];
                this.permissionValues = [];
                this.data = [];
            },
            handleCancel() {
                this.reset();
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
                } else {
                    this.data = [];
                }
            },
            listProjectPermissions() {
                apis.listProjectPermissions().then(data => {
                    if(data.code === 1) {
                        this.permissionData = data.data;
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            handleAdd(e) {
                if(this.memberValues.length === 0) {
                    this.$message.warning("请选择要加入的成员");
                    return;
                }
                if(this.permissionValues.length === 0) {
                    this.$message.warning("请选择加入成员的权限");
                    return;
                }
                apis.addProjectMembers(this.projectId, this.memberValues.join(','), this.permissionValues.join(',')).then(data => {
                    if(data.code === 1) {
                        this.$message.success("添加成功");
                        this.reset();
                        this.$emit('added', e);
                    } else {
                        this.$message.error(data.message);
                    }
                });
            }
        }
    }

</script>

<style scoped>
    .el-select-dropdown__item {
        height: auto;
    }
</style>
