import React from 'react';
import { createStackNavigator, createAppContainer } from 'react-navigation';
import ErrorBoundary from '@/components/ErrorBoundary';
import Mine from '@/pages/mine/mine';
import Setting from '@/pages/setting/setting';
import Recommend from '@/pages/recommend/recommend';
import Chat from '@/pages/chat/chat';
import NavigationService from '@/utils/NavigationService';

const RecommendNavigator = createStackNavigator(
  {
    Recommend
  },
  {
    initialRouteName: 'Recommend'
  }
);

const ChatNavigator = createStackNavigator(
  {
    Chat
  },
  {
    initialRouteName: 'Chat'
  }
);

const MineNavigator = createStackNavigator(
  {
    Mine,
    Setting
  },
  {
    initialRouteName: 'Mine'
  }
);

const RecommendContainer = createAppContainer(RecommendNavigator);
const ChatContainer = createAppContainer(ChatNavigator);
const MineContainer = createAppContainer(MineNavigator);

export default class Router extends React.Component {
  state = {
    App: RecommendContainer
  };

  componentDidMount() {
    const { nativeProps } = this.props;
    const { veiw_name: viewName } = nativeProps;
    const app = {
      fragment1: RecommendContainer,
      fragment2: ChatContainer,
      fragment3: MineContainer
    };
    this.setState({
      App: app[viewName]
    });
  }

  render() {
    const { App } = this.state;
    return (
      <ErrorBoundary>
        <App
          ref={navigatorRef => {
            NavigationService.setTopLevelNavigator(navigatorRef);
          }}
        />
      </ErrorBoundary>
    );
  }
}
