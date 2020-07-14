import Vue from 'vue'
import Vuex from 'vuex';
import ElementUI from 'element-ui';
import '../../common/common-style';
import './style/index.css';
import '@exuanbo/file-icons-js/dist/css/file-icons.min.css';
import router from './router';
import App from './App.vue'
import settings from '../../common/settings';

Vue.use(ElementUI);
Vue.use(Vuex);

Vue.config.productionTip = false;
Vue.prototype.settings = settings;

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
