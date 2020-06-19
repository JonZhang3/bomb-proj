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
        projectName: '不知道',
    },
    mutations: {
        setProjectName(state) {
            // state.projectName;
        }
    }
});

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');
