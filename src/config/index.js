import { Platform } from 'react-native';

export { themeSize } from './size';
export { themeColor } from './color';
export { themeLayout } from './layout';

export { scale } from './scale';

export const isAndroid = Platform.OS === 'android';

export default {
  baseUrl: 'http://192.168.0.213:8011',
};
