import React from 'react';
import { View, StyleSheet, AppState } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import SpringScrollView from '@/components/SpringScrollView';
import SecondaryText from '@/components/AppText/SecondaryText';
import { ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import SimpleHeader from '@/components/ScrollHeader/SimpleHeader';
import { scale, themeLayout } from '@/config';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { lastArr, actionBeforeCheckLogin, clearRepeatArr } from '@/utils/utils';
import { connect } from '@/utils/dva';
import { OpenRnActivity } from '@/components/NativeModules';
import Loading from '@/components/Loading/loading';
import deleteModalHoc from '@/components/pageComponent/deleteModalHoc';
import ImageWithDefault from '@/components/ImageWithDefault';

class Xshare extends React.Component {
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
      pagesize: 20,
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
        }
      },
    });
  };

  handleRefreshList = () => {
    const data = {
      id: 0,
      isFirst: true,
    };
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
  //   const label = ['工具'];
  //   dispatch({
  //     type: 'recommend/submitXShareEffect',
  //     payload: {
  //       content: 'textValue',
  //       app_info: '10617',
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
          loadingFooter={ChineseNormalFooter}
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
          {xshare.xshareList.map(item => {
            return (
              <XfriendItem
                handleShowDeleteModal={handleShowDeleteModal}
                handleConfirmDelete={handleConfirmDelete}
                attentionLikeCallback={this.handleRefreshCallback}
                key={item.id}
                itemData={item}
              />
            );
          })}
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
});
