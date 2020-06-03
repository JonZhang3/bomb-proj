<template>
    <div class="panel">
        <blockquote class="panel-title"><span>登录</span></blockquote>
        <el-form :model="registerForm" style="margin-top: 10px;">
            <el-form-item>
                <el-input v-model="registerForm.userName" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="registerForm.nickName" placeholder="昵称/真实姓名"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="registerForm.phone" placeholder="手机号"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="registerForm.password" type="password" placeholder="密码（不少于6位）"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="registerForm.rpassword" type="password" placeholder="确认密码"></el-input>
            </el-form-item>
            <el-form-item>
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

    import settings from '../../../common/settings';

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
                imageCodeUrl: `${settings.baseUrl}/common/captcha`
            };
        },
        methods: {
            changeCode() {
                this.imageCodeUrl = `${settings.baseUrl}/common/captcha?${new Date().getTime()}`
            },
            register() {
                alert('2');
            },
            toLoginPage() {
                this.$router.push('/');
            }
        }
    }
</script>
