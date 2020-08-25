<template>
    <el-dialog width="30%" :title="edit ? '修改数据库' : '新增数据库'" v-bind="$attrs"
               class="add-project-member-dialog" top="60px"
               :before-close="handleCancel" :close-on-click-modal="false">
        <el-form :model="dbForm" ref="dbForm" :rules="dbFormRules" label-width="100px" size="medium">
            <el-form-item prop="databaseName" label="数据库名">
                <el-input v-model="dbForm.databaseName"></el-input>
            </el-form-item>
            <el-form-item prop="databaseDesc" label="描述">
                <el-input v-model="dbForm.databaseDesc"></el-input>
            </el-form-item>
            <el-form-item prop="type" label="数据库类型">
                <span v-if="!edit" class="el-form-item__tip"><i class="el-icon-warning"></i> 数据库类型在选择保存后不可修改</span>
                <el-select v-model="dbForm.type" placeholder="请选择" :disabled="edit" style="width: 100%;">
                    <el-option key="mysql" value="mysql" label="MySQL"></el-option>
                    <el-option key="oracle" value="oracle" label="Oracle"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item prop="version" label="数据库版本">
                <el-input v-model="dbForm.version"></el-input>
            </el-form-item>
            <el-tabs v-model="activeTab">
                <el-tab-pane label="开发库" name="dev">
                    <el-form-item prop="dbHost" label="Host">
                        <el-input v-model="dbForm.dbHost"></el-input>
                    </el-form-item>
                    <el-form-item prop="dbPort" label="Port">
                        <el-input v-model="dbForm.dbPort"></el-input>
                    </el-form-item>
                    <el-form-item prop="userName" label="用户名">
                        <el-input v-model="dbForm.userName"></el-input>
                    </el-form-item>
                </el-tab-pane>
                <el-tab-pane label="测试库" name="test">
                    <el-form-item prop="dbHost" label="Host">
                        <el-input v-model="dbForm.testDbHost"></el-input>
                    </el-form-item>
                    <el-form-item prop="dbPort" label="Port">
                        <el-input v-model="dbForm.testDbPort"></el-input>
                    </el-form-item>
                    <el-form-item prop="userName" label="用户名">
                        <el-input v-model="dbForm.testUserName"></el-input>
                    </el-form-item>
                </el-tab-pane>
                <el-tab-pane label="正式库" name="prod">
                    <el-form-item prop="dbHost" label="Host">
                        <el-input v-model="dbForm.prodDbHost"></el-input>
                    </el-form-item>
                    <el-form-item prop="dbPort" label="Port">
                        <el-input v-model="dbForm.prodDbPort"></el-input>
                    </el-form-item>
                    <el-form-item prop="userName" label="用户名">
                        <el-input v-model="dbForm.prodUserName"></el-input>
                    </el-form-item>
                </el-tab-pane>
            </el-tabs>

<!--            <el-form-item v-if="!edit" prop="password" label="密码">-->
<!--                <span class="el-form-item__tip"><i class="el-icon-warning"></i> 密码不会被保存，仅用于初始化数据库信息</span>-->
<!--                <el-input></el-input>-->
<!--            </el-form-item>-->
        </el-form>
        <span slot="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleAdd">{{edit ? '修 改' : '增 加'}}</el-button>
        </span>
    </el-dialog>
</template>

<script>

    import apis from "../../../../../api/apis";

    export default {
        name: 'add-or-edit-project-database-dialog',
        props: {
            projectId: String,
            edit: {
                type: Boolean,
                default: false
            },
            dbData: Object
        },
        data() {
            return {
                activeTab: 'dev',
                dbForm: {
                    id: '',
                    databaseName: '',
                    databaseDesc: '',
                    type: '',
                    dbHost: '',
                    dbPort: '',
                    userName: '',
                    testDbHost: '',
                    testDbPort: '',
                    testUserName: '',
                    prodDbHost: '',
                    prodDbPort: '',
                    prodUserName: '',
                    version: ''
                },
                dbFormRules: {
                    databaseName: [
                        {required: true, message: '请输入数据库名', trigger: 'blur'},
                        {max: 50, message: '数据库名最大长度为 50', trigger: 'blur'}
                    ],
                    databaseDesc: [
                        {max: 255, message: '描述最大长度为 255', trigger: 'blur'}
                    ],
                    type: [
                        {required: true, message: '请选择数据库类型', trigger: 'blur'}
                    ],
                    dbHost: [
                        {required: true, message: '请填写数据库主机名', trigger: 'blur'}
                    ],
                    dbPort: [
                        {required: true, message: '请填写数据库端口号', trigger: 'blur'}
                    ],
                    version: [
                        {max: 50, message: '数据库版本最大长度为 50', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            handleCancel() {
                this.$refs['dbForm'].resetFields();
                this.activeTab = 'dev';
                this.$emit('cancel');
            },
            handleAdd(e) {
                this.$refs['dbForm'].validate(valid => {
                    if(valid) {
                        if(this.edit) {
                            this.updateProjectDatabase(e);
                        } else {
                            this.addProjectDatabase(e);
                        }
                    } else {
                        return false;
                    }
                });
            },
            addProjectDatabase(e) {
                apis.addProjectDatabase(this.projectId, {
                    databaseName: this.dbForm.databaseName,
                    databaseDesc: this.dbForm.databaseDesc,
                    host: this.dbForm.dbHost,
                    port: this.dbForm.dbPort,
                    userName: this.dbForm.userName,
                    version: this.dbForm.version,
                    type: this.dbForm.type
                }).then(data => {
                    if(data.code === 1) {
                        this.$message.success('新增数据库成功');
                        this.activeTab = 'dev';
                        this.$refs['dbForm'].resetFields();
                        this.$emit('success', e);
                    } else {
                        this.$message.error(data.message);
                    }
                });
            },
            updateProjectDatabase(e) {
                apis.updateProjectDatabase(this.projectId, this.dbForm.id, {
                    databaseName: this.dbForm.databaseName,
                    databaseDesc: this.dbForm.databaseDesc,
                    host: this.dbForm.dbHost,
                    port: this.dbForm.dbPort,
                    userName: this.dbForm.userName,
                    version: this.dbForm.version,
                    type: this.dbForm.type
                }).then(data => {
                    if(data.code === 1) {
                        this.$message.success('修改数据库成功');
                        this.activeTab = 'dev';
                        this.$refs['dbForm'].resetFields();
                        this.$emit('success', e);
                    } else {
                        this.$message.error(data.message);
                    }
                });
            }
        },
        watch: {
            'dbData.id'(val) {
                this.dbForm.id = val ? val : '';
            },
            'dbData.databaseName'(val) {
                this.dbForm.databaseName = val ? val : '';
            },
            'dbData.databaseDesc'(val) {
                this.dbForm.databaseDesc = val ? val : '';
            },
            'dbData.type'(val) {
                this.dbForm.type = val ? val : '';
            },
            'dbData.dbHost'(val) {
                this.dbForm.dbHost = val ? val : '';
            },
            'dbData.dbPort'(val) {
                this.dbForm.dbPort = val ? val : '';
            },
            'dbData.userName'(val) {
                this.dbForm.userName = val ? val : '';
            },
            'dbData.version'(val) {
                this.dbForm.version = val ? val : ''
            }
        }
    }

</script>
