/**
 * scale.js
 * 自适应布局
 * @param elementWidth: ui给的原始尺寸
 */
import { Dimensions, Platform } from 'react-native';

const deviceWidthDp = Dimensions.get('window').width;
const uiWidthPx = 750;

function scale(elementWidth) {
  const transferNumb = elementWidth * deviceWidthDp / uiWidthPx;

  // 避免出现循环小数
  if (transferNumb >= 1) return Math.ceil(transferNumb);

  // 如果是安卓，最小为1，避免边框出现锯齿
  if (Platform.OS === 'android') return 1;

  return 0.5;
}

export default scale;
