import 'whatwg-fetch';
import { getParamsString } from '@/utils/utils';
import config from '@/config/index';

const baseUrl = 'http://restapi.amap.com';
const key = config.amapWebKey;

// 文档参考：https://lbs.amap.com/api/webservice/summary

function amapRequest(url) {
  return fetch(url)
    .then(res => res.json())
    .catch(err => console.log('err:', err));
}

// 关键字搜索
export async function amapSearchApi(params) {
  const defaultData = { key, children: '1', offset: '20', page: '1', extensions: 'all' };
  return amapRequest(
    `${baseUrl}/v3/place/text?${getParamsString({
      ...params,
      ...defaultData,
    })}`
  );
}
