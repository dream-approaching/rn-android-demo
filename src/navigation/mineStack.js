import React from 'react';
import { createStackNavigator, createAppContainer } from 'react-navigation';
import Mine from '../pages/mine/mine';
import Setting from '../pages/setting/setting';
import ErrorBoundary from '../components/ErrorBoundary';

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
    return (
      <ErrorBoundary>
        <MineContainer />
      </ErrorBoundary>
    );
  }
}
