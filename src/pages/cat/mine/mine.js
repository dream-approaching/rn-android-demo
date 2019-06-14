import React from 'react';
import { ImageBackground, StyleSheet, View } from 'react-native';
import MenuList from '@/components/MenuList';
import { OpenRnActivity, OpenActivity } from '@/components/NativeModules';
import { themeColor, scale, themeLayout } from '@/config';
import { connect } from '@/utils/dva';
import myImages from '@/utils/myImages';
import SpringScrollView from '@/components/SpringScrollView';
import Avatar from './components/Avatar';
import MenuCard from './components/MenuCard';

class Mine extends React.Component {
  static navigationOptions = {
    header: null,
  };

  handlePlus = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'global/testCountEffect',
    });
  };

  render() {
    const { userInfo } = this.props;
    const userData = {
      // avatar: 'http://photocdn.sohu.com/20150721/mp23627612_1437451852870_2.gif',
      avatar: userInfo && userInfo.head_image,
      name: userInfo ? userInfo.nick_name || `用户${userInfo.mobilephone.slice(7)}` : '未登录',
    };
    const cardMenu = [
      {
        title: '文章',
        icon: myImages.article,
        onPressAction: () => OpenActivity.openCollection('1'),
      },
      {
        title: '应用',
        icon: myImages.application,
        onPressAction: () => OpenActivity.openCollection('3'),
      },
      {
        title: '话题',
        icon: myImages.chat,
        onPressAction: () => OpenActivity.openCollection('2'),
      },
    ];
    const cardMenu2 = [
      {
        title: '发布',
        icon: myImages.article,
        onPressAction: () => OpenRnActivity('catPublish'),
        notNeedAuth: true,
      },
      {
        title: 'home',
        icon: myImages.application,
        onPressAction: () => OpenRnActivity('home'),
        notNeedAuth: true,
      },
      {
        title: '图文',
        icon: myImages.chat,
        onPressAction: () => OpenRnActivity('catDetailChat'),
        notNeedAuth: true,
      },
    ];
    const listMenu = [
      // {
      //   title: '我要认领应用',
      //   icon: myImages.own,
      //   onPressAction: () => OpenRnActivity('claimApp'),
      // },
      {
        title: '我的通知',
        icon: myImages.notice,
        onPressAction: () => OpenRnActivity('myNotice'),
      },
      {
        title: '我的关注',
        icon: myImages.attention,
        onPressAction: () => OpenActivity.openFansOrFollow('1'),
      },
      {
        title: '我的粉丝',
        icon: myImages.fans,
        onPressAction: () => OpenActivity.openFansOrFollow('2'),
      },
      {
        title: '设置',
        icon: myImages.setting,
        onPressAction: () => OpenActivity.openSetting(),
        notNeedAuth: true,
      },
      // {
      //   title: 'webview',
      //   icon: myImages.notice,
      //   onPressAction: () => OpenRnActivity('detailWebview'),
      //   notNeedAuth: true,
      // },
      // {
      //   title: '反馈意见',
      //   icon: myImages.suggest,
      //   onPressAction: () => {
      //     OpenRnActivity('myFeedback');
      //   },
      // },
    ];
    return (
      <View style={styles.container}>
        <SpringScrollView>
          <ImageBackground resizeMode='cover' source={{ uri: myImages.bg }} style={styles.imgbg}>
            <Avatar userInfo={userInfo} style={[styles.cardUser]} data={userData} />
          </ImageBackground>
          <MenuCard style={[styles.cardCon, styles.cardMenu]} menu={cardMenu} />
          <MenuCard style={[styles.cardCon, styles.cardMenu]} menu={cardMenu2} />
          <MenuList style={[styles.cardCon, styles.listMenu]} menu={listMenu} />
        </SpringScrollView>
      </View>
    );
  }
}

const mapStateToProps = ({ global, loading }) => ({
  userInfo: global.userInfo,
  loading: loading.effects['global/testCountEffect'],
});

export default connect(mapStateToProps)(Mine);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: themeColor.bgF4,
  },
  imgbg: {
    alignItems: 'center',
  },
  cardCon: {
    backgroundColor: themeColor.white,
    ...themeLayout.padding(0, scale(18)),
    width: scale(360),
  },
  cardUser: {
    width: scale(360),
    height: scale(158),
  },
  listMenu: {
    marginBottom: scale(50),
  },
  cardMenu: {
    ...themeLayout.flex('column', 'space-between', 'flex-start'),
    ...themeLayout.padding(scale(20), scale(34), scale(20), scale(18)),
    // height: scale(111),
    marginBottom: scale(4),
  },
});
