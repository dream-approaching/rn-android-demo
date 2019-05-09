import React from 'react';
import { View, Image, StyleSheet, Text, TouchableOpacity } from 'react-native';
// import { TouchableOpacity } from 'react-native-gesture-handler';
import { themeLayout, scale, themeColor, themeSize } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import SmallText from '@/components/AppText/SmallText';
import CommonText from '@/components/AppText/CommonText';
import LikeBtn from '@/components/Comment/likeBtn';
import myImages from '@/utils/myImages';
import { OpenRnActivity, OpenActivity } from '@/components/NativeModules';

export default class extends React.PureComponent {
  state = {
    mainBodyHeight: 0,
  };

  mainBodyLayout = ({ nativeEvent }) => {
    const { height } = nativeEvent.layout;
    this.setState({
      mainBodyHeight: height,
    });
  };

  toggleAttention = () => {
    const { itemData } = this.props;
    console.log('%citemData:', 'color: #0e93e0;background: #aaefe5;', itemData.attention);
  };

  handleRightBottomAction = () => {
    const { origin } = this.props;
    if (origin === 'myPage') {
      // showModal
    }
  };

  gotoXfriendDetail = itemData => {
    const { noPress } = this.props;
    return noPress ? () => {} : OpenRnActivity('xFriendDetail', JSON.stringify(itemData));
  };

  gotoAppDetail = itemData => {
    OpenActivity.openAppDetails(
      itemData.mydata
        ? itemData.mydata.id
        : itemData.appdata
        ? itemData.appdata.id
        : itemData.app_info
    );
  };

  render() {
    const { itemData, noPress, origin } = this.props;
    const { mainBodyHeight } = this.state;
    return (
      <View style={styles.container}>
        <TouchableOpacity>
          <View style={styles.avatarCon}>
            <Image style={styles.avatar} source={{ uri: itemData.head_image }} />
            {+itemData.is_big_v === 2 && (
              <Image style={styles.bigV} source={{ uri: myImages.approve }} />
            )}
          </View>
        </TouchableOpacity>
        <View style={styles.itemRight}>
          <View style={styles.flexRowBetween}>
            <SecondaryText>{itemData.commit_user}</SecondaryText>
            {origin !== 'myPage' && (
              <TouchableOpacity onPress={this.toggleAttention}>
                <SmallText style={styles.attenText(itemData.is_add_friends)}>
                  {itemData.is_add_friends ? '+关注' : '已关注'}
                </SmallText>
              </TouchableOpacity>
            )}
          </View>
          <SmallText>{itemData.timestr || itemData.created_time}</SmallText>
          <TouchableOpacity
            activeOpacity={noPress ? 1 : 0.2}
            onPress={() => this.gotoXfriendDetail(itemData)}
            style={styles.mainBody}
          >
            <Text
              ref={ref => (this.refText = ref)}
              numberOfLines={6}
              onLayout={this.mainBodyLayout}
              style={styles.mainContext}
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
          </TouchableOpacity>
          {mainBodyHeight >= 110 && (
            <TouchableOpacity onPress={() => this.gotoXfriendDetail(itemData)}>
              <Text style={styles.seeAllText}>查看详情</Text>
            </TouchableOpacity>
          )}
          <View style={styles.flexRowBetween}>
            <TouchableOpacity onPress={() => this.gotoAppDetail(itemData)} style={styles.appCon}>
              <Image
                style={styles.appIcon}
                source={{
                  uri: itemData.mydata
                    ? itemData.mydata.img
                    : itemData.appdata
                    ? itemData.appdata.app_logo
                    : itemData.app_logo,
                }}
              />
              <SmallText style={styles.appName}>
                {itemData.mydata
                  ? itemData.mydata.title
                  : itemData.appdata
                  ? itemData.appdata.app_short_desc
                  : itemData.app_name_cn}
              </SmallText>
            </TouchableOpacity>
            <View />
          </View>
          <View style={styles.bottomBar}>
            <LikeBtn size={16} likeNum={itemData.fabulous} textStyle={styles.bottomBarText} />
            <TouchableOpacity style={styles.flexRowBetween}>
              <Image style={styles.bottomBarIcon} source={{ uri: myImages.comment }} />
              <SmallText style={styles.bottomBarText}>{itemData.comment_num}</SmallText>
            </TouchableOpacity>
            <TouchableOpacity style={styles.flexRowBetween}>
              <Image style={styles.bottomBarIcon} source={{ uri: myImages.share }} />
              <SmallText style={styles.bottomBarText}>{itemData.forward_num}</SmallText>
            </TouchableOpacity>
            <TouchableOpacity onPress={this.handleRightBottomAction} style={styles.btnEtc}>
              {(origin === 'myPage' && <SmallText style={styles.delText}>删除</SmallText>) || (
                <Image style={styles.bottomBarIcon} source={{ uri: myImages.btnEtc }} />
              )}
            </TouchableOpacity>
          </View>
        </View>
      </View>
    );
  }
}

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
