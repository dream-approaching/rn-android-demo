import { queryCommentReq, submitCommentReq, queryChildCommentReq } from '@/services/comment';
import Toast from '@/components/Toast';

export default {
  namespace: 'catComment',

  state: {
    commentList: [],
    childCommentList: [],
    commentListTotal: 0,
  },

  effects: {
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
        if (response && response.code === 0) {
          successFn && successFn(response);
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
  },

  reducers: {
    saveCommentList(state, { payload, isFirstPage }) {
      return {
        ...state,
        commentList: isFirstPage ? payload.list || [] : [...state.commentList, ...payload.list],
        commentListTotal: payload.cnt || 0,
      };
    },
    saveChildCommentList(state, { payload, isFirstPage }) {
      return {
        ...state,
        childCommentList: isFirstPage ? payload || [] : [...state.childCommentList, ...payload],
      };
    },
  },
};
