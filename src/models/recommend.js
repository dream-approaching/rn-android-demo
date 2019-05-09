import { queryHotClassifyReq, submitXShareReq } from '@/services/recommend';
import { searchReq } from '@/services/search';

export default {
  namespace: 'recommend',

  state: {
    appList: [],
    selectedLabel: [],
    hotClassify: [], // 热门标签
    searchLable: [], // 搜索标签
  },

  effects: {
    *queryAppEffect({ payload, finallyFn, successFn }, { call, put }) {
      try {
        const response = yield call(searchReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveAppList',
            payload: response.data || [],
            isFirstPage: payload.isFirst,
          });
          successFn && successFn(response.data);
        }
      } catch (err) {
        console.log('err', err);
      } finally {
        finallyFn && finallyFn();
      }
    },
    *queryHotClassifyEffect({ payload }, { call, put }) {
      try {
        const response = yield call(queryHotClassifyReq, payload);
        if (response && response.code === 0) {
          if (payload.search) {
            yield put({
              type: 'saveSearchClassifyList',
              payload: response.data || [],
            });
          } else {
            yield put({
              type: 'saveHotClassifyList',
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
    saveAppList(state, { payload, isFirstPage }) {
      return {
        ...state,
        appList: isFirstPage ? payload : [...state.appList, ...payload],
      };
    },
    saveHotClassifyList(state, { payload }) {
      return {
        ...state,
        hotClassify: payload,
      };
    },
    saveSearchClassifyList(state, { payload }) {
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
