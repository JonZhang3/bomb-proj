import axios from 'axios';
import qs from 'querystring';
import {Loading, Message} from 'element-ui';
import settings from '../common/settings';

axios.defaults.timeout = settings.httpTimeout;
axios.defaults.baseURL = settings.baseUrl;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.withCredentials = true;

export const get = (url, params, config = {useLoading: true}) => {
    let loading;
    if(config.useLoading) {
        loading = Loading.service({fullscreen: true});
    }
    return new Promise((resolve, reject) => {
        axios.get(url, {
            params: params
        }).then(res => {
            loading && loading.close();
            resolve(res.data);
        }).catch(err => {
            loading && loading.close();
            reject(err.data);
        });
    });
}

export const post = (url, params, configs = {useLoading: true}) => {
    let loading;
    if(configs.useLoading) {
        loading = Loading.service({fullscreen: true});
    }
    return new Promise((resolve, reject) => {
        axios.post(url, qs.stringify(params), configs)
            .then(res => {
                loading && loading.close();
                resolve(res.data);
            }).catch(err => {
                loading && loading.close();
                reject(err.data);
            });
    });
}


