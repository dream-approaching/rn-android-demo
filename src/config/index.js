import { Platform } from 'react-native';
import { OpenActivity } from '@/components/NativeModules';
import { store } from '../index';

console.log('%cOpenActivity:', 'color: #0e93e0;background: #aaefe5;', OpenActivity.host_content);

export { themeSize, themeCatSize } from './size';
export { themeColor, themeCatColor } from './color';
export { themeLayout } from './layout';

export { scale } from './scale';

export const isAndroid = Platform.OS === 'android';

export default {
  baseUrl: 'http://192.168.0.200:1230',
  baseUrl1: 'http://192.168.0.200:1530',
  // baseUrl: OpenActivity.host_content,
  bundleVersion: '1.0.1',
  app_ver: '1',
  app_ver_code: '1',
  ch: '1',
  channel_id: '2',
  amapAndroidKey: '5e267ea634a1e72863ab77238664cc80',
  amapWebKey: 'efc41095e2acb00f45066583aee61e5c',
  getUserInfo: () => {
    const state = store.getState();
    const { userInfo } = state.global;
    console.log(userInfo);
    return {
      access_token: 'e5f693b29571352cb86aa8c0dc1f070d',
      mobilephone: '17605041621',
      // access_token: userInfo ? userInfo.access_token : '',
      // mobilephone: userInfo ? userInfo.mobilephone : '',
    };
  },
};
