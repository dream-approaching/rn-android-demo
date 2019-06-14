import { queryCatHomeListReq, queryCatArticleDetailReq } from '@/services/catApi';
import Toast from '@/components/Toast';

export default {
  namespace: 'catHome',

  state: {
    catHomeList: [],
    catHomeListTotal: 0,
    articleDetail: { img: '', detailtwo: [] },
  },

  effects: {
    *queryCatHomeListEffect({ payload, successFn }, { call, put }) {
      console.log('%cpayload:', 'color: #0e93e0;background: #aaefe5;', payload);
      try {
        const response = yield call(queryCatHomeListReq, payload);
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.code === 0) {
          yield put({
            type: 'saveCatHomeList',
            payload: response.data,
            isFirstPage: payload.isFirst,
          });
          successFn && successFn(response.data);
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    *queryArticleDetailEffect({ payload, successFn }, { call, put }) {
      try {
        const response = yield call(queryCatArticleDetailReq, payload);
        if (response && response.code === 0) {
          yield put({
            type: 'saveArticleDetail',
            payload: response.data || {},
          });
          successFn && successFn();
        } else {
          Toast.show(response.msg);
        }
      } catch (err) {
        console.log('err', err);
      }
    },
  },

  reducers: {
    saveCatHomeList(state, { payload, isFirstPage }) {
      return {
        ...state,
        catHomeList: isFirstPage ? payload || [] : [...state.catHomeList, ...payload],
      };
    },
    saveArticleDetail(state, { payload }) {
      return {
        ...state,
        articleDetail: payload,
      };
    },
  },
};
