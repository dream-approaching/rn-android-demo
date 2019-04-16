import { Dimensions, StyleSheet } from 'react-native';

const windowPlatform = Dimensions.get('window');

function marginPadding(type, arg) {
  let typeObj = {};
  switch (arg.length) {
    case 1:
      typeObj = {
        [`${type}Vertical`]: arg[0],
        [`${type}Horizontal`]: arg[0]
      };
      break;
    case 2:
      typeObj = {
        [`${type}Vertical`]: arg[0],
        [`${type}Horizontal`]: arg[1]
      };
      break;
    case 3:
      typeObj = {
        [`${type}Top`]: arg[0],
        [`${type}Horizontal`]: arg[1],
        [`${type}Bottom`]: arg[2]
      };
      break;
    case 4:
      typeObj = {
        [`${type}Top`]: arg[0],
        [`${type}Right`]: arg[1],
        [`${type}Bottom`]: arg[2],
        [`${type}Left`]: arg[3]
      };
      break;
    default:
      break;
  }
  return typeObj;
}

export const color = {
  red: '#FF0000',
  orange: '#FFA500',
  yellow: '#FFFF00',
  green: '#00FF00',
  cyan: '#00FFFF',
  blue: '#0000FF',
  purple: '#800080',
  black: '#000',
  white: '#FFF',
  gray: '#808080',
  transparent: 'transparent',

  themeColor: '#e74c3c', // 主题色
  textGrayColor: '#989898', // 默认灰色字体颜色
  textBlockColor: '#262626', // 默认黑色字体颜色
  bgColor: '#E6E6E6', // 默认背景颜色
  placeholderColor: '#eee', // 默认placeholder颜色
  borderColor: '#808080', // borderColor
  navThemeColor: '#FEFEFE',
  iconGray: '#989898',
  iconBlack: '#262626'
};

export const size = {
  screenWidth: windowPlatform.width,
  screenHeight: windowPlatform.height,
  minBorder: StyleSheet.hairlineWidth
};

export const layout = {
  flex: (direc = 'row', justify = 'center', align = 'center') => {
    return {
      display: 'flex',
      flexDirection: direc,
      justifyContent: justify,
      alignItems: align
    };
  },
  margin(...args) {
    return marginPadding('margin', args);
  },
  padding(...args) {
    return marginPadding('padding', args);
  }
};
