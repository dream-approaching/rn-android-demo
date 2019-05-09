import { submitCommentReq, queryCommentReq, queryChildCommentReq } from '@/services/comment';
import { giveLikelikeReq } from '@/services/common';
import { ToastAndroid } from 'react-native';

export default {
  namespace: 'comment',

  state: {
    commentList: [],
    commentListTotal: 0,
    childCommentList: [],
    childCommentListTotal: 0,
  },

  effects: {
    *queryCommentEffect({ payload, successFn }, { call, put }) {
      try {
        const response = yield call(queryCommentReq, payload);
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.code === 0) {
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
    *queryChildCommentEffect({ payload, successFn }, { call, put }) {
      try {
        const response = yield call(queryChildCommentReq, payload);
        console.log(
          '%cqueryChildCommentReq response:',
          'color: #0e93e0;background: #aaefe5;',
          response
        );
        if (response && response.code === 0) {
          yield put({
            type: 'saveChildCommentList',
            payload: response.data || [],
            isFirstPage: payload.isFirst,
          });
          successFn && successFn();
        } else {
          ToastAndroid.show(response.msg, ToastAndroid.SHORT);
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    *submitCommentEffect({ payload, successFn, failedFn, finallyFn }, { call }) {
      try {
        const response = yield call(submitCommentReq, payload);
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.code === 0) {
          successFn && successFn();
        } else {
          ToastAndroid.show(response.msg, ToastAndroid.SHORT);
        }
      } catch (err) {
        failedFn && failedFn();
        console.log('err', err);
      } finally {
        finallyFn && finallyFn();
      }
    },
    *giveLikelikeEffect({ payload, successFn }, { call }) {
      try {
        const response = yield call(giveLikelikeReq, payload);
        console.log('%cgiveLikelikeReq response:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.code === 0) {
          successFn && successFn();
        } else {
          ToastAndroid.show(response.msg, ToastAndroid.SHORT);
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
        commentList: isFirstPage ? payload.list : [...state.commentList, ...payload.list],
        commentListTotal: payload.cnt,
      };
    },
    saveChildCommentList(state, { payload, isFirstPage }) {
      return {
        ...state,
        childCommentList: isFirstPage ? payload : [...state.commentList, ...payload],
      };
    },
  },
};
