import request from '@/utils/request';
import config from '@/config';

// 点赞
export async function toggleLikeReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/fabulous/set_fabulous`, { data: params });
}

// 关注
export async function toggleAttentionReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/fabulous/set_fabulous`, { data: params });
}
