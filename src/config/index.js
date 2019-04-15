import { Platform } from 'react-native';
import { color, size, layout } from './theme';
import scale from './scale';

export default {
  scale,
  isAndroid: Platform.OS === 'android',
  color,
  size,
  layout
};
