import {
  queryXshareListReq,
  queryOthershareListReq,
  queryArticleDetailReq,
  queryXshareDetailReq,
  deleteXshareReq,
} from '@/services/xshare';
import { toggleAttentionReq, toggleArticleCollectReq } from '@/services/common';
import Toast from '@/components/Toast';
import { storeXshare } from '@/utils/utils';

export default {
  namespace: 'xshare',

  state: {
    articleDetail: {}, // 互动话题内页
    xshareDetail: {}, // 莓友分享内页
    xshareList: [],
    otherShareList: [],
  },

  effects: {
    // 莓友分享列表
    *queryXshareListEffect({ payload, successFn, finallyFn }, { call, put, select }) {
      try {
        const response = yield call(queryXshareListReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveXshareList',
            payload: response.data || [],
            isFirstPage: payload.isFirst,
          });
          let storeXshareObj = {};
          yield select(state => {
            storeXshareObj = storeXshare(response.data, state);
          });
          yield put({
            type: 'global/saveXshareData',
            payload: storeXshareObj,
          });
          successFn && successFn(response.data);
        }
      } catch (err) {
        console.log('err', err);
      } finally {
        finallyFn && finallyFn();
      }
    },
    // 个人页的莓友分享列表
    *queryOtherShareListEffect({ payload, successFn, finallyFn }, { call, put, select }) {
      try {
        const response = yield call(queryOthershareListReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveOthershareList',
            payload: response.data || [],
            isFirstPage: payload.isFirst,
          });
          let storeXshareObj = {};
          yield select(state => {
            storeXshareObj = storeXshare(response.data, state);
          });
          yield put({
            type: 'global/saveXshareData',
            payload: storeXshareObj,
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
    // 删除自己的莓友分享
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
    // 互动话题、数字生活研究所、应用推荐 切换收藏
    *toggleArticleCollectEffect({ payload, successFn }, { call }) {
      try {
        const response = yield call(toggleArticleCollectReq, payload);
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
    // 莓友分享内页
    *queryXshareDetailEffect({ payload, successFn }, { call, put, select }) {
      try {
        const response = yield call(queryXshareDetailReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveXshareDetail',
            payload: response.data || {},
          });
          let storeXshareObj = {};
          yield select(state => {
            storeXshareObj = storeXshare([response.data], state);
          });
          yield put({
            type: 'global/saveXshareData',
            payload: storeXshareObj,
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
