import Vue from 'vue'
import Vuex from 'vuex';
import ElementUI from 'element-ui';
import '../../common/common-style';
import './style/index.css';
import router from './router';
import App from './App.vue'

Vue.use(ElementUI);
Vue.use(Vuex);

Vue.config.productionTip = false;

const store = new Vuex.Store({
    state: {
        projectName: '',
        projectId: '',
    },
    mutations: {
        setProjectName(state, newProjectName) {
            state.projectName = newProjectName;
        },
        setProjectId(state, newProjectId) {
            state.projectId = newProjectId;
        }
    }
});

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');
