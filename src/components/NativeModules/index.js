import { NativeModules } from 'react-native';

/**
 * OpenActivity 打开原生activity
 * OpenReactActivity 打开RN界面
 */
console.log('%cNativeModules:', 'color: #0e93e0;background: #aaefe5;', NativeModules);
export const { OpenActivity, OpenReactActivity } = NativeModules;
