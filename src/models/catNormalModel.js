import { toggleAttentionReq, toggleLikeReq } from '@/services/common';
import { setRewardReq } from '@/services/catApi';
import Toast from '@/components/Toast';

export default {
  namespace: 'catNormal',

  state: {},

  effects: {
    // 关注用户
    *toggleAttentionEffect({ payload, successFn }, { call }) {
      try {
        const response = yield call(toggleAttentionReq, payload);
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.code === 0) {
          successFn && successFn();
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    // 点赞
    *toggleLikeEffect({ payload, successFn }, { call }) {
      try {
        const response = yield call(toggleLikeReq, payload);
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.code === 0) {
          successFn && successFn();
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    // 打赏
    *setRewardEffect({ payload, successFn }, { call }) {
      try {
        const response = yield call(setRewardReq, payload);
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.code === 0) {
          successFn && successFn();
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        console.log('err', err);
      }
    },
  },

  reducers: {
    saveCommentList(state, { payload, isFirstPage }) {
      return {
        ...state,
        commentList: isFirstPage ? payload.list || [] : [...state.commentList, ...payload.list],
        commentListTotal: payload.cnt || 0,
      };
    },
  },
};
