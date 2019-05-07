import React from 'react';
import { createStackNavigator, createAppContainer } from 'react-navigation';
import ErrorBoundary from '@/components/ErrorBoundary';
import NavigationService from '@/utils/NavigationService';
import Empty from '@/pages/empty';
import Mine from '@/pages/mine/mine';
import Setting from '@/pages/setting/setting';
import Recommend from '@/pages/recommend/recommend';
import RecommendEdit from '@/pages/recommend/recommendEdit';
import AddLabel from '@/pages/recommend/addLabel';
import XFriend from '@/pages/xfriend/xfriend';
import XFriendDetail from '@/pages/xfriend/xfriendDetail';
import Comment from '@/pages/comment/comment';
import ChildComment from '@/pages/comment/childComment';
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
import Search from '@/pages/search/search';
import MoreApp from '@/pages/search/moreApp';
import MoreArticle from '@/pages/search/moreArticle';
import MoreChat from '@/pages/search/moreChat';
import MoreShare from '@/pages/search/moreShare';

const EmptyNav = createStackNavigator({ Empty }, { initialRouteName: 'Empty' });
const RecommendNav = createStackNavigator(
  { Recommend, RecommendEdit, AddLabel },
  { initialRouteName: 'Recommend' }
);
const XFriendNav = createStackNavigator({ XFriend }, { initialRouteName: 'XFriend' });
const XFriendDetailNav = createStackNavigator(
  { XFriendDetail },
  { initialRouteName: 'XFriendDetail' }
);
const MineNav = createStackNavigator({ Mine }, { initialRouteName: 'Mine' });
const CommentNav = createStackNavigator({ Comment, ChildComment }, { initialRouteName: 'Comment' });
const DetailChatNav = createStackNavigator(
  { DetailChat, ChildComment },
  { initialRouteName: 'DetailChat' }
);
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
const SearchNav = createStackNavigator(
  { Search, MoreApp, MoreArticle, MoreChat, MoreShare },
  { initialRouteName: 'Search' }
);

const RecommendContainer = createAppContainer(RecommendNav);
const XFriendContainer = createAppContainer(XFriendNav);
const XFriendDetailContainer = createAppContainer(XFriendDetailNav);
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
const SearchContainer = createAppContainer(SearchNav);

export default class Router extends React.Component {
  state = {
    App: createAppContainer(EmptyNav),
  };

  componentDidMount() {
    const { nativeProps } = this.props;
    const { veiw_name: viewName } = nativeProps;
    console.log('%cparams:', 'color: #0e93e0;background: #aaefe5;', nativeProps);
    const app = {
      fragment4: XFriendContainer,
      fragment3: MineContainer,
      xFriendDetail: XFriendDetailContainer,
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
      search: SearchContainer,
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
