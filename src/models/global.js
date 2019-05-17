// import { queryNotices } from '@/services/api';

export default {
  namespace: 'global',

  state: {
    shouldXshareRefresh: false,
    userInfo: null,
    xshareData: null,
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
    saveUserInfo(state, { payload }) {
      return {
        ...state,
        userInfo: payload,
      };
    },
    saveXshareData(state, { payload }) {
      // if (state.xshareData[payload.id]) return state;)
      return {
        ...state,
        xshareData: [...state.xshareData, ...payload],
      };
    },
  },

  subscriptions: {},
};
