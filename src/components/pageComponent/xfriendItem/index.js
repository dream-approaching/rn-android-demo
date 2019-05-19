import React from 'react';
import { View, Image, StyleSheet, Text } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, scale, themeColor, themeSize } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import SmallText from '@/components/AppText/SmallText';
import CommonText from '@/components/AppText/CommonText';
import LikeBtn from '@/components/Comment/likeBtn';
import myImages from '@/utils/myImages';
import { OpenRnActivity, OpenActivity } from '@/components/NativeModules';
import { LIKE_TYPE } from '@/config/constants';
import { connect } from '@/utils/dva';
import ImageWithDefault from '@/components/ImageWithDefault';
import { actionBeforeCheckLogin } from '@/utils/utils';

class XshareItem extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      mainBodyHeight: 0,
    };
    const { userInfo } = props.global;
    this.isOwn = userInfo && props.itemData.commit_user === userInfo.nick_name;
  }

  mainBodyLayout = ({ nativeEvent }) => {
    const { height } = nativeEvent.layout;
    this.setState({
      mainBodyHeight: height,
    });
  };

  toggleAttention = () => {
    const { itemData, dispatch, toggleAttentionCallback = () => {} } = this.props;
    const data = {
      follow_mobilephone: itemData.mobilephone,
      opt: !itemData.is_add_friends ? 'del' : 'add',
    };
    dispatch({
      type: 'xshare/toggleAttentionEffect',
      payload: data,
      successFn: () => {
        toggleAttentionCallback();
      },
    });
  };

  handleRightBottomAction = () => {
    const { itemData } = this.props;
    if (this.isOwn) {
      return this.props.handleShowDeleteModal(itemData);
    }
    // 举报
    OpenActivity.openReportDialog(itemData.id);
  };

  gotoXfriendDetail = () => {
    const { noPress, itemData } = this.props;
    return noPress
      ? () => {}
      : OpenRnActivity('xFriendDetail', JSON.stringify({ id: itemData.id }));
  };

  gotoAppDetail = () => {
    const { itemData } = this.props;
    OpenActivity.openAppDetails(
      itemData.mydata
        ? itemData.mydata.id
        : itemData.appdata ? itemData.appdata.id : itemData.app_info
    );
  };

  gotoPersonPage = () => {
    const { itemData, origin } = this.props;
    if (this.isOwn || origin === 'myShare') return null;
    OpenActivity.openUserIndex(itemData.mobilephone);
  };

  render() {
    const { isDetail, origin, global } = this.props;
    let { itemData } = this.props;
    const { xshareData } = global;
    if (xshareData[itemData.id]) {
      itemData = xshareData[itemData.id];
    }
    const { mainBodyHeight } = this.state;
    const lineNumber = !isDetail ? { numberOfLines: 6 } : {};
    const notShowAttention = this.isOwn || origin === 'myShare';
    return (
      <View style={styles.container}>
        <TouchableNativeFeedback color onPress={this.gotoPersonPage}>
          <View style={styles.avatarCon}>
            <ImageWithDefault style={styles.avatar} source={{ uri: itemData.head_image }} />
            {+itemData.is_big_v === 2 && (
              <Image style={styles.bigV} source={{ uri: myImages.approve }} />
            )}
          </View>
        </TouchableNativeFeedback>
        <View style={styles.itemRight}>
          <View style={styles.flexRowBetween}>
            <SecondaryText>{itemData.commit_user}</SecondaryText>
            {!notShowAttention && (
              <TouchableNativeFeedback onPress={() => actionBeforeCheckLogin(this.toggleAttention)}>
                <SmallText style={styles.attenText(itemData.is_add_friends)}>
                  {itemData.is_add_friends ? '+关注' : '已关注'}
                </SmallText>
              </TouchableNativeFeedback>
            )}
          </View>
          <SmallText>{itemData.timestr || itemData.created_time}</SmallText>
          <TouchableNativeFeedback onPress={this.gotoXfriendDetail}>
            <Text
              ref={ref => (this.refText = ref)}
              onLayout={this.mainBodyLayout}
              style={styles.mainContext}
              {...lineNumber}
            >
              {itemData.label &&
                itemData.label.split(',').map(item => {
                  return (
                    <CommonText style={styles.labelText} key={item}>
                      #{item}{' '}
                    </CommonText>
                  );
                })}
              <CommonText>&nbsp;&nbsp;{itemData.content}</CommonText>
            </Text>
          </TouchableNativeFeedback>
          {!isDetail &&
            mainBodyHeight >= 110 && (
              <TouchableNativeFeedback onPress={this.gotoXfriendDetail}>
                <Text style={styles.seeAllText}>查看详情</Text>
              </TouchableNativeFeedback>
            )}
          <View style={styles.flexRowBetween}>
            <TouchableNativeFeedback tapArea={1} onPress={this.gotoAppDetail}>
              <View style={styles.appCon}>
                <ImageWithDefault
                  style={styles.appIcon}
                  source={{
                    uri: itemData.mydata
                      ? itemData.mydata.img
                      : itemData.appdata ? itemData.appdata.app_logo : itemData.app_logo,
                  }}
                />
                <SmallText style={styles.appName}>
                  {itemData.mydata
                    ? itemData.mydata.title
                    : itemData.appdata ? itemData.appdata.app_short_desc : itemData.app_name_cn}
                </SmallText>
              </View>
            </TouchableNativeFeedback>
            <View />
          </View>
          <View style={styles.bottomBar}>
            <LikeBtn
              type={LIKE_TYPE.share}
              itemData={itemData}
              size={16}
              textStyle={styles.bottomBarText}
            />
            <TouchableNativeFeedback onPress={this.gotoXfriendDetail}>
              <View style={styles.flexRowBetween}>
                <Image style={styles.bottomBarIcon} source={{ uri: myImages.comment }} />
                <SmallText style={styles.bottomBarText}>{+itemData.comment_num || ''}</SmallText>
              </View>
            </TouchableNativeFeedback>
            <TouchableNativeFeedback>
              <View style={styles.flexRowBetween}>
                <Image style={styles.bottomBarIcon} source={{ uri: myImages.share }} />
                <SmallText style={styles.bottomBarText}>{+itemData.forward_num || ''}</SmallText>
              </View>
            </TouchableNativeFeedback>
            <TouchableNativeFeedback
              onPress={() => actionBeforeCheckLogin(this.handleRightBottomAction)}
            >
              <View style={styles.btnEtc}>
                {(this.isOwn && <SmallText style={styles.delText}>删除</SmallText>) || (
                  <Image style={styles.bottomBarIcon} source={{ uri: myImages.btnEtc }} />
                )}
              </View>
            </TouchableNativeFeedback>
          </View>
        </View>
      </View>
    );
  }
}

const mapStateToProps = ({ xshare, global }) => ({ xshare, global });

export default connect(mapStateToProps)(XshareItem);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    ...themeLayout.flex('row', 'space-between', 'flex-start'),
    ...themeLayout.padding(0, scale(16)),
    marginTop: scale(17),
  },
  avatarCon: {
    width: scale(49),
    height: scale(49),
    borderRadius: scale(25),
  },
  avatar: {
    width: scale(49),
    height: scale(49),
    borderRadius: scale(25),
    backgroundColor: themeColor.bgF4,
  },
  bigV: {
    position: 'absolute',
    bottom: 0,
    right: 0,
    zIndex: 1000,
    width: scale(14),
    height: scale(14),
  },
  itemRight: {
    flex: 1,
    marginLeft: scale(12),
    paddingBottom: scale(14),
    ...themeLayout.borderSide(),
  },
  flexRowBetween: {
    ...themeLayout.flex('row', 'space-between'),
  },
  attenText: attention => {
    return {
      fontSize: scale(11),
      color: !attention ? themeColor.font.secondary : themeColor.primaryColor,
    };
  },
  mainContext: {
    lineHeight: scale(20),
  },
  labelText: {
    color: themeColor.font.secondary,
  },
  appCon: {
    height: scale(22),
    ...themeLayout.flex('row'),
    ...themeLayout.padding(0, scale(10)),
    backgroundColor: themeColor.bgF4,
    borderRadius: scale(10),
    marginTop: scale(8),
  },
  appIcon: {
    width: scale(17),
    height: scale(17),
    borderRadius: scale(9),
  },
  appName: {
    color: themeColor.primaryColor,
    marginLeft: scale(6),
    // fontWeight: '500',
  },
  bottomBar: {
    ...themeLayout.flex('row', 'space-between'),
    marginTop: scale(12),
  },
  bottomBarText: {
    fontSize: scale(11),
    color: themeColor.font.small,
    marginLeft: scale(5),
  },
  bottomBarIcon: {
    width: scale(16),
    height: scale(16),
  },
  btnEtc: {
    marginLeft: scale(30),
  },
  etcIcon: {
    width: scale(18),
    height: scale(18),
  },
  seeAllText: {
    color: '#2c5b93',
    fontSize: themeSize.font.secondary,
    marginTop: scale(5),
  },
  delText: {
    color: themeColor.primaryColor,
    fontSize: scale(11),
    ...themeLayout.borderSide('Bottom', themeColor.primaryColor),
  },
});
