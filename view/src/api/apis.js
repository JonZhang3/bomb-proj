import {get, post, del, put} from '../common/http';
import settings from '../common/settings';

const baseUrl = settings.baseUrl;

export default {
    captchaUrl: baseUrl + '/api/common/captcha',
    coverBaseUrl: baseUrl + '/static/',
    coverUploadUrl: baseUrl + '/api/upload/cover',
    checkCaptcha(code) {
        return get('/api/common/checkCaptcha', {verificationCode: code}, {useLoading: false});
    },
    login(params) {// 登录
        return post('/api/user/login', params);
    },
    register(params) {// 注册
        return post('/api/user/register', params);
    },
    newProject(params) {// 新建项目
        return post('/api/project', params);
    },
    queryProjects(params) {// 分页查询项目列表
        return get('/api/project', params);
    },
    deleteProject(projectId) {// 删除项目
        return del(`/api/project/${projectId}`);
    },
    updateProject(projectId, params) {// 更新项目
        return put(`/api/project/${projectId}`, params);
    },
    getProjectDetail(projectId, params) {// 获取项目详情
        return get(`/api/project/${projectId}`, params);
    },
    queryProjectMembers(projectId, params) {// 查询项目成员
        return get(`/api/project/${projectId}/member`, params);
    },
    queryProjectNotExistsUsers(projectId, params, configs) {// 查询不存在该项目中的用户
        return get(`/api/project/${projectId}/not-exists-users`, params, configs);
    },
    addProjectMembers(projectId, userIds, permissions) {// 增加项目成员
        return post(`/api/project/${projectId}/member`, {userIds: userIds, permissions: permissions});
    },
    listProjectPermissions() {
        return get('/api/project/permission');
    },
    listProjectCode(projectId, params) {
        return get(`/api/project/${projectId}/code`, params);
    },
    addProjectCode(projectId, params) {
        return post(`/api/project/${projectId}/code`, params);
    },
    updateProjectCode(projectId, codeId, params) {
        return put(`/api/project/${projectId}/code/${codeId}`, params);
    },
    deleteProjectCode(projectId, codeId) {
        return del(`/api/project/${projectId}/code/${codeId}`);
    },
    listProjectDataTables(projectId, params, configs) {
        return get(`/api/project/${projectId}/datatable`, params, configs);
    },
    addProjectDataTable(projectId, params) {
        return post(`/api/project/${projectId}/datatable`, params);
    },
    deleteProjectDataTable(projectId, tableId) {
        return del(`/api/project/${projectId}/datatable/${tableId}`);
    },
    updateProjectDataTable(projectId, tableId, params) {
        return put(`/api/project/${projectId}/datatable/${tableId}`, params);
    },
    getDataTableIndexes(dbType, configs) {
        return get('/api/project/field-indexes', {dbType}, configs);
    },
    getDataTableFieldTypes(dbType, configs) {
        return get('/api/project/field-types', {dbType}, configs);
    }
}
