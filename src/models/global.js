// import { queryNotices } from '@/services/api';

export default {
  namespace: 'global',

  state: {
    count: 1,
  },

  effects: {
    *testCountEffect(_, { put, select }) {
      const unreadCount = yield select(
        state => state.global.count + 1
      );
      yield put({
        type: 'testCountReducer',
        payload: unreadCount,
      });
    },
  },

  reducers: {
    testCountReducer(state, { payload }) {
      return {
        ...state,
        count: payload,
      };
    },
  },

  subscriptions: {},
};
