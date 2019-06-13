// import { queryNotices } from '@/services/api';
import { omit } from '@/utils/utils';

export default {
  namespace: 'global',

  state: {
    shouldXshareRefresh: false,
    userInfo: null,
    xshareData: {},
    canShowLoading: true,
    globalLoading: false,
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
    *changeLoadingEffect({ payload, successFn }, { put }) {
      try {
        yield put({
          type: 'saveLoading',
          payload,
        });
        successFn && successFn();
      } catch (err) {
        console.log('err', err);
      }
    },
    *showGlobalLoadingEffect({ payload, successFn }, { put, call }) {
      try {
        yield put({
          type: 'toggleGlobalLoading',
          payload: true,
        });
        if (payload.time) {
          const delay = ms =>
            new Promise(resolve => {
              setTimeout(resolve, ms);
            });
          yield call(delay, payload.time);
          yield put({
            type: 'toggleGlobalLoading',
            payload: false,
          });
          successFn && successFn();
        }
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
    saveLoading(state, { payload }) {
      return {
        ...state,
        canShowLoading: payload,
      };
    },
    saveXshareData(state, { payload }) {
      /**
       * 莓友分享列表、莓友分享内页、个人页、搜索、更多搜索
       * 触发保存莓友分享列表那几个reducer时，一并触发这个reducer
       * payload是其id不存在与当前xshareData中的数据，处理好之后再传过来，这里直接合并
       *
       * 当点赞，评论，分享成功时，手动给修改的那一条itemData对应的值做加减
       * 再触发这个reducer
       * payload是修改的那一条数据,这里判断itemData.id存在的话，直接修改这个id下的数据
       *
       * 显示的时候先判断这个itemData的id在xshareData中是否存在
       * 存在的话把itemData替换成xshareData中的这条数据
       * 如果不存在，则用接口返回的itemData
       */
      if (payload.id) {
        // 只有一条数据，表示是点赞的情况
        state.xshareData[payload.id] = payload;
        return {
          ...state,
          xshareData: state.xshareData,
        };
      }
      if (payload.type === 'del') {
        return {
          ...state,
          xshareData: omit(state.xshareData, [payload.delItemId]),
        };
      }
      return {
        ...state,
        xshareData: { ...state.xshareData, ...payload },
      };
    },
    cleanXshareData(state) {
      return {
        ...state,
        xshareData: {},
      };
    },
    toggleGlobalLoading(state, { payload }) {
      return {
        ...state,
        globalLoading: payload,
      };
    },
  },

  subscriptions: {},
};
