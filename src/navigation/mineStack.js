import React from 'react';
import { createStackNavigator, createAppContainer } from 'react-navigation';
import Mine from '../pages/mine/mine';
import Setting from '../pages/setting/setting';

const MineNavigator = createStackNavigator(
  {
    Mine,
    Setting
  },
  {
    initialRouteName: 'Mine'
  }
);

const MineContainer = createAppContainer(MineNavigator);

export default class extends React.Component {
  render() {
    return <MineContainer />;
  }
}
