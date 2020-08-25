<template>
    <el-row class="main-content-root"
            style="display: flex;flex-direction: column;height: 100%;padding: 20px 0 0 0;">
        <el-row>
            <el-page-header @back="handleBack" style="display: flex;align-items: center;padding-left: 20px;">
                <el-breadcrumb slot="content" separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
                    <el-breadcrumb-item>数据库【{{databaseName}}】</el-breadcrumb-item>
<!--                    <el-breadcrumb-item v-if="currentTableData.tableName">{{currentOperation}}表【{{currentTableData.tableName}}】</el-breadcrumb-item>-->
                </el-breadcrumb>
            </el-page-header>
        </el-row>
        <el-row style="margin-top: 10px;flex: 1;display: flex;height: 100%;overflow: hidden;">
            <div style="display: flex;flex-direction: column; width: 200px;border-right: 1px solid #DCDFE6;height: 100%;overflow: hidden;">
                <div style="padding: 0 5px 10px 5px;display: flex;justify-content: center;align-items: center;">
                    <el-input v-model="tableSearchText" size="mini" placeholder="模糊匹配表名" clearable></el-input>
                    <i class="el-icon-plus" @click="handleAddTable" title="添加表"
                       style="cursor: pointer;font-size: 18px;padding-left: 5px;"></i>
                </div>
                <list-menu router router-mode="replace" style="flex: 1;"
                           :default-active="tableListActiveIndex"
                           v-loading="tableLoading">
                    <list-menu-item v-for="(item, i) in tables"
                                    :key="item.id" @click="handleTableClick(item)"
                                    :index="`/project/${$route.params.id}/db/${$route.params.dbId}/table/${item.id}/view`">
                        <i class="el-icon-s-grid"></i>
                        <el-tooltip effect="dark" :content="item.tableName" placement="right">
                            <span style="flex: 1;overflow: hidden;text-overflow: ellipsis;font-size: 12px;">{{item.tableName}}</span>
                        </el-tooltip>
                    </list-menu-item>
                </list-menu>
                <div style="text-align: center;">
                    <el-pagination hide-on-single-page small layout="prev, pager, next"
                                   :page-size="pager.pageSize" :current-page="pager.page" :total="pager.total"></el-pagination>
                </div>
            </div>
            <div style="flex: 1;height: 100%;">
                <router-view></router-view>
            </div>
        </el-row>
        <add-database-table-dialog :visible.sync="addTableDialogVisible" :project-id="projectId" :database-id="databaseId"
                                   @success="handleTableAddSuccess" @cancel="handleTableAddCancel"></add-database-table-dialog>
    </el-row>
</template>

<script>

    import ListMenu from "../../../../../components/listmenu/ListMenu";
    import ListMenuItem from "../../../../../components/listmenu/ListMenuItem";
    import AddDatabaseTableDialog from "./AddDatabaseTableDialog";

    import apis from "../../../../../api/apis";

    export default {
        name: 'ViewDatabase',
        components: {
            ListMenu,
            ListMenuItem,
            AddDatabaseTableDialog
        },
        provide() {
            return {
                root: this
            }
        },
        data() {
            return {
                addTableDialogVisible: false,
                tableListActiveIndex: '',
                tables: [],
                databaseName: this.$route.params.databaseName,
                currentTableData: {},
                currentOperation: '查看',
                dbType: this.$route.params.type,
                indexesOptions: [],
                fieldTypeOptions: [],
                tableSearchText: '',
                tableLoading: false,
                pager: {
                    pageSize: 0,
                    page: 1,
                    total: 0
                },
            }
        },
        mounted() {
            this.tableListActiveIndex = this.$route.path;
            if(!this.databaseName) {
                this.handleBack();
                return;
            }
            this.listDatabaseTables();
        },
        watch: {
            '$route.path'(val) {
                this.tableListActiveIndex = val;
            }
        },
        computed: {
            projectName() {
                return this.$store.state.projectName;
            },
            projectId() {
                return this.$store.state.projectId;
            },
            databaseId() {
                return this.$route.params.dbId;
            }
        },
        methods: {
            handleBack() {
                this.$router.replace({path: `/project/${this.projectId}/db`});
            },
            changeBreadcrumb(operation) {
                this.currentOperation = operation;
            },
            handleTableClick(row) {
                this.changeBreadcrumb('查看');
                this.currentTableData = row;
            },
            handleAddTable() {
                this.addTableDialogVisible = true;
            },
            handleTableAddSuccess() {
                this.addTableDialogVisible = false;
                this.listDatabaseTables();
            },
            handleTableAddCancel() {
                this.addTableDialogVisible = false;
            },
            listDatabaseTables(useGlobalLoading = false) {
                this.tableLoading = true;
                apis.listProjectDataTables(this.projectId, this.databaseId,
                    {tableName: this.tableSearchText}, {useLoading: useGlobalLoading}).then(data => {
                    this.tableLoading = false;
                    if(data.code === 1) {
                        this.pager.pageSize = data.data.limit;
                        this.pager.page = data.data.page;
                        this.pager.total = data.data.total;
                        this.tables = data.data.records;
                    } else {
                        this.$message.error(data.message);
                    }
                });
            }
        }
    }

</script>

<style>

</style>
