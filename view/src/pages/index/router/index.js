import Vue from 'vue';
import VueRouter from 'vue-router';

import Index from '../components/Index';
import Projects from "../components/Projects";

import ProjectDetail from "../components/ProjectDetail";
import ProjectDashboard from "../components/project/detail/ProjectDashboard";
import ProjectMember from "../components/project/member/ProjectMember";
import ProjectCode from "../components/project/code/ProjectCode";
import ProjectDatatable from "../components/project/datatable/ProjectDatatable";
import ViewDatatableFields from "../components/project/datatable/ViewDatatableFields";
import EditDatatableFields from "../components/project/datatable/EditDatatableFields";
import ProjectFiles from "../components/project/files/ProjectFiles";

import NotFound from "../components/NotFound";

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
            },
            {
                path: 'datatable',
                name: 'project-datatable',
                component: ProjectDatatable
            },
            {
                path: 'datatable/:tableId/fields/view',
                name: 'project-datatable-fields-view',
                component: ViewDatatableFields,
                meta: {
                    hasParent: true
                }
            },
            {
                path: 'datatable/:tableId/fields/edit',
                name: 'project-datatable-fields-edit',
                component: EditDatatableFields,
                meta: {
                    hasParent: true
                }
            },
            {
                path: 'files',
                name: 'project-files',
                component: ProjectFiles
            }
        ]
    },
    {
        path: '/404',
        name: '404',
        component: NotFound
    }
];

export default new VueRouter({
    mode: 'history',
    routes
});
