import { queryHotLabelReq } from '@/services/xshare';

export default {
  namespace: 'xshare',

  state: {
    selectedLabel: [],
    hotLable: [], // 热门标签
    searchLable: [], // 搜索标签
  },

  effects: {
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
  },

  reducers: {
    saveHotLabelList(state, { payload }) {
      return {
        ...state,
        hotLable: [...state.hotLable, ...payload],
      };
    },
    saveSearchLabelList(state, { payload }) {
      return {
        ...state,
        searchLable: [...state.searchLable, ...payload],
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
