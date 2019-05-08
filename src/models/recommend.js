import { queryHotLabelReq, searchReq, submitXShareReq } from '@/services/recommend';

export default {
  namespace: 'recommend',

  state: {
    appList: [],
    selectedLabel: [],
    hotLable: [], // 热门标签
    searchLable: [], // 搜索标签
  },

  effects: {
    *queryAppEffect({ payload, finallyFn }, { call, put }) {
      try {
        const response = yield call(searchReq, payload);
        if (response.code === 0) {
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
    *queryHotLabelEffect({ payload }, { call, put }) {
      try {
        const response = yield call(queryHotLabelReq, payload);
        if (response && response.code === 0) {
          if (payload.search) {
            yield put({
              type: 'saveSearchLabelList',
              payload: response.data || [],
            });
          } else {
            yield put({
              type: 'saveHotLabelList',
              payload: response.data || [],
            });
          }
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    *submitXShareEffect({ payload }, { call, put }) {
      try {
        const response = yield call(submitXShareReq, payload);
        if (response && response.code === 0) {
          console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response, put);
        }
      } catch (err) {
        console.log('err', err);
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
    saveHotLabelList(state, { payload }) {
      return {
        ...state,
        hotLable: payload,
      };
    },
    saveSearchLabelList(state, { payload }) {
      return {
        ...state,
        searchLable: payload,
      };
    },
    saveSelectedLabelList(state, { payload }) {
      return {
        ...state,
        selectedLabel: payload,
      };
    },
  },
};
