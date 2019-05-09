import { queryHistorySearchReq, searchReq } from '@/services/search';

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
    *querySearchEffect({ payload, finallyFn, successFn }, { call, put }) {
      try {
        const response = yield call(searchReq, payload);
        if (response && response.code === 0) {
          if (+payload.search_type === 1) {
            yield put({
              type: 'saveSearchAll',
              payload: response.data || {},
            });
          } else {
            yield put({
              type: 'saveSearchList',
              payload: response.data || [],
              isFirstPage: payload.isFirst,
            });
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
