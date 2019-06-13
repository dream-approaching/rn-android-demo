import React from 'react';
import { View, StyleSheet, AppState, DeviceEventEmitter, FlatList } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import SpringScrollView from '@/components/SpringScrollView';
import SecondaryText from '@/components/AppText/SecondaryText';
import SimpleHeader from '@/components/ScrollHeader/SimpleHeader';
import { scale, themeLayout } from '@/config';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { lastArr, actionBeforeCheckLogin, clearRepeatArr, removeArrIndex } from '@/utils/utils';
import { connect } from '@/utils/dva';
import { OpenRnActivity, GetUserInfo } from '@/components/NativeModules';
import Loading from '@/components/Loading/loading';
import deleteModalHoc from '@/components/pageComponent/deleteModalHoc';
import ImageWithDefault from '@/components/ImageWithDefault';
import SimpleFooter from '@/components/ScrollFooter/SimpleFooter';
// import { xshare as XSHARE } from '@/config/fakeData';

class Xshare extends React.PureComponent {
  static navigationOptions = {
    header: null,
  };

  state = {
    allLoaded: false,
  };

  componentDidMount() {
    const { dispatch } = this.props;
    this.defaultQueryList();
    AppState.addEventListener('change', () => {
      if (this.props.global.shouldXshareRefresh) {
        this.defaultQueryList();
        dispatch({ type: 'global/toggleXshareRefreshEffect', payload: false });
      }
    });

    // 监听原生发送的事件
    this.nativeLister = DeviceEventEmitter.addListener('native_call_rn_params', strParams => {
      const params = JSON.parse(strParams);
      console.log('%cparams:', 'color: #0e93e0;background: #aaefe5;', params);
      const { global, xshare } = this.props;
      const obj = {};
      switch (params.option) {
        case 'set_follow':
          console.log('监听到切换关注', params);
          Object.values(global.xshareData).map(item => {
            if (item.mobilephone === params.mobilephone) {
              item.is_add_friends = params.attention;
              obj[item.id] = item;
            }
          });
          dispatch({
            type: 'global/saveXshareData',
            payload: obj,
          });
          break;
        case 'delete_x_share':
          console.log('监听到删除个人页的莓友分享', params);
          if (global.xshareData[params.contentId]) {
            // 在已储存的列表中有删除的那一条
            dispatch({
              type: 'global/saveXshareData',
              payload: {
                type: 'del',
                delItemId: params.contentId,
              },
            });
          }
          const index = xshare.xshareList.findIndex(item => item.id === params.contentId);
          if (index > -1) {
            // 在X友分享列表中有删除的那一条
            console.log('%cindex:', 'color: #0e93e0;background: #aaefe5;', index);
            dispatch({
              type: 'xshare/saveXshareList',
              payload: removeArrIndex(xshare.xshareList, index),
              isFirstPage: true,
            });
          }
          break;
        case 'set_fabulous_x_share':
          console.log('监听到修改个人页的莓友分享点赞', params);
          const editShareItem = global.xshareData[params.contentId];
          editShareItem.fabulous = params.fabulousCount;
          editShareItem.is_fabulous = params.fabulous;
          dispatch({
            type: 'global/saveXshareData',
            payload: editShareItem,
          });
          break;
        case 'login':
          console.log('监听到登录', params);
          GetUserInfo.getUserInfoString(res => {
            dispatch({
              type: 'global/saveUserInfo',
              payload: JSON.parse(res),
            });
          });
          break;
        case 'UpdataUserInfo':
          console.log('监听到修改用户信息', params);
          GetUserInfo.getUserInfoString(res => {
            dispatch({
              type: 'global/saveUserInfo',
              payload: JSON.parse(res),
            });
          });
          // 重新请求莓友分享列表
          this.handleRefreshList();
          break;
        case 'UserExit':
          console.log('监听到退出登录', params);
          dispatch({
            type: 'global/saveUserInfo',
            payload: null,
          });
          dispatch({
            type: 'search/saveHistorySearchList',
            payload: [],
          });
          break;
        default:
          console.log('未处理的事件：', params);
      }
    });
  }

  componentWillUnmount() {
    this.nativeLister.remove();
  }

  defaultQueryList = () => {
    const data = {
      id: 0,
      isFirst: true,
    };
    this.queryXshareListDispatch(data);
  };

  // 查询X友列表
  queryXshareListDispatch = params => {
    const { dispatch } = this.props;
    const data = {
      pagesize: 10,
      ...params,
    };
    dispatch({
      type: 'xshare/queryXshareListEffect',
      payload: data,
      successFn: response => {
        if (response.length === 0) {
          this.setState({
            allLoaded: true,
          });
        }
      },
      finallyFn: () => {
        if (data.isFirst) {
          this.refScrollView && this.refScrollView.endRefresh();
        } else {
          this.refScrollView && this.refScrollView.endLoading();
          dispatch({
            type: 'global/changeLoadingEffect',
            payload: false,
            successFn: () => {
              setTimeout(() => {
                dispatch({
                  type: 'global/changeLoadingEffect',
                  payload: true,
                });
              }, 1000);
            },
          });
        }
      },
    });
  };

  handleRefreshList = () => {
    const data = {
      id: 0,
      isFirst: true,
    };
    const { dispatch } = this.props;
    dispatch({ type: 'global/cleanXshareData' });
    this.queryXshareListDispatch(data);
    this.setState({ allLoaded: false });
  };

  handleQueryNextPage = () => {
    const { xshare } = this.props;
    const lastItem = lastArr(xshare.xshareList);
    this.queryXshareListDispatch({ id: lastItem.id });
  };

  handleDeleteCallBack = itemData => {
    const { xshare, dispatch } = this.props;
    dispatch({
      type: 'xshare/saveXshareList',
      payload: clearRepeatArr(xshare.xshareList, [itemData]),
      isFirstPage: true,
    });
  };

  handleRefreshCallback = () => {
    const { xshare } = this.props;
    this.queryXshareListDispatch({
      id: 0,
      isFirst: true,
      pagesize: xshare.xshareList.length,
    });
  };

  gotoShare = () => OpenRnActivity('recommend');

  // // 测试用
  // gotoShare = () => {
  //   const { dispatch } = this.props;
  //   const label = ['工具', '生活'];
  //   dispatch({
  //     type: 'recommend/submitXShareEffect',
  //     payload: {
  //       content: '很老牌的app了，经常有很大的优惠，我还办了会员，也可以点外卖',
  //       app_info: '6431',
  //       label: label.join(','),
  //     },
  //     successFn: () => {
  //       dispatch({
  //         type: 'global/toggleXshareRefreshEffect',
  //         payload: true,
  //         successFn: () => this.defaultQueryList(),
  //       });
  //     },
  //   });
  // };

  render() {
    const { allLoaded } = this.state;
    const { xshare, loading, handleShowDeleteModal, handleConfirmDelete, global } = this.props;
    const { userInfo } = global;
    if (loading && !xshare.xshareList.length) return <Loading />;
    return (
      <View style={styles.container}>
        <SpringScrollView
          ref={ref => (this.refScrollView = ref)}
          bounces
          contentStyle={{ backgroundColor: '#fff' }}
          loadingFooter={SimpleFooter}
          onLoading={this.handleQueryNextPage}
          refreshHeader={SimpleHeader}
          onRefresh={this.handleRefreshList}
          allLoaded={allLoaded}
        >
          <TouchableNativeFeedback notOut onPress={() => actionBeforeCheckLogin(this.gotoShare)}>
            <View style={styles.shareCon}>
              <ImageWithDefault
                style={styles.avatar}
                source={{ uri: userInfo ? userInfo.head_image : '' }}
              />
              <SecondaryText>点击这里分享你喜爱的应用吧~ </SecondaryText>
            </View>
          </TouchableNativeFeedback>
          <FlatList
            keyExtractor={item => `${item.id}`}
            data={xshare.xshareList}
            renderItem={({ item }) => {
              return (
                <View style={styles.xfriendItemCon}>
                  <XfriendItem
                    handleShowDeleteModal={handleShowDeleteModal}
                    handleConfirmDelete={handleConfirmDelete}
                    itemData={item}
                  />
                </View>
              );
            }}
          />
        </SpringScrollView>
      </View>
    );
  }
}

const mapStateToProps = ({ xshare, loading, global }) => ({
  xshare,
  global,
  loading: loading.effects['xshare/queryXshareListEffect'],
  deleteLoading: loading.effects['xshare/deleteXshareEffect'],
});

export default connect(mapStateToProps)(deleteModalHoc(Xshare));

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  shareCon: {
    ...themeLayout.flex('row'),
    ...themeLayout.border(scale(1)),
    ...themeLayout.padding(scale(5), 0),
    borderRadius: scale(6),
    width: scale(260),
    alignSelf: 'center',
    // elevation: themeSize.minBorder,
    marginTop: scale(16),
    marginBottom: scale(10),
  },
  avatar: {
    width: scale(28),
    height: scale(28),
    backgroundColor: 'lightblue',
    borderRadius: scale(14),
    marginRight: scale(10),
  },
  xfriendItemCon: {
    ...themeLayout.padding(0, scale(16)),
  },
});
