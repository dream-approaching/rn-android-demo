import React from 'react';
import { View, Text } from 'react-native';
import MenuList from '@/components/MenuList';
import myImages from '@/utils/images';

export default class Mine extends React.Component {
  static navigationOptions = {
    header: null
  };

  componentDidMount() {
    console.log('Mine componentDidMount');
    console.log('15259040632');
  }

  render() {
    console.log('%cmyImages:', 'color: #0e93e0;background: #aaefe5;', myImages);
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
        navigatePath: 'Setting'
      },
      {
        title: '反馈意见',
        leftIcon: myImages.suggest,
        navigatePath: ''
      }
    ];
    return (
      <View
        style={{ flex: 1, alignItems: 'center', justifyContent: 'center', backgroundColor: '#eee' }}
      >
        <Text>Mine Screens</Text>
        <MenuList menu={menu} />
      </View>
    );
  }
}
