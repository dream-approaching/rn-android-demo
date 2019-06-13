import React from 'react';
import { View, Image, StyleSheet, Text } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, themeCatColor, themeCatSize } from '@/config';
import SecondaryText from '@/components/AppText/Cat/SecondaryText';
import SmallText from '@/components/AppText/Cat/SmallText';
import CommonText from '@/components/AppText/Cat/CommonText';
import LikeBtn from '@/components/Comment/Cat/likeBtn';
import myImages from '@/utils/myImages';
import { OpenRnActivity, OpenActivity } from '@/components/NativeModules';
import { LIKE_TYPE, immediateTimer } from '@/config/constants';
import { connect } from '@/utils/dva';
import ImageWithDefault from '@/components/ImageWithDefault';
import { actionBeforeCheckLogin } from '@/utils/utils';

class XshareItem extends React.Component {
  mainBodyHeight = 0;

  state = {
    numberofLines: null,
    showMore: false,
  };

  componentDidMount() {
    this.lineTimer = setTimeout(() => {
      this.setState({
        numberofLines: 6,
      });
    }, 100);
  }

  componentWillUnmount() {
    // this.timer && clearTimeout(this.timer);
    this.lineTimer && clearTimeout(this.lineTimer);
  }

  mainBodyLayout = async ({ nativeEvent }) => {
    const { height } = nativeEvent.layout;
    if (!this.mainBodyHeight) {
      this.mainBodyHeight = height;
    } else if (this.mainBodyHeight > height) {
      this.setState({
        showMore: true,
      });
    }
  };

  toggleAttention = () => {
    const { itemData, dispatch, global } = this.props;
    const data = {
      follow_mobilephone: itemData.mobilephone,
      opt: !itemData.is_add_friends ? 'del' : 'add',
    };
    const obj = {};
    Object.values(global.xshareData).map(item => {
      if (item.mobilephone === itemData.mobilephone) {
        item.is_add_friends = !item.is_add_friends;
        obj[item.id] = item;
      }
    });
    dispatch({
      type: 'global/saveXshareData',
      payload: obj,
    });
    if (this.timer) clearTimeout(this.timer);
    this.timer = setTimeout(() => {
      dispatch({
        type: 'xshare/toggleAttentionEffect',
        payload: data,
      });
    }, immediateTimer);
  };

  handleRightBottomAction = () => {
    const { itemData, userInfo } = this.props;
    if (userInfo && itemData.commit_user === userInfo.nick_name) {
      return this.props.handleShowDeleteModal(itemData);
    }
    // 举报
    OpenActivity.openReportDialog(itemData.id);
  };

  gotoXfriendDetail = () => {
    const { noPress, itemData } = this.props;
    return noPress
      ? () => {}
      : OpenRnActivity('xFriendDetail', JSON.stringify({ contentId: itemData.id }));
  };

  gotoAppDetail = () => {
    const { itemData } = this.props;
    OpenActivity.openAppDetails(
      itemData.mydata
        ? itemData.mydata.id
        : itemData.appdata
        ? itemData.appdata.id
        : itemData.app_info
    );
  };

  gotoPersonPage = () => {
    const { itemData, origin, userInfo } = this.props;
    const isOwn = userInfo && itemData.commit_user === userInfo.nick_name;
    if (isOwn || origin === 'myShare') return null;
    OpenActivity.openUserIndex(itemData.mobilephone);
  };

  render() {
    const { isDetail, origin, global, userInfo, islastOne } = this.props;
    let { itemData } = this.props;
    const { xshareData } = global;
    if (xshareData[itemData.id]) {
      itemData = xshareData[itemData.id];
    }
    const { showMore } = this.state;
    const isOwn = userInfo && itemData.commit_user === userInfo.nick_name;
    const notShowAttention = isOwn || origin === 'myShare';
    return (
      <View style={styles.container}>
        <TouchableNativeFeedback onPress={this.gotoPersonPage}>
          <View style={styles.avatarCon}>
            <ImageWithDefault style={styles.avatar} source={{ uri: itemData.head_image }} />
            {+itemData.is_big_v === 2 && (
              <Image style={styles.bigV} source={{ uri: myImages.approve }} />
            )}
          </View>
        </TouchableNativeFeedback>
        <View style={styles.itemRight(islastOne)}>
          <View style={styles.flexRowBetween}>
            <TouchableNativeFeedback tapArea={1} onPress={this.gotoPersonPage}>
              <SecondaryText>{itemData.commit_user}</SecondaryText>
            </TouchableNativeFeedback>
            {!notShowAttention && (
              <TouchableNativeFeedback onPress={() => actionBeforeCheckLogin(this.toggleAttention)}>
                <SmallText style={styles.attenText(itemData.is_add_friends)}>
                  {itemData.is_add_friends ? '+关注' : '已关注'}
                </SmallText>
              </TouchableNativeFeedback>
            )}
          </View>
          <SmallText style={[styles.marginTop(1), styles.superText]}>
            {itemData.timestr || itemData.created_time}
          </SmallText>
          <TouchableNativeFeedback tapArea={5} onPress={this.gotoXfriendDetail}>
            <View>
              <Text
                ref={ref => (this.refText = ref)}
                onLayout={this.mainBodyLayout}
                style={styles.mainContext}
                // numberOfLines={isDetail ? null : 3}
                numberOfLines={this.state.numberofLines}
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
            </View>
          </TouchableNativeFeedback>
          {!isDetail && showMore && (
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
                    uri:
                      (itemData.mydata && itemData.mydata.img) ||
                      (itemData.appdata && itemData.appdata.app_logo),
                  }}
                />
                <SmallText numberOfLines={1} style={styles.appName}>
                  {(itemData.mydata && itemData.mydata.app_name_cn) ||
                    (itemData.appdata && itemData.appdata.app_name_cn)}
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
              {(origin !== 'detail' && (
                <View style={styles.btnEtc}>
                  {(isOwn && <SmallText style={styles.delText}>删除</SmallText>) || (
                    <Image style={styles.bottomBarIcon} source={{ uri: myImages.btnEtc }} />
                  )}
                </View>
              )) || <View style={styles.btnEtc} />}
            </TouchableNativeFeedback>
          </View>
        </View>
      </View>
    );
  }
}

const mapStateToProps = ({ xshare, global }) => ({ xshare, global, userInfo: global.userInfo });

export default connect(mapStateToProps)(XshareItem);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    ...themeLayout.flex('row', 'space-between', 'flex-start'),
    ...themeLayout.padding(0),
    marginTop: 17,
  },
  marginTop: num => {
    return {
      marginTop: num,
    };
  },
  superText: {
    fontSize: 10,
  },
  avatarCon: {
    width: 46,
    height: 46,
    borderRadius: 25,
  },
  avatar: {
    width: 46,
    height: 46,
    borderRadius: 25,
    backgroundColor: themeCatColor.bgF4,
  },
  bigV: {
    position: 'absolute',
    bottom: 0,
    right: 0,
    zIndex: 1000,
    width: 14,
    height: 14,
  },
  itemRight: isLastOne => {
    const obj = {
      flex: 1,
      marginLeft: 12,
      paddingBottom: 14,
    };
    return isLastOne ? obj : { ...obj, ...themeLayout.borderSide() };
  },
  flexRowBetween: {
    ...themeLayout.flex('row', 'space-between'),
  },
  attenText: attention => {
    return {
      fontSize: 13,
      color: !attention ? themeCatColor.font.secondary : themeCatColor.primaryColor,
    };
  },
  mainContext: {
    lineHeight: 22,
    marginTop: 5,
  },
  labelText: {
    color: themeCatColor.font.secondary,
  },
  appCon: {
    height: 22,
    ...themeLayout.flex('row'),
    ...themeLayout.padding(0, 10),
    backgroundColor: themeCatColor.bgF4,
    borderRadius: 10,
    marginTop: 10,
  },
  appIcon: {
    width: 17,
    height: 17,
    borderRadius: 9,
  },
  appName: {
    color: themeCatColor.primaryColor,
    marginLeft: 6,
    // fontWeight: '500',
  },
  bottomBar: {
    ...themeLayout.flex('row', 'space-between'),
    marginTop: 13,
  },
  bottomBarText: {
    fontSize: 11,
    color: themeCatColor.font.small,
    marginLeft: 5,
  },
  bottomBarIcon: {
    width: 16,
    height: 16,
  },
  btnEtc: {
    marginLeft: 30,
  },
  etcIcon: {
    width: 18,
    height: 18,
  },
  seeAllText: {
    color: '#2c5b93',
    fontSize: themeCatSize.font.secondary,
    marginTop: 5,
  },
  delText: {
    color: themeCatColor.primaryColor,
    fontSize: 11,
    ...themeLayout.borderSide('Bottom', themeCatColor.primaryColor),
  },
});
