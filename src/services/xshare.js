import request from '@/utils/request';
import config from '@/config';

// 查询热门标签 以及 搜索标签
export async function queryHotLabelReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/label/get_hot_list`, { data: params });
}
