<template>
    <el-row class="main-content-root"
            style="display: flex;flex-direction: column;height: 100%;padding: 20px 0 0 0;">
        <el-row>
            <el-page-header @back="handleBack" style="display: flex;align-items: center;padding-left: 20px;">
                <el-breadcrumb slot="content" separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item>项目【{{projectName}}】</el-breadcrumb-item>
                    <el-breadcrumb-item>数据库【{{databaseName}}】</el-breadcrumb-item>
                </el-breadcrumb>
            </el-page-header>
        </el-row>
        <el-row style="margin-top: 10px;flex: 1;display: flex;height: 100%;overflow: hidden;">
            <div style="min-width: 200px;border-right: 1px solid #DCDFE6;height: 100%">
                <list-menu router>
                    <list-menu-item v-for="(item, i) in tables"
                                    :key="item.id"
                                    :index="`/project/${$route.params.id}/db/${$route.params.dbId}/table/${item.id}`">
                        <i class="el-icon-s-grid"></i>
                        <span>{{item.tableName}}</span>
                        <i class="el-icon-edit" style="position: absolute;right: 0;"></i>
                    </list-menu-item>
                </list-menu>
            </div>
            <div style="flex: 1;height: 100%;">
                <router-view></router-view>
            </div>
        </el-row>
    </el-row>
</template>

<script>

    import ListMenu from "../../../../../components/listmenu/ListMenu";
    import ListMenuItem from "../../../../../components/listmenu/ListMenuItem";
    import apis from "../../../../../api/apis";

    export default {
        name: 'ViewDatabase',
        components: {
            ListMenu,
            ListMenuItem
        },
        props: {

        },
        data() {
            return {
                tables: [],
                databaseName: this.$route.params.databaseName,
                dbType: this.$route.params.type,
                indexesOptions: [],
                fieldTypeOptions: []
            }
        },
        mounted() {
            if(!this.databaseName) {
                this.handleBack();
                return;
            }
            this.init(() => {
                this.listDatabaseTables();
            });
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
            init(callback) {
                const loading = this.$loading({fullscreen: true});
                Promise.all([
                    apis.getDataTableIndexes(this.dbType, {useLoading: false}),
                    apis.getDataTableFieldTypes(this.dbType, {useLoading: false})
                ]).then(datas => {
                    loading.close();
                    if(datas && datas.length === 2) {
                        let initSuccess = true;
                        if(datas[0].code === 1) {
                            this.indexesOptions = datas[0].data;
                        } else {
                            initSuccess = false;
                            this.$message.error(datas[0].message);
                        }
                        if(datas[1].code === 1) {
                            this.fieldTypeOptions = datas[1].data;
                        } else {
                            initSuccess = false;
                            this.$message.error(datas[1].message);
                        }
                        if(initSuccess) {
                            callback && callback();
                        }
                    } else {
                        this.$message.error("获取初始化信息失败");
                    }
                });
            },
            listDatabaseTables() {
                apis.listProjectDataTables(this.projectId, this.databaseId).then(data => {
                    if(data.code === 1) {
                        this.tables = data.data;
                    } else {
                        this.$message.error(data.message);
                    }
                });
            }
        }
    }

</script>
