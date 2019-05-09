import request from '@/utils/request';
import config from '@/config';

// 查询X友分享列表
export async function queryXshareListReq(params) {
  return request(`${config.baseUrl2}/interface/v1/app/share/get_share_list`, { data: params });
}

// 查询个人页面分享列表
export async function queryOthershareListReq(params) {
  return request(`${config.baseUrl2}/interface/v1/app/content/get_other_sharelist`, {
    data: params,
  });
}
