import React from 'react';
import { View, StyleSheet, StatusBar, TouchableOpacity, InteractionManager } from 'react-native';
import ScrollableTabView from 'react-native-scrollable-tab-view';
import CommonText from '@/components/AppText/CommonText';
import { FlatList } from 'react-native-gesture-handler';
import SpringScrollView from '@/components/SpringScrollView';
import { ChineseNormalHeader, ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import Header from '@/components/Header';
// import { notice } from '@/config/fakeData';
import CommentLikeItem from './components/commentLikeItem';
import { OpenRnActivity, OpenActivity } from '@/components/NativeModules';
import TabBar from './components/TabBar';
import { scale, themeLayout, themeSize } from '@/config';
import { COMMENT_TYPE, NOTICE_TYPE } from '@/config/constants';
import { lastArr } from '@/utils/utils';
import Toast from '@/components/Toast';
import MyModal from '@/components/Modal';
import { connect } from '@/utils/dva';
import WithLoading from '@/components/Loading/WithLoading';

class MyNotice extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    allLoaded_1: false,
    allLoaded_2: false,
    allLoaded_3: false,
    modalVisible: false,
    selectedItem: {},
    avtiveTab: '1',
  };

  componentDidMount() {
    this.defaultQueryList();
    StatusBar.setBarStyle('dark-content', true);
  }

  defaultQueryList = () => {
    const data = {
      id: 0,
      isFirst: true,
      notice_type: NOTICE_TYPE.comment,
    };
    this.queryNoticeListDispatch(data);
  };

  // 查询通知列表
  queryNoticeListDispatch = params => {
    const { dispatch } = this.props;
    const data = {
      pagesize: 10,
      ...params,
    };
    dispatch({
      type: 'notice/queryNoticeListEffect',
      payload: data,
      successFn: response => {
        if (response.length === 0) {
          this.setState({
            [`allLoaded_${data.notice_type}`]: true,
          });
        }
      },
      finallyFn: () => {
        if (data.isFirst) {
          this[`refScrollView_${data.notice_type}`] &&
            this[`refScrollView_${data.notice_type}`].endRefresh();
        } else {
          this[`refScrollView_${data.notice_type}`] &&
            this[`refScrollView_${data.notice_type}`].endLoading();
        }
      },
    });
  };

  handleChangeTab = async tabItem => {
    const avtiveTab = tabItem.ref.props.type;
    await this.setState({ avtiveTab });
    const { notice } = this.props;
    const { commentList, likeList, systemList } = notice;
    const dataSource = +avtiveTab === 1 ? commentList : +avtiveTab === 2 ? likeList : systemList;
    // 如果切换tab还没有数据，则发起第一次请求
    if (!dataSource.length) {
      this.handleRefresh();
    }
  };

  handleRefresh = () => {
    const { avtiveTab } = this.state;
    const data = {
      id: 0,
      isFirst: true,
      notice_type: avtiveTab,
    };
    this.queryNoticeListDispatch(data);
    this.setState({ [`allLoaded_${avtiveTab}`]: false });
  };

  handleQueryNextPage = () => {
    const { avtiveTab } = this.state;
    const { notice } = this.props;
    const { commentList, likeList, systemList } = notice;
    const dataSource = +avtiveTab === 1 ? commentList : +avtiveTab === 2 ? likeList : systemList;
    const lastItem = lastArr(dataSource);
    console.log('%cxshare:', 'color: #0e93e0;background: #aaefe5;', lastItem);
    this.queryNoticeListDispatch({ id: lastItem.id, notice_type: avtiveTab });
  };

  handleShowModal = itemData => {
    this.setState({
      modalVisible: true,
      selectedItem: itemData,
    });
  };

  handleHideModal = () => {
    const { modalVisible } = this.state;
    modalVisible && this.setState({ modalVisible: false });
  };

  renderCommentItem = ({ item }) => {
    return (
      <CommentLikeItem
        gotoReplyAction={this.gotoReply}
        gotoDetailAction={this.gotoDetail}
        showModalAction={this.handleShowModal}
        itemData={item}
      />
    );
  };

  gotoReply = async itemData => {
    const { navigation } = this.props;
    const { modalVisible } = this.state;
    if (modalVisible) {
      this.setState({ modalVisible: false });
      InteractionManager.runAfterInteractions(() => {
        navigation.navigate('ReplyNotice', {
          itemData,
        });
      });
    } else {
      navigation.navigate('ReplyNotice', {
        itemData,
      });
    }
  };

  gotoDetail = selectedItem => {
    console.log('%cselectedItem:', 'color: #0e93e0;background: #aaefe5;', selectedItem);
    this.handleHideModal();
    switch (+selectedItem.type) {
      case COMMENT_TYPE.app:
        OpenActivity.openAppDetails(selectedItem.id);
        break;
      case COMMENT_TYPE.article:
        Toast.show('数字生活研究所 webview');
        break;
      case COMMENT_TYPE.chat:
        OpenRnActivity('detailChat', JSON.stringify({ id: selectedItem.id }));
        break;
      case COMMENT_TYPE.recommend:
        Toast.show('应用推荐 webview');
        break;
      case COMMENT_TYPE.share:
        console.log('COMMENT_TYPE.share');
        OpenRnActivity('xFriendDetail', JSON.stringify({ id: selectedItem.id }));
        break;
      default:
        console.log('other');
    }
  };

  render() {
    const { modalVisible, selectedItem } = this.state;
    const { notice, loading } = this.props;
    const modalBtn = [
      {
        text: '回复',
        hasMargin: false,
        pressAction: () => this.gotoReply(selectedItem),
      },
      {
        text: '详情',
        hasMargin: false,
        pressAction: () => this.gotoDetail(selectedItem),
      },
      {
        text: '取消',
        hasMargin: true,
        pressAction: this.handleHideModal,
      },
    ];
    const tabArr = [
      {
        tabLabel: '评论',
        data: notice.commentList,
        type: '1',
      },
      {
        tabLabel: '点赞',
        data: notice.likeList,
        type: '2',
      },
      {
        tabLabel: '系统通知',
        data: notice.systemList,
        type: '3',
      },
    ];
    return (
      <View style={styles.container}>
        <Header title='我的通知' />
        <ScrollableTabView onChangeTab={this.handleChangeTab} renderTabBar={() => <TabBar />}>
          {tabArr.map(tabItem => {
            return (
              <SpringScrollView
                ref={ref => (this[`refScrollView_${tabItem.type}`] = ref)}
                refreshHeader={ChineseNormalHeader}
                loadingFooter={ChineseNormalFooter}
                onLoading={this.handleQueryNextPage}
                onRefresh={this.handleRefresh}
                bounces
                tabLabel={tabItem.tabLabel}
                key={tabItem.type}
                type={tabItem.type}
                allLoaded={this.state[`allLoaded_${tabItem.type}`]}
              >
                <WithLoading loading={loading && !tabItem.data.length}>
                  <FlatList
                    keyExtractor={item => `${item.id}`}
                    data={tabItem.data}
                    renderItem={this.renderCommentItem}
                  />
                </WithLoading>
              </SpringScrollView>
            );
          })}
        </ScrollableTabView>
        <MyModal
          hideModalAction={this.handleHideModal}
          isVisible={modalVisible}
          style={styles.bottomModal}
        >
          {modalBtn.map(item => {
            return (
              <TouchableOpacity
                key={item.text}
                activeOpacity={1}
                style={styles.modalBtn(item.hasMargin)}
                onPress={item.pressAction}
              >
                <CommonText>{item.text}</CommonText>
              </TouchableOpacity>
            );
          })}
        </MyModal>
      </View>
    );
  }
}

const mapStateToProps = ({ notice, loading }) => ({
  notice,
  loading: loading.effects['notice/queryNoticeListEffect'],
});

export default connect(mapStateToProps)(MyNotice);

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  bottomModal: {
    justifyContent: 'flex-end',
    margin: 0,
  },
  modalBtn: isCancel => {
    const style = {
      backgroundColor: '#fff',
      width: themeSize.screenWidth,
      height: scale(61),
      ...themeLayout.flex(),
      ...themeLayout.borderSide('Top'),
    };
    return isCancel ? { ...style, marginTop: scale(5) } : style;
  },
});
