import Vue from 'vue';
import VueRouter from 'vue-router';

import Index from '../components/Index';
import Projects from "../components/Projects";

import ProjectDetail from "../components/ProjectDetail";
import ProjectDashboard from "../components/project/detail/ProjectDashboard";
import ProjectMember from "../components/project/member/ProjectMember";
import ProjectCode from "../components/project/code/ProjectCode";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'index',
        component: Index,
        children: [
            {
                path: '/',
                name:'projects',
                component: Projects
            }
        ]
    },
    {
        path: '/project/:id',
        name: 'project-detail',
        component: ProjectDetail,
        children: [
            {
                path: '/',
                name: 'project-dashboard',
                component: ProjectDashboard
            },
            {
                path: 'member',
                name: 'project-member',
                component: ProjectMember
            },
            {
                path: 'code',
                name: 'project-code',
                component: ProjectCode
            }
        ]
    }
];

export default new VueRouter({
    mode: 'history',
    routes
});
