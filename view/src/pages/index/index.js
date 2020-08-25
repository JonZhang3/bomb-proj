import Vue from 'vue'
import Vuex from 'vuex';
import 'xe-utils'
import VXETable from 'vxe-table'
import VXETablePluginElement from 'vxe-table-plugin-element'
import ElementUI from 'element-ui';
import 'vxe-table/lib/index.css'
import '../../common/common-style';
import './style/index.css';
import '@exuanbo/file-icons-js/dist/css/file-icons.min.css';
import BombTable from '../../components/simpletable/BombTable';
import BombDrawer from "../../components/BombDrawer";
import router from './router';
import App from './App.vue'
import settings from '../../common/settings';

VXETable.use(VXETablePluginElement)

Vue.use(ElementUI);
Vue.use(VXETable);
Vue.component('BombTable', BombTable);
Vue.component('BombDrawer', BombDrawer);
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
