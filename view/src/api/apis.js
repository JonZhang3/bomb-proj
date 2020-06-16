import {get, post} from '../common/http';
import settings from '../common/settings';
import urls from './urls';

const baseUrl = settings.baseUrl;

const captchaUrl = baseUrl + '/common/captcha';

function checkCaptcha(code) {
    return get(urls.checkCaptcha, {verificationCode: code}, {useLoading: false});
}

function login(params) {
    return post(urls.login, params);
}

function register(params) {
    return post(urls.register, params);
}

export default {
    captchaUrl,
    checkCaptcha,
    login,
    register
}
