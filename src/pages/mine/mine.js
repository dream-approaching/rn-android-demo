import React from 'react';
import { ImageBackground, StyleSheet } from 'react-native';
import MenuList from '@/components/MenuList';
import myImages from '@/utils/images';
import { themeColor, scale, themeLayout } from '@/config';
import { ScrollView } from 'react-native-gesture-handler';
import MenuCard from './components/MenuCard';

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center'
  },
  menuCon: {
    backgroundColor: themeColor.white,
    borderRadius: scale(25),
    ...themeLayout.padding(0, scale(14)),
    overflow: 'hidden',
    width: scale(328)
  }
});

export default class Mine extends React.Component {
  static navigationOptions = {
    header: null
  };

  componentDidMount() {
    console.log('Mine componentDidMount');
  }

  render() {
    const menu = [
      {
        title: '我要认领应用',
        leftIcon: myImages.own,
        navigatePath: ''
      },
      {
        title: '我的通知',
        leftIcon: myImages.notice,
        navigatePath: ''
      },
      {
        title: '我的关注',
        leftIcon: myImages.attention,
        navigatePath: ''
      },
      {
        title: '我的粉丝',
        leftIcon: myImages.fans,
        navigatePath: ''
      },
      {
        title: '设置',
        leftIcon: myImages.setting,
        navigatePath: ''
      },
      {
        title: '反馈意见',
        leftIcon: myImages.suggest,
        navigatePath: ''
      }
    ];
    return (
      <ScrollView>
        <ImageBackground resizeMode="stretch" source={myImages.bg} style={styles.container}>
          <MenuCard style={styles.menuCon} menu={menu} />
          <MenuList style={styles.menuCon} menu={menu} />
        </ImageBackground>
      </ScrollView>
    );
  }
}
