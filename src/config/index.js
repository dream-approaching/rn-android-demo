import { Platform } from 'react-native';

export { themeSize } from './size';
export { themeColor } from './color';
export { themeLayout } from './layout';

export { scale } from './scale';

export const isAndroid = Platform.OS === 'android';

export default {
  baseUrl: 'http://192.168.0.213:8011', // 搜索
  baseUrl2: 'http://192.168.0.200:1230', // 评论，X友分享列表
};
