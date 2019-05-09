import request from '@/utils/request';
import config from '@/config';

// 点赞
export async function giveLikelikeReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/label/get_hot_list`, { data: params });
}
