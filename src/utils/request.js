// import fetch from 'whatwg-fetch';
import 'whatwg-fetch';
import { GetUserInfo } from '@/components/NativeModules';
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

function myFetch(fetchPromise, timeout = 25 * 1000) {
  const abortPromise = new Promise((resolve, reject) => {
    setTimeout(() => {
      const err = new Error('请求超时');
      err.name = 'timeout';
      reject(err);
    }, timeout);
  });

  const abortablePromise = Promise.race([fetchPromise, abortPromise]);
  console.log('333');

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
    const globalParams = {
      // access_token: '3a93d69719f6e035bdb5c4d3b8a11547',
      // mobilephone: '13564587895',
      access_token: GetUserInfo.token,
      mobilephone: GetUserInfo.phone,
      app_ver: '1',
      app_ver_code: '1',
      ch: '1',
      channel_id: '1',
    };
    const data = { ...globalParams, ...newOptions.data };

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
      if (status === 'timeout') {
        console.log('%c请求超时:', 'color: #0e93e0;background: #aaefe5;', `${fetchUrl}:请求超时`);
      }
      console.log(e);
    });
}
