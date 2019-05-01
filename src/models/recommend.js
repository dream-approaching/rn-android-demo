import { searchReq } from '@/services/api';

export default {
  namespace: 'recommend',

  state: {
    appList: [],
  },

  effects: {
    *queryAppEffect({ payload, finallyFn }, { call, put }) {
      try {
        const response = yield call(searchReq, payload);
        if (response.success) {
          yield put({
            type: 'saveAppList',
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
    saveAppList(state, { payload }) {
      return {
        ...state,
        appList: [...state.appList, ...payload],
      };
    },
  },
};
