import { queryHistorySearchReq, searchReq } from '@/services/search';
import { storeXshare } from '@/utils/utils';

export default {
  namespace: 'search',

  state: {
    historySearchList: [],
    searchAll: {},
    searchList: [],
  },

  effects: {
    *queryHistorySearchEffect({ payload }, { call, put }) {
      try {
        const response = yield call(queryHistorySearchReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveHistorySearchList',
            payload: response.data || [],
          });
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    *querySearchEffect({ payload, finallyFn, successFn }, { call, put, select }) {
      try {
        const response = yield call(searchReq, payload);
        if (response && response.code === 0) {
          if (+payload.search_type === 1) {
            yield put({
              type: 'saveSearchAll',
              payload: response.data || {},
            });
            if (response.data.share) {
              let storeXshareObj = {};
              yield select(state => {
                storeXshareObj = storeXshare(response.data.share, state);
              });
              yield put({
                type: 'global/saveXshareData',
                payload: storeXshareObj,
              });
            }
          } else {
            yield put({
              type: 'saveSearchList',
              payload: response.data || [],
              isFirstPage: payload.isFirst,
            });
            if (payload.search_type === 5) {
              let storeXshareObj = {};
              yield select(state => {
                storeXshareObj = storeXshare(response.data, state);
              });
              yield put({
                type: 'global/saveXshareData',
                payload: storeXshareObj,
              });
            }
          }
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
    saveHistorySearchList(state, { payload }) {
      return {
        ...state,
        historySearchList: payload,
      };
    },
    saveSearchAll(state, { payload }) {
      return {
        ...state,
        searchAll: payload,
      };
    },
    saveSearchList(state, { payload, isFirstPage }) {
      return {
        ...state,
        searchList: isFirstPage ? payload : [...state.searchList, ...payload],
      };
    },
  },
};
