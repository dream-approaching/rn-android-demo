import request from '@/utils/request';
import config from '@/config';

// 查询莓友分享列表
export async function queryXshareListReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/share/get_share_list`, { data: params });
}

// 查询个人页面分享列表
export async function queryOthershareListReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/content/get_other_sharelist`, {
    data: params,
  });
}

// 查询互动话题、数字生活研究所、应用推荐内页
export async function queryArticleDetailReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/content/get_app_recommend_detail`, {
    data: params,
  });
}

// 查询莓友分享内页
export async function queryXshareDetailReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/content/get_app_share_detail`, {
    data: params,
  });
}

// 删除自己的莓友分享
export async function deleteXshareReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/content/del_app_share`, {
    data: params,
  });
}
