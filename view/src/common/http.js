import axios from 'axios';
import {Loading, Message} from 'element-ui';
import settings from '../common/settings';

axios.defaults.timeout = settings.httpTimeout;
axios.defaults.baseURL = settings.baseUrl;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';

export const get = (url, params, config = {}) => {
    return new Promise((resolve, reject) => {
        axios({
            method: 'get',
            url,

        })
    });
}


