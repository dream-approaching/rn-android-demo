import {
  submitCommentReq,
  queryCommentReq,
  queryChildCommentReq,
  queryAllCommentReq,
} from '@/services/comment';
import { toggleLikeReq } from '@/services/common';
import Toast from '@/components/Toast';

export default {
  namespace: 'comment',

  state: {
    allCommentList: [],
    allCommentListTotal: 0,
    commentList: [],
    commentListTotal: 0,
    childCommentList: [],
    childCommentListTotal: 0,
  },

  effects: {
    *queryAllCommentEffect({ payload, successFn, finallyFn }, { call, put }) {
      try {
        const response = yield call(queryAllCommentReq, payload);
        console.log('%cpayload:', 'color: #0e93e0;background: #aaefe5;', payload);
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.code === 0) {
          yield put({
            type: 'saveAllCommentList',
            payload: response.data || {},
            isFirstPage: payload.isFirst,
          });
          successFn && successFn(response.data.list);
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        console.log('err', err);
      } finally {
        finallyFn && finallyFn();
      }
    },
    *queryCommentEffect({ payload, successFn, finallyFn }, { call, put }) {
      try {
        const response = yield call(queryCommentReq, payload);
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.code === 0) {
          yield put({
            type: 'saveCommentList',
            payload: response.data || {},
            isFirstPage: payload.isFirst,
          });
          successFn && successFn(response.data.list);
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        console.log('err', err);
      } finally {
        finallyFn && finallyFn();
      }
    },
    *queryChildCommentEffect({ payload, successFn, finallyFn }, { call, put }) {
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
          successFn && successFn(response.data);
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        console.log('err', err);
      } finally {
        finallyFn && finallyFn();
      }
    },
    *submitCommentEffect({ payload, successFn, failedFn, finallyFn }, { call }) {
      try {
        const response = yield call(submitCommentReq, payload);
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.code === 0) {
          successFn && successFn();
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        failedFn && failedFn();
        console.log('err', err);
      } finally {
        finallyFn && finallyFn();
      }
    },
    *toggleLikeEffect({ payload, successFn }, { call }) {
      try {
        const response = yield call(toggleLikeReq, payload);
        console.log('%cgiveLikelikeReq response:', 'color: #0e93e0;background: #aaefe5;', response);
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
    saveAllCommentList(state, { payload, isFirstPage }) {
      return {
        ...state,
        allCommentList: isFirstPage ? payload.list : [...state.allCommentList, ...payload.list],
        allCommentListTotal: payload.cnt,
      };
    },
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
        childCommentList: isFirstPage ? payload : [...state.childCommentList, ...payload],
      };
    },
  },
};
