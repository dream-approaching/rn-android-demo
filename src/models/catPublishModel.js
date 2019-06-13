import { amapSearchApi } from '@/services/amapApi';
import { submitPublishReq } from '@/services/catApi';
import Toast from '@/components/Toast';

export default {
  namespace: 'catPublish',

  state: {
    defaultAddressList: [],
    addressList: [],
    selectedAddress: '',
  },

  effects: {
    *queryDefaultAddressListEffect({ payload }, { call, put }) {
      try {
        const response = yield call(amapSearchApi, payload);
        if (response && response.status === '1') {
          yield put({
            type: 'saveDefaultAddressList',
            payload: response.pois,
          });
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    *queryAddressListEffect({ payload }, { call, put }) {
      try {
        const response = yield call(amapSearchApi, payload);
        if (response && response.status === '1') {
          yield put({
            type: 'saveAddressList',
            payload: response.pois,
          });
        }
      } catch (err) {
        console.log('err', err);
      }
    },
    *submitPublishEffect({ payload, successFn }, { call }) {
      console.log('%cpayload:', 'color: #0e93e0;background: #aaefe5;', payload);
      try {
        const response = yield call(submitPublishReq, payload);
        console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
        if (response && response.code === 0) {
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
    saveDefaultAddressList(state, { payload }) {
      return {
        ...state,
        defaultAddressList: payload,
      };
    },
    saveAddressList(state, { payload }) {
      return {
        ...state,
        addressList: payload,
      };
    },
    saveSelectAddress(state, { payload }) {
      return {
        ...state,
        selectedAddress: payload,
      };
    },
  },
};
