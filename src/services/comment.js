import request from '@/utils/request';
import config from '@/config';

// 提交评论
export async function submitCommentReq(params) {
  return request(`${config.baseUrl2}/interface/v1/app/comment/commit_comment`, { data: params });
}

// 获取一级评论
export async function queryCommentReq(params) {
  return request(`${config.baseUrl2}/interface/v1/app/comment/get_comment_list`, { data: params });
}

// 获取子级评论
export async function queryChildCommentReq(params) {
  return request(`${config.baseUrl2}/interface/v1/app/comment/get_comment_detaillist`, {
    data: params,
  });
}

// 获取所有评论
export async function queryAllCommentReq(params) {
  return request(`${config.baseUrl2}/interface/v1/app/comment/get_comment_data`, { data: params });
}
