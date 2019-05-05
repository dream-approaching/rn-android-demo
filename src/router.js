import React from 'react';
import { createStackNavigator, createAppContainer } from 'react-navigation';
import ErrorBoundary from '@/components/ErrorBoundary';
import NavigationService from '@/utils/NavigationService';
import Empty from '@/pages/empty';
import Mine from '@/pages/mine/mine';
import Setting from '@/pages/setting/setting';
import Recommend from '@/pages/recommend/recommend';
import Chat from '@/pages/chat/chat';
import Comment from '@/pages/comment/comment';
import CommentDetail from '@/pages/comment/commentDetail';
import DetailChat from '@/pages/detailChat/detailChat';
import DetailWebview from '@/pages/detailWebview/detailWebview';
import EditUser from '@/pages/editUser/editUser';
import CollectArticle from '@/pages/myCollect/collectArticle';
import CollectApp from '@/pages/myCollect/collectApp';
import CollectChat from '@/pages/myCollect/collectChat';
import ClaimApp from '@/pages/claimApp/claimApp';
import MyNotice from '@/pages/myNotice/myNotice';
import MyAttention from '@/pages/myAttention/myAttention';
import MyFans from '@/pages/myFans/myFans';
import MyFeedback from '@/pages/myFeedback/myFeedback';
import PersonPage from '@/pages/personPage/personPage';

const EmptyNav = createStackNavigator({ Empty }, { initialRouteName: 'Empty' });
const RecommendNav = createStackNavigator({ Recommend }, { initialRouteName: 'Recommend' });
const ChatNav = createStackNavigator({ Chat }, { initialRouteName: 'Chat' });
const MineNav = createStackNavigator({ Mine }, { initialRouteName: 'Mine' });
const CommentNav = createStackNavigator(
  { Comment, CommentDetail },
  { initialRouteName: 'Comment' }
);
const DetailChatNav = createStackNavigator({ DetailChat }, { initialRouteName: 'DetailChat' });
const DetailWebviewNav = createStackNavigator(
  { DetailWebview },
  { initialRouteName: 'DetailWebview' }
);
const EditUserNav = createStackNavigator({ EditUser }, { initialRouteName: 'EditUser' });
const CollectArticleNav = createStackNavigator(
  { CollectArticle },
  { initialRouteName: 'CollectArticle' }
);
const CollectAppNav = createStackNavigator({ CollectApp }, { initialRouteName: 'CollectApp' });
const CollectChatNav = createStackNavigator({ CollectChat }, { initialRouteName: 'CollectChat' });
const ClaimAppNav = createStackNavigator({ ClaimApp }, { initialRouteName: 'ClaimApp' });
const MyNoticeNav = createStackNavigator({ MyNotice }, { initialRouteName: 'MyNotice' });
const MyAttentionNav = createStackNavigator({ MyAttention }, { initialRouteName: 'MyAttention' });
const MyFansNav = createStackNavigator({ MyFans }, { initialRouteName: 'MyFans' });
const SettingNav = createStackNavigator({ Setting }, { initialRouteName: 'Setting' });
const MyFeedbackNav = createStackNavigator({ MyFeedback }, { initialRouteName: 'MyFeedback' });
const PersonPageNav = createStackNavigator({ PersonPage }, { initialRouteName: 'PersonPage' });

const RecommendContainer = createAppContainer(RecommendNav);
const ChatContainer = createAppContainer(ChatNav);
const MineContainer = createAppContainer(MineNav);
const CommentContainer = createAppContainer(CommentNav);
const DetailChatContainer = createAppContainer(DetailChatNav);
const DetailWebviewContainer = createAppContainer(DetailWebviewNav);
const EditUserContainer = createAppContainer(EditUserNav);
const CollectArticleContainer = createAppContainer(CollectArticleNav);
const CollectAppContainer = createAppContainer(CollectAppNav);
const CollectChatContainer = createAppContainer(CollectChatNav);
const ClaimAppContainer = createAppContainer(ClaimAppNav);
const MyNoticeContainer = createAppContainer(MyNoticeNav);
const MyAttentionContainer = createAppContainer(MyAttentionNav);
const MyFansContainer = createAppContainer(MyFansNav);
const SettingContainer = createAppContainer(SettingNav);
const MyFeedbackContainer = createAppContainer(MyFeedbackNav);
const PersonPageContainer = createAppContainer(PersonPageNav);

export default class Router extends React.Component {
  state = {
    App: createAppContainer(EmptyNav),
  };

  componentDidMount() {
    const { nativeProps } = this.props;
    const { veiw_name: viewName } = nativeProps;
    const app = {
      fragment2: ChatContainer,
      fragment3: MineContainer,
      comment: CommentContainer,
      detailChat: DetailChatContainer,
      detailWebview: DetailWebviewContainer,
      editUser: EditUserContainer,
      collectArticle: CollectArticleContainer,
      collectApp: CollectAppContainer,
      collectChat: CollectChatContainer,
      claimApp: ClaimAppContainer,
      myNotice: MyNoticeContainer,
      myAttention: MyAttentionContainer,
      myFans: MyFansContainer,
      setting: SettingContainer,
      myFeedback: MyFeedbackContainer,
      personPage: PersonPageContainer,
      recommend: RecommendContainer,
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
