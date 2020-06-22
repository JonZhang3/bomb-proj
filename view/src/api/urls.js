export default {
    login: '/user/login',
    register: '/user/register',
    captcha: '/common/captcha',
    checkCaptcha: '/common/checkCaptcha',
    newProject: '/project',
    listProject: '/project',
    deleteProject: '/project/',
    updateProject: '/project/',
    projectDetail: '/project/',
    listProjectMembers: function(projectId) {
        return `/project/${projectId}/member`
    },
    queryProjectNotExistsUsers: function(projectId) {
        return `/project/${projectId}/not-exists-users`;
    },
    addProjectMembers: function(projectId) {
        return `/project/${projectId}/member`;
    },
    listProjectPermissions: '/project/permission',

}
