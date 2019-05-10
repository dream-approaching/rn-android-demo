import React from 'react';
import { ImageBackground, StyleSheet, View, Button } from 'react-native';
import MenuList from '@/components/MenuList';
import { OpenRnActivity, OpenActivity, GetUserInfo } from '@/components/NativeModules';
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

  componentDidMount() {
    console.log('Mine componentDidMount, GetUserInfo');
  }

  handlePlus = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'global/testCountEffect',
    });
  };

  render() {
    const userData = {
      avatar: GetUserInfo.avatar,
      name: GetUserInfo.nickname || `用户${GetUserInfo.phone.slice(7)}`,
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
        onPressAction: () => {
          OpenActivity.openCollection('3');
        },
      },
      {
        title: '话题',
        icon: myImages.chat,
        onPressAction: () => {
          OpenRnActivity('collectChat');
        },
      },
    ];
    const listMenu = [
      {
        title: '我要认领应用',
        icon: myImages.own,
        onPressAction: () => {
          OpenRnActivity('claimApp');
        },
      },
      {
        title: '我的通知',
        icon: myImages.notice,
        onPressAction: () => {
          OpenRnActivity('myNotice');
        },
      },
      {
        title: '我的关注',
        icon: myImages.attention,
        onPressAction: () => {
          OpenRnActivity('myAttention');
        },
      },
      {
        title: '我的粉丝',
        icon: myImages.fans,
        onPressAction: () => {
          OpenRnActivity('myFans');
        },
      },
      {
        title: '设置',
        icon: myImages.setting,
        onPressAction: () => {
          OpenRnActivity('setting');
        },
      },
      {
        title: '反馈意见',
        icon: myImages.suggest,
        onPressAction: () => {
          OpenRnActivity('myFeedback');
        },
      },
    ];
    return (
      <View style={styles.container}>
        <SpringScrollView>
          <ImageBackground resizeMode='cover' source={{ uri: myImages.bg }} style={styles.imgbg}>
            <Avatar style={[styles.cardUser]} data={userData} />
          </ImageBackground>
          <View
            style={{
              ...themeLayout.flex('row', 'space-around'),
              width: '100%',
              marginBottom: scale(30),
            }}
          >
            <Button
              title='登录'
              onPress={() => OpenActivity.open('com.lieying.content.social.login.ENTER')}
            />
            <Button title='评论页' onPress={() => OpenRnActivity('comment')} />
            <Button
              title='我的分享'
              onPress={() =>
                OpenRnActivity('myShare', JSON.stringify({ phone: GetUserInfo.phone }))
              }
            />
            {/* <Button title='我要推荐' onPress={() => OpenRnActivity('recommend')} /> */}
            <Button title='搜索' onPress={() => OpenRnActivity('search')} />
          </View>
          <MenuCard style={[styles.cardCon, styles.cardMenu]} menu={cardMenu} />
          <MenuList style={[styles.cardCon, styles.listMenu]} menu={listMenu} />
        </SpringScrollView>
      </View>
    );
  }
}

const mapStateToProps = ({ global, loading }) => ({
  global,
  loading: loading.effects['global/testCountEffect'],
});

export default connect(mapStateToProps)(Mine);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: themeColor.bgColor,
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
    ...themeLayout.padding(scale(12), scale(34), scale(12), scale(18)),
    height: scale(111),
    marginBottom: scale(4),
  },
});
