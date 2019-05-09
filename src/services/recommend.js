import request from '@/utils/request';
import config from '@/config';

// 查询以及搜索 热门分类
export async function queryHotClassifyReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/label/get_hot_list`, { data: params });
}

// X友分享提交
export async function submitXShareReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/-----`, { data: params });
}
