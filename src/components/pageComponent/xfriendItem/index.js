import React from 'react';
import { View, Image, StyleSheet, Text, TouchableOpacity } from 'react-native';
// import { TouchableOpacity } from 'react-native-gesture-handler';
import { themeLayout, scale, themeColor, themeSize } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import SmallText from '@/components/AppText/SmallText';
import CommonText from '@/components/AppText/CommonText';
import LikeBtn from '@/components/Comment/likeBtn';
import moment from '@/components/moment';
import myImages from '@/utils/myImages';
import { OpenRnActivity } from '@/components/NativeModules';

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

  gotoXfriendDetail = () => {
    const { noPress } = this.props;
    return noPress ? () => {} : OpenRnActivity('xFriendDetail');
  };

  render() {
    const { itemData, noPress } = this.props;
    const { mainBodyHeight } = this.state;
    return (
      <View style={styles.container}>
        <TouchableOpacity>
          <View style={styles.avatarCon}>
            <Image style={styles.avatar} source={{ uri: itemData.avatar }} />
            {itemData.approve && <Image style={styles.bigV} source={{ uri: myImages.approve }} />}
          </View>
        </TouchableOpacity>
        <View style={styles.itemRight}>
          <View style={styles.flexRowBetween}>
            <SecondaryText>{itemData.name}</SecondaryText>
            <TouchableOpacity onPress={this.toggleAttention}>
              <SmallText style={styles.attenText(itemData.attention)}>
                {!itemData.attention ? '+关注' : '已关注'}
              </SmallText>
            </TouchableOpacity>
          </View>
          <SmallText>{moment(itemData.time * 1000).fromNow(true)}</SmallText>
          <TouchableOpacity
            activeOpacity={noPress ? 1 : 0.2}
            onPress={this.gotoXfriendDetail}
            style={styles.mainBody}
          >
            <Text
              ref={ref => (this.refText = ref)}
              numberOfLines={6}
              onLayout={this.mainBodyLayout}
              style={styles.mainContext}
            >
              {itemData.label.map(item => {
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
            <TouchableOpacity onPress={this.gotoXfriendDetail}>
              <Text style={styles.seeAllText}>查看详情</Text>
            </TouchableOpacity>
          )}
          <View style={styles.flexRowBetween}>
            <TouchableOpacity style={styles.appCon}>
              <Image style={styles.appIcon} source={{ uri: itemData.app.icon }} />
              <SmallText style={styles.appName}>{itemData.app.name}</SmallText>
            </TouchableOpacity>
            <View />
          </View>
          <View style={styles.bottomBar}>
            <LikeBtn size={16} likeNum={123} textStyle={styles.bottomBarText} />
            <TouchableOpacity style={styles.flexRowBetween}>
              <Image style={styles.bottomBarIcon} source={{ uri: myImages.comment }} />
              <SmallText style={styles.bottomBarText}>234</SmallText>
            </TouchableOpacity>
            <TouchableOpacity style={styles.flexRowBetween}>
              <Image style={styles.bottomBarIcon} source={{ uri: myImages.share }} />
              <SmallText style={styles.bottomBarText}>89</SmallText>
            </TouchableOpacity>
            <TouchableOpacity style={styles.btnEtc}>
              <Image style={styles.bottomBarIcon} source={{ uri: myImages.btnEtc }} />
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
      color: attention ? themeColor.font.secondary : themeColor.primaryColor,
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
    fontWeight: '500',
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
});
