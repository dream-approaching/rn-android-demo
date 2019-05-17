import React from 'react';
import { DeviceEventEmitter } from 'react-native';
import { createStackNavigator, createAppContainer } from 'react-navigation';
import { GetUserInfo } from '@/components/NativeModules';
import { connect } from '@/utils/dva';
import ErrorBoundary from '@/components/ErrorBoundary';
import NavigationService from '@/utils/NavigationService';
import Empty from '@/pages/empty';
import Mine from '@/pages/mine/mine';
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
import ReplyNotice from '@/pages/myNotice/replyNotice';
import MyFeedback from '@/pages/myFeedback/myFeedback';
import MyShare from '@/pages/myshare/myshare';
import PersonPage from '@/pages/personPage/personPage';
import Search from '@/pages/search/search';
import MoreSearch from '@/pages/search/moreSearch';

// const transitionConfig = () => ({
//   transitionSpec: {
//     duration: 1000,
//     easing: Easing.out(Easing.poly(4)),
//     timing: Animated.timing,
//   },
//   screenInterpolator: sceneProps => {
//     const { layout, position, scene } = sceneProps;
//     const { index } = scene;

//     const height = layout.initHeight;
//     const translateY = position.interpolate({
//       inputRange: [index - 1, index, index + 1],
//       outputRange: [height, 0, 0],
//     });

//     const opacity = position.interpolate({
//       inputRange: [index - 1, index - 0.99, index],
//       outputRange: [0, 1, 1],
//     });

//     return { opacity, transform: [{ translateY }] };
//   },
// });

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
const MyNoticeNav = createStackNavigator(
  { MyNotice, ReplyNotice },
  { initialRouteName: 'MyNotice' }
);
const MyFeedbackNav = createStackNavigator({ MyFeedback }, { initialRouteName: 'MyFeedback' });
const MyShareNav = createStackNavigator({ MyShare }, { initialRouteName: 'MyShare' });
const PersonPageNav = createStackNavigator({ PersonPage }, { initialRouteName: 'PersonPage' });
const SearchNav = createStackNavigator({ Search, MoreSearch }, { initialRouteName: 'Search' });

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
const MyFeedbackContainer = createAppContainer(MyFeedbackNav);
const MyShareContainer = createAppContainer(MyShareNav);
const PersonPageContainer = createAppContainer(PersonPageNav);
const SearchContainer = createAppContainer(SearchNav);

class Router extends React.Component {
  state = {
    App: createAppContainer(EmptyNav),
  };

  componentDidMount() {
    // 配置activity
    const { nativeProps } = this.props.screenProps;
    const { veiw_name: viewName } = nativeProps;
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
      myFeedback: MyFeedbackContainer,
      myShare: MyShareContainer,
      personPage: PersonPageContainer,
      recommend: RecommendContainer,
      search: SearchContainer,
    };
    this.setState({
      App: app[viewName],
    });

    // 读取本地的用户信息设置到global model
    const { dispatch } = this.props;
    GetUserInfo.getUserInfoString(res => {
      if (res) {
        dispatch({
          type: 'global/saveUserInfo',
          payload: JSON.parse(res),
        });
      }
    });

    // 监听登录事件，重写global的userInfo
    DeviceEventEmitter.addListener('UserLogin', () => {
      console.log('监听到登录成功');
      GetUserInfo.getUserInfoString(res => {
        dispatch({
          type: 'global/saveUserInfo',
          payload: JSON.parse(res),
        });
      });
    });

    // 监听退出登录事件，清空global的userInfo
    DeviceEventEmitter.addListener('UserExit', () => {
      console.log('监听到退出登录');
      dispatch({
        type: 'global/saveUserInfo',
        payload: null,
      });
    });
  }

  render() {
    const { App } = this.state;
    return (
      <ErrorBoundary>
        <App
          screenProps={this.props.screenProps}
          ref={navigatorRef => {
            NavigationService.setTopLevelNavigator(navigatorRef);
          }}
        />
      </ErrorBoundary>
    );
  }
}

const mapStateToProps = ({ global }) => ({ global });

export default connect(mapStateToProps)(Router);
