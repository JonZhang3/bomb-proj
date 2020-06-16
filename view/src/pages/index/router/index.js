import Vue from 'vue';
import VueRouter from 'vue-router';

import Index from '../components/Index';
import Projects from "../components/Projects";

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
    }
];

export default new VueRouter({
    mode: 'history',
    routes
});
