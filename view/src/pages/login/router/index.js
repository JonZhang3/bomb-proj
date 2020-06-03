import Login from '../components/Login';
import Register from '../components/Register';

import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'login',
        component: Login
    },
    {
        path: '/register',
        name: 'register',
        component: Register
    }
];

export default new VueRouter({
    mode: 'history',
    base: '/login',
    routes
})
