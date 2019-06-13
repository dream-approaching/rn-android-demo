import { queryCatHomeListReq, queryCatArticleDetailReq } from '@/services/catApi';
import Toast from '@/components/Toast';

export default {
  namespace: 'catHome',

  state: {
    catHomeList: [],
    catHomeListTotal: 0,
    articleDetail: { img: '' },
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
        // const response = yield call(queryCatArticleDetailReq, payload);
        const response = {
          success: true,
          code: 0,
          msg: '成功',
          data: {
            id: '42',
            channel_id: '2',
            commit_user: 'rre',
            mobilephone: '15864578953',
            fabulous: '0',
            label: '',
            content: '11发达的方式',
            app_info: '',
            comment_num: '0',
            created_time: '1560222052',
            type: '5',
            forward_num: '0',
            img:
              'http://192.168.0.200:1230/uploads_cms_images/1560137435195_34691.jpg,http://192.168.0.200:1230/uploads_cms_images/1560137436024_51992.jpg,http://192.168.0.200:1230/uploads_cms_images/1560137436024_51992.jpg',
            title: '333',
            mill_created_time: '0',
            location: '',
            head_image: 'http://192.168.0.200:8060/uploads_cms_images/1552617692930_2304.jpg',
            is_big_v: '1',
            is_add_friends: true,
            timestr: '前天',
            is_fabulous: true,
            count: 3,
            detailtwo: [
              {
                id: '105',
                channel_id: '2',
                content: '11发达的方式',
                commit_user: 'chxFKL347170615',
                mobilephone: '13564587895',
                fabulous: '0',
                created_time: '1559188234',
                path: '0-4',
                parent_id: '4',
              },
              {
                id: '101',
                channel_id: '2',
                content: 'gj',
                commit_user: '3rre',
                mobilephone: '18256478952',
                fabulous: '0',
                created_time: '1558322968',
                path: '0',
                parent_id: '0',
              },
            ],
          },
          encrypt: 0,
        };
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
