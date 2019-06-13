import { Dimensions, StyleSheet } from 'react-native';

const windowPlatform = Dimensions.get('window');

export const themeSize = {
  screenWidth: windowPlatform.width,
  screenHeight: windowPlatform.height,
  minBorder: StyleSheet.hairlineWidth,
  font: {
    superLarge: 19,
    larger: 17,
    common: 15,
    secondary: 13,
    small: 12,
    superSmall: 11,
  },
};

export const themeCatSize = {
  screenWidth: windowPlatform.width,
  screenHeight: windowPlatform.height,
  minBorder: StyleSheet.hairlineWidth,
  font: {
    superLarge: 19,
    larger: 14,
    common: 12,
    secondary: 12,
    small: 10,
    superSmall: 11,
  },
};
