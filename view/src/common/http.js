import axios from 'axios';
import qs from 'querystring';
import {Loading} from 'element-ui';
import settings from '../common/settings';

axios.defaults.timeout = settings.httpTimeout;
axios.defaults.baseURL = settings.baseUrl;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.withCredentials = true;

export const get = (url, params, configs = {useLoading: true, loadingText: ''}) => {
    let loading;
    if(configs.useLoading) {
        loading = Loading.service({fullscreen: true, text: configs.loadingText ? configs.loadingText : '拼命加载中...'});
    }
    return new Promise((resolve, reject) => {
        axios.get(url, {
            params: params
        }).then(res => {
            loading && loading.close();
            resolve(res.data);
        }).catch(err => {
            loading && loading.close();
            reject(err);
        });
    });
}

export const post = (url, params, configs = {useLoading: true, loadingText: ''}) => {
    let loading;
    if(configs.useLoading) {
        loading = Loading.service({fullscreen: true, text: configs.loadingText ? configs.loadingText : '拼命加载中...'});
    }
    return new Promise((resolve, reject) => {
        axios.post(url, qs.stringify(params), configs)
            .then(res => {
                loading && loading.close();
                resolve(res.data);
            }).catch(err => {
                loading && loading.close();
                reject(err);
            });
    });
}

export const del = (url, params, configs = {useLoading: true, loadingText: ''}) => {
    let loading;
    if(configs.useLoading) {
        loading = Loading.service({fullscreen: true, text: configs.loadingText ? configs.loadingText : '拼命加载中...'});
    }
    return new Promise((resolve, reject) => {
        axios.delete(url, {
            params: params
        }).then(res => {
            loading && loading.close();
            resolve(res.data);
        }).catch(err => {
            loading && loading.close();
            reject(err);
        });
    });
}

export const put = (url, params, configs = {useLoading: true, loadingText: ''}) => {
    let loading;
    if(configs.useLoading) {
        loading = Loading.service({fullscreen: true, text: configs.loadingText ? configs.loadingText : '拼命加载中...'});
    }
    return new Promise((resolve, reject) => {
        axios.put(url, qs.stringify(params), configs).then(res => {
            loading && loading.close();
            resolve(res.data);
        }).catch(err => {
            loading && loading.close();
            reject(err);
        });
    });
}
