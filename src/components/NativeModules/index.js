import { NativeModules } from 'react-native';
import { name } from '../../../app.json';

/**
 * OpenActivity 打开原生activity
 * OpenReactActivity 打开RN界面
 * GetUserInfo 用户信息
 */
console.log('%cNativeModules:', 'color: #0e93e0;background: #aaefe5;', NativeModules);

export const { OpenActivity } = NativeModules;

export const OpenRnActivity = (route, json) => {
  NativeModules.OpenReactActivity.open(name, route, json);
};

export const GetUserInfo = {
  nickname: NativeModules.GetUserInfo.KEY_ONE_NICK,
  phone: NativeModules.GetUserInfo.KEY_ONE_PHONE,
  token: NativeModules.GetUserInfo.KEY_ONE_TOKEN,
  avatar: '',
};
