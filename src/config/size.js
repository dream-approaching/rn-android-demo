import { Dimensions, StyleSheet } from 'react-native';
import { scale } from './scale';

const windowPlatform = Dimensions.get('window');

export const themeSize = {
  screenWidth: windowPlatform.width,
  screenHeight: windowPlatform.height,
  minBorder: StyleSheet.hairlineWidth,
  font: {
    larger: scale(17),
    common: scale(15),
    secondary: scale(13)
  }
};
