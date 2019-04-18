import React from 'react';
import { createStackNavigator, createAppContainer } from 'react-navigation';
import Mine from '@/pages/mine/mine';
import Setting from '@/pages/setting/setting';
import ErrorBoundary from '@/components/ErrorBoundary';
import NavigationService from './NavigationService';

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
  constructor(props) {
    super(props);
    console.log('mineStack constructor');
  }

  componentDidMount() {
    console.log('mineStack componentDidMount');
  }

  render() {
    return (
      <ErrorBoundary>
        <MineContainer
          ref={navigatorRef => {
            NavigationService.setTopLevelNavigator(navigatorRef);
          }}
        />
      </ErrorBoundary>
    );
  }
}
