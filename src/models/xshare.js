import { queryXshareListReq, queryOthershareListReq } from '@/services/xshare';

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
