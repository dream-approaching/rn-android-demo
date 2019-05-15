import {
  queryXshareListReq,
  queryOthershareListReq,
  queryArticleDetailReq,
  queryXshareDetailReq,
  deleteXshareReq,
} from '@/services/xshare';
import { toggleAttentionReq } from '@/services/common';
import Toast from '@/components/Toast';

export default {
  namespace: 'xshare',

  state: {
    articleDetail: {}, // 互动话题内页
    xshareDetail: {}, // X友分享内页
    xshareList: [],
    otherShareList: [],
  },

  effects: {
    // X友分享列表
    *queryXshareListEffect({ payload, successFn, finallyFn }, { call, put }) {
      try {
        const response = yield call(queryXshareListReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveXshareList',
            payload: response.data || [],
            isFirstPage: payload.isFirst,
          });
          successFn && successFn(response.data);
        }
      } catch (err) {
        console.log('err', err);
      } finally {
        finallyFn && finallyFn();
      }
    },
    // 个人页的X友分享列表
    *queryOtherShareListEffect({ payload, successFn, finallyFn }, { call, put }) {
      try {
        const response = yield call(queryOthershareListReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveOthershareList',
            payload: response.data || [],
            isFirstPage: payload.isFirst,
          });
          successFn && successFn(response.data);
        }
      } catch (err) {
        console.log('err', err);
      } finally {
        finallyFn && finallyFn();
      }
    },
    // 关注用户
    *toggleAttentionEffect({ payload, successFn }, { call }) {
      try {
        const response = yield call(toggleAttentionReq, payload);
        if (response && response.code === 0) {
          successFn && successFn();
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    // 删除自己的X友分享
    *deleteXshareEffect({ payload, successFn }, { call }) {
      try {
        const response = yield call(deleteXshareReq, payload);
        if (response && response.code === 0) {
          successFn && successFn();
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    // 互动话题、数字生活研究所、应用推荐内页
    *queryArticleDetailEffect({ payload, successFn }, { call, put }) {
      try {
        const response = yield call(queryArticleDetailReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveArticleDetail',
            payload: response.data || [],
          });
          successFn && successFn();
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    // X友分享内页
    *queryXshareDetailEffect({ payload, successFn }, { call, put }) {
      try {
        const response = yield call(queryXshareDetailReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveXshareDetail',
            payload: response.data || [],
          });
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
    saveXshareList(state, { payload, isFirstPage }) {
      return {
        ...state,
        xshareList: isFirstPage ? payload : [...state.xshareList, ...payload],
      };
    },
    saveOthershareList(state, { payload, isFirstPage }) {
      return {
        ...state,
        otherShareList: isFirstPage ? payload : [...state.otherShareList, ...payload],
      };
    },
    saveArticleDetail(state, { payload }) {
      return {
        ...state,
        articleDetail: payload,
      };
    },
    saveXshareDetail(state, { payload }) {
      return {
        ...state,
        xshareDetail: payload,
      };
    },
  },
};
