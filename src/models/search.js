import { queryHotSearchReq } from '@/services/search';

export default {
  namespace: 'search',

  state: {
    hotSearchList: [],
  },

  effects: {
    *queryHotSearchEffect({ payload, finallyFn }, { call, put }) {
      try {
        const response = yield call(queryHotSearchReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveHotSearchList',
            payload: response.data || [],
          });
        }
      } catch (err) {
        console.log('err', err);
      } finally {
        finallyFn && finallyFn();
      }
    },
  },

  reducers: {
    saveHotSearchList(state, { payload }) {
      return {
        ...state,
        hotSearchList: payload,
      };
    },
  },
};
