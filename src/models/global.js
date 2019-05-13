// import { queryNotices } from '@/services/api';

export default {
  namespace: 'global',

  state: {
    shouldXshareRefresh: false,
  },

  effects: {
    *toggleXshareRefreshEffect({ payload, successFn }, { put }) {
      try {
        yield put({
          type: 'toggleXshareRefreshReducer',
          payload,
        });
        successFn && successFn();
      } catch (err) {
        console.log('err', err);
      }
    },
  },

  reducers: {
    toggleXshareRefreshReducer(state, { payload }) {
      return {
        ...state,
        shouldXshareRefresh: payload,
      };
    },
  },

  subscriptions: {},
};
