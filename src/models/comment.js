import { submitCommentReq, queryCommentReq } from '@/services/comment';
import { ToastAndroid } from 'react-native';

export default {
  namespace: 'comment',

  state: {
    commentList: [],
    commentListTotal: 0,
  },

  effects: {
    *queryCommentEffect({ payload, successFn }, { call, put }) {
      try {
        // const response = yield call(queryCommentReq, payload);
        const response = { success: true, data: { cnt: 10, list: [] } };
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.success) {
          yield put({
            type: 'saveCommentList',
            payload: response.data || {},
            isFirstPage: payload.isFirst,
          });
          successFn && successFn();
        } else {
          ToastAndroid.show(response.msg, ToastAndroid.LONG);
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    *submitCommentEffect({ payload, successFn, failedFn, finallyFn }, { call }) {
      try {
        const response = yield call(submitCommentReq, payload);
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.success) {
          successFn && successFn();
        } else {
          ToastAndroid.show(response.msg, ToastAndroid.LONG);
        }
      } catch (err) {
        failedFn && failedFn();
        console.log('err', err);
      } finally {
        finallyFn && finallyFn();
      }
    },
  },

  reducers: {
    saveCommentList(state, { payload, isFirstPage }) {
      return {
        ...state,
        commentList: isFirstPage ? payload.list : [...state.commentList, ...payload.list],
        commentListTotal: payload.cnt,
      };
    },
  },
};
