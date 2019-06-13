import { queryNoticeListReq } from '@/services/common';
import Toast from '@/components/Toast';
import { NOTICE_TYPE } from '@/config/constants';

export default {
  namespace: 'notice',

  state: {
    commentList: [],
    likeList: [],
    systemList: [],
  },

  effects: {
    // 通知列表
    *queryNoticeListEffect({ payload, successFn, finallyFn }, { call, put }) {
      try {
        const response = yield call(queryNoticeListReq, payload);
        let reducers;
        if (response && response.code === 0) {
          switch (+payload.notice_type) {
            case NOTICE_TYPE.comment:
              reducers = 'saveCommentList';
              break;
            case NOTICE_TYPE.like:
              reducers = 'saveLikeList';
              break;
            default:
              reducers = 'saveSystemList';
          }
          yield put({
            type: reducers,
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
  },

  reducers: {
    saveCommentList(state, { payload, isFirstPage }) {
      return {
        ...state,
        commentList: isFirstPage ? payload : [...state.commentList, ...payload],
      };
    },
    saveLikeList(state, { payload, isFirstPage }) {
      return {
        ...state,
        likeList: isFirstPage ? payload : [...state.likeList, ...payload],
      };
    },
    saveSystemList(state, { payload, isFirstPage }) {
      return {
        ...state,
        systemList: isFirstPage ? payload : [...state.systemList, ...payload],
      };
    },
    clearNotice() {
      return {
        commentList: [],
        likeList: [],
        systemList: [],
      };
    },
  },
};
