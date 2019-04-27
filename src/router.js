import React from 'react';
import { createStackNavigator, createAppContainer } from 'react-navigation';
import ErrorBoundary from '@/components/ErrorBoundary';
import Mine from '@/pages/mine/mine';
import Setting from '@/pages/setting/setting';
import Recommend from '@/pages/recommend/recommend';
import Chat from '@/pages/chat/chat';
import Comment from '@/pages/comment/comment';
import DetailChat from '@/pages/detailChat/detailChat';
import DetailWebview from '@/pages/detailWebview/detailWebview';
import NavigationService from '@/utils/NavigationService';

const RecommendNavigator = createStackNavigator({ Recommend }, { initialRouteName: 'Recommend' });
const ChatNavigator = createStackNavigator({ Chat }, { initialRouteName: 'Chat' });
const MineNavigator = createStackNavigator({ Mine, Setting }, { initialRouteName: 'Mine' });
const CommentNavigator = createStackNavigator({ Comment }, { initialRouteName: 'Comment' });
const DetailChatNavigator = createStackNavigator(
  { DetailChat },
  { initialRouteName: 'DetailChat' }
);
const DetailWebviewNavigator = createStackNavigator(
  { DetailWebview },
  { initialRouteName: 'DetailWebview' }
);

const RecommendContainer = createAppContainer(RecommendNavigator);
const ChatContainer = createAppContainer(ChatNavigator);
const MineContainer = createAppContainer(MineNavigator);
const CommentContainer = createAppContainer(CommentNavigator);
const DetailChatContainer = createAppContainer(DetailChatNavigator);
const DetailWebviewContainer = createAppContainer(DetailWebviewNavigator);

export default class Router extends React.Component {
  state = {
    App: RecommendContainer,
  };

  componentDidMount() {
    const { nativeProps } = this.props;
    const { veiw_name: viewName } = nativeProps;
    console.log('%cnativeProps:', 'color: #0e93e0;background: #aaefe5;', nativeProps);
    const app = {
      fragment1: RecommendContainer,
      fragment2: ChatContainer,
      fragment3: MineContainer,
      comment: CommentContainer,
      detailChat: DetailChatContainer,
      detailWebview: DetailWebviewContainer,
    };
    this.setState({
      App: app[viewName],
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
