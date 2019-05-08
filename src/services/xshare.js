import request from '@/utils/request';
import config from '@/config';

// 查询X友分享列表
export async function queryXshareListReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/-----`, { data: params });
}

// 查询X友分享内页
export async function queryXshareDetailReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/-----`, { data: params });
}
