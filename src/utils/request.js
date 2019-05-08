import fetch from 'whatwg-fetch';
// import { ToastAndroid } from 'react-native';
// import { stringify } from 'qs';

const codeMessage = {
  200: '服务器成功返回请求的数据。',
  201: '新建或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
  401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。',
};
function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }
  const errortext = codeMessage[response.status] || response.statusText;
  const error = new Error(errortext);
  error.name = response.status;
  error.response = response;
  throw error;
}

function myFetch(fetchPromise, timeout = 15 * 1000) {
  const abortPromise = new Promise((resolve, reject) => {
    setTimeout(() => {
      const err = new Error('请求超时');
      // ToastAndroid.show('请求超时', ToastAndroid.LONG); // 每次都会执行
      // 告诉原生有异常
      reject(err);
    }, timeout);
  });

  const abortablePromise = Promise.race([fetchPromise, abortPromise]);

  return abortablePromise;
}

/**
 * Requests a URL, returning a promise.
 *
 * @param  {string} url       The URL we want to request
 * @param  {object} [options] The options we want to pass to "fetch"
 * @return {object}           An object containing either "data" or "err"
 */
export default function request(url, options, time) {
  const defaultOptions = {
    credentials: 'include',
    method: 'GET',
  };
  let newOptions = { ...defaultOptions, ...options };
  if (
    newOptions.method === 'POST' ||
    newOptions.method === 'PUT' ||
    newOptions.method === 'DELETE'
  ) {
    if (!(newOptions.body instanceof FormData)) {
      newOptions.headers = {
        Accept: 'application/json',
        'Content-Type': 'application/json; charset=utf-8',
        ...newOptions.headers,
      };
      newOptions.body = JSON.stringify(newOptions.body);
    } else {
      // newOptions.body is FormData
      newOptions.headers = {
        Accept: 'application/json',
        ...newOptions.headers,
      };
    }
  }

  let fetchUrl;

  if (newOptions.method === 'GET') {
    const { data } = newOptions;
    // data.access_token = '3a93d69719f6e035bdb5c4d3b8a11547'; // token1
    // data.mobilephone = '13564587895'; // 用户手机号1

    data.access_token = 'c3c4db657e05f684f696096ec6f06371'; // token2
    data.mobilephone = '18503068868'; // 用户手机号2

    // data.access_token = 'eb07f80389496cc665ffb93bc059263e'; // token3
    // data.mobilephone = '13613033073'; // 用户手机号4
    data.app_ver = '1'; // app版本
    data.app_ver_code = '1'; // app版本编码
    data.ch = '1'; // 渠道
    data.channel_id = '1'; // 渠道号

    const query = data ? `?params=${JSON.stringify(data)}` : '';
    fetchUrl = `${url}${query}`;
    newOptions = null;
  } else {
    fetchUrl = url;
  }

  return myFetch(fetch(fetchUrl, newOptions), time)
    .then(checkStatus)
    .then(response => response.json())
    .catch(e => {
      const status = e.name;
      if (status === 401) {
        console.log('权限异常 登出');
        return;
      }
      if (status === 403) {
        console.log('403异常');
        return;
      }
      if (status <= 504 && status >= 500) {
        console.log('500异常');
        return;
      }
      if (status >= 404 && status < 422) {
        console.log('404异常');
      }
      console.log(e);
    });
}
