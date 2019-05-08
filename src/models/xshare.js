import { queryXshareListReq, queryXshareDetailReq } from '@/services/xshare';

export default {
  namespace: 'xshare',

  state: {
    xshareList: [],
    xshareListTotal: 0,
  },

  effects: {
    *queryXshareListEffect({ payload }, { call, put }) {
      try {
        const response = yield call(queryXshareListReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveXshareList',
            payload: response.data || {},
            isFirstPage: payload.isFirst,
          });
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    *queryXshareDetailEffect({ payload }, { call, put }) {
      try {
        const response = yield call(queryXshareDetailReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveXshareList',
            payload: response.data || {},
            isFirstPage: payload.isFirst,
          });
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
        xshareList: isFirstPage ? payload.list : [...state.commentList, ...payload.list],
        xshareListTotal: payload.cnt,
      };
    },
  },
};
