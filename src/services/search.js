import request from '@/utils/request';
import config from '@/config';

// 查询历史搜索
export async function queryHistorySearchReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/label/get_his_search_list`, { data: params });
}

// 搜索 APP，文章，互动话题，X友分享
export async function searchReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/label/search_list`, { data: params });
}
