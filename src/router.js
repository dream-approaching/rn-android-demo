import React, { Fragment } from 'react';
// import { PermissionsAndroid } from 'react-native';
import { createStackNavigator, createAppContainer } from 'react-navigation';
import { GetUserInfo } from '@/components/NativeModules';
import { connect } from '@/utils/dva';
import ErrorBoundary from '@/components/ErrorBoundary';
import NavigationService from '@/utils/NavigationService';
import Mine from '@/pages/cat/mine/mine';
import Recommend from '@/pages/shumei/recommend/recommend';
import RecommendEdit from '@/pages/shumei/recommend/recommendEdit';
import AddLabel from '@/pages/shumei/recommend/addLabel';
import XFriend from '@/pages/shumei/xfriend/xfriend';
import XFriendDetail from '@/pages/shumei/xfriend/xfriendDetail';
import CatComment from '@/pages/cat/comment/comment';
import ChildComment from '@/pages/cat/comment/childComment';
import DetailChat from '@/pages/cat/detailChat/detailChat';
import DetailWebview from '@/pages/shumei/detailWebview/detailWebview';
import EditUser from '@/pages/shumei/editUser/editUser';
import CollectArticle from '@/pages/shumei/myCollect/collectArticle';
import CollectApp from '@/pages/shumei/myCollect/collectApp';
import CollectChat from '@/pages/shumei/myCollect/collectChat';
import ClaimApp from '@/pages/shumei/claimApp/claimApp';
import MyNotice from '@/pages/shumei/myNotice/myNotice';
import ReplyNotice from '@/pages/shumei/myNotice/replyNotice';
import MyFeedback from '@/pages/shumei/myFeedback/myFeedback';
import MyShare from '@/pages/shumei/myshare/myshare';
import PersonPage from '@/pages/shumei/personPage/personPage';
import Search from '@/pages/shumei/search/search';
import MoreSearch from '@/pages/shumei/search/moreSearch';
import CatPublish from '@/pages/cat/catPublish/catPublish';
import ChooseLocation from '@/pages/cat/catPublish/chooseLocation';
import Home from '@/pages/cat/home/home';
import { init } from 'react-native-amap-geolocation';
import config from '@/config/index';
import Loading from '@/components/Loading/loading';
// import fundebug from 'fundebug-reactnative';

const CatPublishNav = createStackNavigator(
  { CatPublish, ChooseLocation },
  { initialRouteName: 'CatPublish' }
);
const HomeNav = createStackNavigator({ Home }, { initialRouteName: 'Home' });
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
const CatCommentNav = createStackNavigator(
  { CatComment, ChildComment },
  { initialRouteName: 'CatComment' }
);
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
const CatCommentContainer = createAppContainer(CatCommentNav);
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
const CatPublishContainer = createAppContainer(CatPublishNav);
const HomeContainer = createAppContainer(HomeNav);

class Router extends React.PureComponent {
  constructor(props) {
    super(props);
    console.log('Router constructor', new Date().getTime());
  }

  componentDidMount() {
    console.log('Router componentDidMount', new Date().getTime());
    this.geolocationInit();
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

    // fundebug.init({
    //   apikey: '1d8c7243ace176e819a931a09b729ee3e13a4976c6a5927a6b69aa8540dfe888',
    // });
  }

  componentWillUnmount() {
    console.log('router 卸载了');
  }

  geolocationInit = async () => {
    // try {
    //   const granted = await PermissionsAndroid.request(
    //     PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION,
    //     {
    //       title: '申请位置权限',
    //       message: '一个很牛逼的应用想借用你的位置权限，',
    //       buttonNeutral: '等会再问我',
    //       buttonNegative: '不行',
    //       buttonPositive: '好吧',
    //     }
    //   );
    //   console.log('%cgranted:', 'color: #0e93e0;background: #aaefe5;', granted);
    //   if (granted === PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION) {
    //     console.log('现在你获得位置权限了');
    //   } else {
    //     console.log('用户并不屌你');
    //   }
    // } catch (err) {
    //   console.warn('err:', err);
    // }
    await init({
      android: config.amapAndroidKey,
    });
  };

  render() {
    // const { App } = this.state;
    const { global = {}, screenProps } = this.props;
    const { nativeProps } = screenProps;
    const { veiw_name: viewName = MineContainer } = nativeProps;
    const app = {
      fragment4: XFriendContainer,
      fragment3: MineContainer,
      xFriendDetail: XFriendDetailContainer,
      comment: CatCommentContainer,
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
      catPublish: CatPublishContainer,
      home: HomeContainer,
    };
    const App = app[viewName];
    return (
      <ErrorBoundary>
        <Fragment>
          <App
            screenProps={this.props.screenProps}
            ref={navigatorRef => {
              NavigationService.setTopLevelNavigator(navigatorRef);
            }}
          />
          {global.globalLoading && <Loading />}
        </Fragment>
      </ErrorBoundary>
    );
  }
}

const mapStateToProps = ({ global }) => ({ global });

export default connect(mapStateToProps)(Router);
