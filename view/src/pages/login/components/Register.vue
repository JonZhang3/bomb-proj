<template>
    <div class="panel">
        <blockquote class="panel-title"><span>登录</span></blockquote>
        <el-form ref="registerForm" :model="registerForm" :rules="registerFormRules" style="margin-top: 10px;">
            <el-form-item prop="userName">
                <el-input v-model="registerForm.userName" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="nickName">
                <el-input v-model="registerForm.nickName" placeholder="昵称/真实姓名"></el-input>
            </el-form-item>
            <el-form-item prop="email">
                <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
            </el-form-item>
            <el-form-item prop="phone">
                <el-input v-model="registerForm.phone" placeholder="手机号"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input v-model="registerForm.password" type="password" placeholder="密码（不少于6位）"></el-input>
            </el-form-item>
            <el-form-item prop="rpassword">
                <el-input v-model="registerForm.rpassword" type="password" placeholder="确认密码"></el-input>
            </el-form-item>
            <el-form-item prop="verificationCode">
                <el-row>
                    <el-col :span="18">
                        <el-input v-model="registerForm.verificationCode" placeholder="验证码"></el-input>
                    </el-col>
                    <el-col :span="6">
                        <img alt="验证码" :src="imageCodeUrl" @click="changeCode" title="点击刷新">
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="register" style="width: 100%;">注册</el-button>
            </el-form-item>
        </el-form>
        <div style="text-align: right;font-size: 14px;color: #808080;">
            <span class="cursor" @click="toLoginPage">已有账号，去登录 >></span>
        </div>
    </div>
</template>

<script>

    import apis from '../../../api/apis';
    import utils from '../../../common/utils';

    export default {
        name: 'Register',
        data() {
            return {
                registerForm: {
                    userName: '',
                    nickName: '',
                    password: '',
                    rpassword: '',
                    verificationCode: '',
                    email: '',
                    phone: ''
                },
                registerFormRules: {
                    userName: [
                        {required: true, message: '请输入用户名', trigger: 'blur'}
                    ],
                    nickName: [
                        {required: true, message: '请输入昵称/真实姓名', trigger: 'blur'}
                    ],
                    email: [
                        {required: true, message: '请输入邮箱地址', trigger: 'blur'}
                    ],
                    phone: [
                        {required: true, message: '请输入手机号', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {
                            validator: (rule, value, callback) => {
                                if(this.registerForm.rpassword !== '') {
                                    this.$refs['registerForm'].validateField('rpassword');
                                }
                                callback();
                            },
                            trigger: 'blur'
                        }
                    ],
                    rpassword: [
                        {
                            validator: (rule, value, callback) => {
                                if(this.registerForm.password !== value) {
                                    callback('两次输入密码不一致');
                                } else {
                                    callback();
                                }
                            },
                            trigger: 'blur'
                        }
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
            };
        },
        methods: {
            changeCode() {
                this.imageCodeUrl = `${apis.captchaUrl}?${new Date().getTime()}`;
                this.registerForm.verificationCode = '';
            },
            register() {
                this.$refs['registerForm'].validate((valid) => {
                    if(valid) {
                        apis.register({
                            userName: this.registerForm.userName,
                            nickName: this.registerForm.nickName,
                            password: utils.md5(this.registerForm.password),
                            rpassword: utils.md5(this.registerForm.rpassword),
                            verificationCode: this.registerForm.verificationCode,
                            email: this.registerForm.email,
                            phone: this.registerForm.phone
                        }).then(data => {
                            if(data.code === 1) {
                                this.$message.success('注册成功，请登录');
                                this.$router.replace('/');
                            } else {
                                this.$message.error(data.message);
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
            toLoginPage() {
                this.$router.push('/');
            }
        }
    }
</script>
