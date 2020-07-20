import Vue from 'vue';
import VueRouter from 'vue-router';

import Index from '../components/Index';
import Projects from "../components/Projects";

import ProjectDetail from "../components/project/ProjectDetail";
import ProjectDashboard from "../components/project/ProjectDashboard";
import ProjectMember from "../components/project/member/ProjectMember";
import ProjectCode from "../components/project/code/ProjectCode";
import ProjectDatabase from "../components/project/datatable/ProjectDatabase";
import ViewDatatableFields from "../components/project/datatable/ViewDatatableFields";
import EditDatatableFields from "../components/project/datatable/EditDatatableFields";
import ViewDatabase from "../components/project/datatable/ViewDatabase";
import ViewDataTable from "../components/project/datatable/ViewDataTable";
import EditDataTable from "../components/project/datatable/EditDataTable";
import ProjectFiles from "../components/project/files/ProjectFiles";
import ProjectAllFiles from "../components/project/files/ProjectAllFiles";
import ProjectRecentFiles from "../components/project/files/ProjectRecentFiles";
import ProjectStarredFiles from "../components/project/files/ProjectStarredFiles";
import ProjectSharedFiles from "../components/project/files/ProjectSharedFiles";
import ProjectFileRecycleBin from "../components/project/files/ProjectFileRecycleBin";

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
                path: 'db',
                name: 'project-database',
                component: ProjectDatabase
            },
            {
                path: 'db/:dbId',
                name: 'project-database-view',
                component: ViewDatabase,
                meta: {
                    hasParent: true
                },
                children: [
                    {
                        path: 'table/:tableId/view',
                        name: 'project-database-table-view',
                        component: ViewDataTable,
                        meta: {
                            hasParent: true,
                            rootMenu: function(id) {
                                return `/project/${id}/db`;
                            }
                        },
                    },
                    {
                        path: 'table/:tableId/edit',
                        name: 'project-database-table-edit',
                        component: EditDataTable,
                        meta: {
                            hasParent: true,
                            rootMenu: function(id) {
                                return `/project/${id}/db`;
                            }
                        },
                    },
                ]
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
                component: ProjectFiles,
                children: [
                    {
                        path: '/',
                        name: 'project-all-files',
                        component: ProjectAllFiles
                    },
                    {
                        path: 'recent',
                        name: 'project-recent-files',
                        component: ProjectRecentFiles,
                        meta: {
                            hasParent: true,
                            rootMenu: function(id) {
                                return `/project/${id}/files`;
                            }
                        }
                    },
                    {
                        path: 'starred',
                        name: 'project-starred-files',
                        component: ProjectStarredFiles,
                        meta: {
                            hasParent: true,
                            rootMenu: function(id) {
                                return `/project/${id}/files`;
                            }
                        }
                    },
                    {
                        path: 'shared',
                        name: 'project-shared-files',
                        component: ProjectSharedFiles,
                        meta: {
                            hasParent: true,
                            rootMenu: function(id) {
                                return `/project/${id}/files`;
                            }
                        }
                    },
                    {
                        path: 'recycle-bin',
                        name: 'project-files-recycle-bin',
                        component: ProjectFileRecycleBin,
                        meta: {
                            hasParent: true,
                            rootMenu: function(id) {
                                return `/project/${id}/files`;
                            }
                        }
                    }
                ]
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
