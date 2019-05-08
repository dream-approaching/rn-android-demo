import request from '@/utils/request';
import config from '@/config';

// 查询热门搜索
export async function queryHotSearchReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/-----`, { data: params });
}
