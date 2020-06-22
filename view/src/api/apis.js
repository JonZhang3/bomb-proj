import {get, post, del, put} from '../common/http';
import settings from '../common/settings';
import urls from './urls';

const baseUrl = settings.baseUrl;

export default {
    captchaUrl: baseUrl + '/common/captcha',
    coverBaseUrl: baseUrl + '/static/',
    coverUploadUrl: baseUrl + '/upload/cover',
    checkCaptcha(code) {
        return get(urls.checkCaptcha, {verificationCode: code}, {useLoading: false});
    },
    login(params) {
        return post(urls.login, params);
    },
    register(params) {
        return post(urls.register, params);
    },
    newProject(params) {
        return post(urls.newProject, params);
    },
    queryProjects(params) {
        return get(urls.listProject, params);
    },
    deleteProject(projectId) {
        return del(`${urls.deleteProject}${projectId}`);
    },
    updateProject(projectId, params) {
        return put(`${urls.updateProject}${projectId}`, params);
    },
    getProjectDetail(projectId, params) {
        return get(`${urls.projectDetail}${projectId}`, params);
    },
    queryProjectMembers(projectId, params) {
        return get(urls.listProjectMembers(projectId), params);
    },
    queryProjectNotExistsUsers(projectId, params, configs) {
        return get(urls.queryProjectNotExistsUsers(projectId), params, configs);
    },
    addProjectMembers(projectId, userIds) {
        return get(urls.addProjectMembers(projectId), {userIds: userIds});
    }
}
