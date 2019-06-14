import request from '@/utils/request';
import config from '@/config';

// 获取首页列表
export async function queryCatHomeListReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/content/get_video_recommend`, {
    data: params,
  });
}

// 获取图文内页
export async function queryCatArticleDetailReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/share/get_share_detail`, {
    data: params,
  });
}

// 发布接口
export async function submitPublishReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/share/commit_share`, { data: params });
}

// 打赏接口
export async function setRewardReq(params) {
  return request(`${config.baseUrl}/interface/v1/app/reward/set_reward`, { data: params });
}
