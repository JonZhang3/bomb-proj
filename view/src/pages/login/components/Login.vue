<template>
    <div class="panel">
        <blockquote class="panel-title"><span>登录</span></blockquote>
        <el-form ref="loginForm" :model="loginForm" :rules="loginFormRules" style="margin-top: 10px;">
            <el-form-item prop="userName">
                <el-input v-model="loginForm.userName" placeholder="用户名/邮箱/手机号"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input v-model="loginForm.password" type="password" placeholder="密码（不少于6位）"></el-input>
            </el-form-item>
            <el-form-item prop="verificationCode" style="margin-bottom: 0;">
                <el-row>
                    <el-col :span="18">
                        <el-input v-model="loginForm.verificationCode" placeholder="验证码"></el-input>
                    </el-col>
                    <el-col :span="6">
                        <img alt="验证码" :src="imageCodeUrl" @click="changeCode" title="点击刷新">
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item style="text-align: right;margin-bottom: 0;">
                <el-switch v-model="loginForm.remberPwd" active-text="记住密码"></el-switch>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="login" style="width: 100%;">登录</el-button>
            </el-form-item>
        </el-form>
        <div style="text-align: right;font-size: 14px;color: #808080;">
            <span class="cursor" @click="toRegisterPage">没有账号，去注册 >></span>
        </div>
    </div>
</template>

<script>

    import apis from '../../../api/apis';
    import utils from '../../../common/utils';

    export default {
        name: 'Login',
        data() {
            return {
                loginForm: {
                    userName: '',
                    password: '',
                    verificationCode: '',
                    remberPwd: false
                },
                loginFormRules: {
                    userName: [
                        {required: true, message: '请输入用户名', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ],
                    verificationCode: [
                        {required: true, message: '请输入验证码', trigger: 'blur'},
                        {
                            validator: (rule, value, callback) => {
                                apis.checkCaptcha(value).then(data => {
                                    if(data.code === -1) {
                                        callback(new Error(data.message));
                                    } else {
                                        callback();
                                    }
                                });
                            },
                            trigger: 'blur'
                        }
                    ]
                },
                imageCodeUrl: apis.captchaUrl
            }
        },
        methods: {
            changeCode() {
                this.imageCodeUrl = `${apis.captchaUrl}?${new Date().getTime()}`
                this.loginForm.verificationCode = '';
            },
            login() {
                this.$refs['loginForm'].validate((valid) => {
                    console.log(valid);
                    if(valid) {
                        apis.login({
                            userName: this.loginForm.userName,
                            verificationCode: this.loginForm.verificationCode,
                            password: utils.md5(this.loginForm.password),
                            remberPwd: this.loginForm.remberPwd ? 'yes' : 'no'
                        }).then(data => {
                            if(data.code === 1) {
                                this.$message.success('登录成功');
                            } else {
                                this.$message.error(data.message);
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
            toRegisterPage() {
                this.$router.push('register');
            }
        }
    }
</script>
