import request from '@/utils/request';
import config from '@/config';

// 查询app 文章 话题 分享
export async function searchReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/label/search_list`, { data: params });
}

// 查询热门标签 以及 搜索标签
export async function queryHotLabelReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/label/get_hot_list`, { data: params });
}

// X友分享提交
export async function submitXShareReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/-----`, { data: params });
}
