export default {
    login: '/api/user/login',
    register: '/api/user/register',
    captcha: '/api/common/captcha',
    checkCaptcha: '/api/common/checkCaptcha',
    newProject: '/api/project',
    listProject: '/api/project',
    deleteProject: '/api/project/',
    updateProject: '/api/project/',
    projectDetail: '/api/project/',
    listProjectMembers: function(projectId) {
        return `/api/project/${projectId}/member`
    },
    queryProjectNotExistsUsers: function(projectId) {
        return `/api/project/${projectId}/not-exists-users`;
    },
    addProjectMembers: function(projectId) {
        return `/api/project/${projectId}/member`;
    },
    listProjectPermissions: '/api/project/permission',
    listProjectCode(projectId) {
        return `/api/project/${projectId}/code`;
    },
    addProjectCode(projectId) {
        return `/api/project/${projectId}/code`;
    },
    updateProjectCode(projectId, codeId) {
        return `/api/project/${projectId}/code/${codeId}`;
    },
    deleteProjectCode(projectId, codeId) {
        return `/api/project/${projectId}/code/${codeId}`;
    }
}
