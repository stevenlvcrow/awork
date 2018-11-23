import axios from 'axios';
import jsencrypt from 'jsencrypt'
import sha256 from 'crypto-js/sha256';
import store from '../common/vuex/index'
import { Message } from 'element-ui';

let base  = process.env.NODE_ENV === 'development' ? 'http://localhost:63000/demo' : '';

// 请求时的拦截
axios.interceptors.request.use(function (config) {
    return config;
}, function (error) {
    // 当请求异常时做一些处理
    return Promise.reject(error);
});


export default base;