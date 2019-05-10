import { queryXshareListReq, queryOthershareListReq } from '@/services/xshare';
import { toggleAttentionReq } from '@/services/common';
import Toast from '@/components/Toast';

export default {
  namespace: 'xshare',

  state: {
    xshareList: [],
    otherShareList: [],
  },

  effects: {
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
  },
};
