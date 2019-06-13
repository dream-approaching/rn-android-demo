import { NativeModules } from 'react-native';
import { name } from '../../../app.json';

console.log('%cNativeModules:', 'color: #0e93e0;background: #aaefe5;', NativeModules);

/**
 * OpenActivity: 打开原生activity
 * OpenActivity.openAppDetails(id)              app内页
 * OpenActivity.openReportDialog(id)            举报对话框
 * OpenActivity.openUserData()                  编辑用户信息
 * OpenActivity.openUserIndex(phone)            个人页面
 * OpenActivity.openCollection(type)            type: 1-文章收藏  2-App收藏
 * OpenActivity.openFansOrFollow(type)          type: 1-我的关注  2-我的粉丝
 * OpenActivity.openSetting()                   设置界面
 * OpenActivity.open(string)                    其他页面
 *                  com.lieying.content.social.ENTER —— 登录
 */
/**
 * RnCallBack
 * @param int position;
 * @param int praiseNum;
 * @param int joinNum;
 * @param boolean praise;
 * @param boolean collection
 * @param String commentNum;
 * @param String id;
 * @returns json
 */
export const { OpenActivity, GetUserInfo, RnCallBack, UMAnalyticsModule } = NativeModules;

// 打开RN界面
export const OpenRnActivity = (route, json) => {
  NativeModules.OpenReactActivity.open(name, route, json);
};
