import {get, post, del, put} from '../common/http';
import settings from '../common/settings';
import urls from './urls';

const baseUrl = settings.baseUrl;

const captchaUrl = baseUrl + '/common/captcha';
const coverBaseUrl = baseUrl + '/static/';
const coverUploadUrl = baseUrl + '/upload/cover';

function checkCaptcha(code) {
    return get(urls.checkCaptcha, {verificationCode: code}, {useLoading: false});
}

function login(params) {
    return post(urls.login, params);
}

function register(params) {
    return post(urls.register, params);
}

function newProject(params) {
    return post(urls.newProject, params);
}

function queryProjects(params) {
    return get(urls.listProject, params);
}

function deleteProject(projectId) {
    return del(`${urls.deleteProject}${projectId}`);
}

function updateProject(projectId, params) {
    return put(`${urls.updateProject}${projectId}`, params);
}

export default {
    captchaUrl,
    coverBaseUrl,
    coverUploadUrl,
    checkCaptcha,
    login,
    register,
    newProject,
    queryProjects,
    updateProject,
}
