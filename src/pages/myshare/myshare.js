import React from 'react';
import { View, StyleSheet } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import { ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import SimpleHeader from '@/components/ScrollHeader/SimpleHeader';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { lastArr, actionBeforeCheckLogin, clearRepeatArr } from '@/utils/utils';
import { connect } from '@/utils/dva';
import { OpenRnActivity } from '@/components/NativeModules';
import deleteModalHoc from '@/components/pageComponent/deleteModalHoc';
import FirstLoading from '@/components/Loading/FirstLoading';
import NoData from '@/components/NoData';

class MyXshare extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    allLoaded: false,
    isFirstTime: true,
  };

  componentDidMount() {
    this.defaultQueryList();
  }

  componentWillUnmount() {
    this.props.dispatch({
      type: 'saveOthershareList',
      payload: [],
      isFirstPage: true,
    });
  }

  defaultQueryList = () => {
    const data = {
      id: 0,
      isFirst: true,
    };
    this.queryOthershareListDispatch(data);
  };

  // 查询X友列表
  queryOthershareListDispatch = data => {
    const { dispatch, screenProps } = this.props;
    const { params } = screenProps.nativeProps;
    const phone = params ? JSON.parse(params).userPhone : '';
    const payload = {
      pagesize: 20,
      othermobilephone: phone,
      ...data,
    };
    dispatch({
      type: 'xshare/queryOtherShareListEffect',
      payload,
      successFn: response => {
        if (response.length === 0) {
          this.setState({
            allLoaded: true,
          });
        }
        this.setState({ isFirstTime: false });
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
    this.queryOthershareListDispatch(data);
    this.setState({ allLoaded: false });
  };

  handleQueryNextPage = () => {
    const { xshare } = this.props;
    const lastItem = lastArr(xshare.otherShareList);
    this.queryOthershareListDispatch({ id: lastItem.id });
  };

  handleDeleteCallBack = itemData => {
    const { xshare, dispatch } = this.props;
    dispatch({
      type: 'xshare/saveOthershareList',
      payload: clearRepeatArr(xshare.otherShareList, [itemData]),
      isFirstPage: true,
    });
  };

  gotoShare = () => {
    return actionBeforeCheckLogin(() => OpenRnActivity('recommend'));
  };

  render() {
    const { allLoaded, isFirstTime } = this.state;
    const { xshare, loading, handleShowDeleteModal, handleConfirmDelete } = this.props;
    return (
      <View style={styles.container}>
        <FirstLoading loading={loading && isFirstTime}>
          <SpringScrollView
            ref={ref => (this.refScrollView = ref)}
            bounces
            loadingFooter={ChineseNormalFooter}
            onLoading={this.handleQueryNextPage}
            refreshHeader={SimpleHeader}
            onRefresh={this.handleRefreshList}
            allLoaded={allLoaded}
          >
            {(!!xshare.otherShareList.length &&
              xshare.otherShareList.map(item => {
                return (
                  <XfriendItem
                    handleShowDeleteModal={handleShowDeleteModal}
                    handleConfirmDelete={handleConfirmDelete}
                    key={item.id}
                    itemData={item}
                    origin='myShare'
                  />
                );
              })) || <NoData text='暂无分享' />}
          </SpringScrollView>
        </FirstLoading>
      </View>
    );
  }
}

const mapStateToProps = ({ xshare, loading }) => ({
  xshare,
  loading: loading.effects['xshare/queryOtherShareListEffect'],
  deleteLoading: loading.effects['xshare/deleteXshareEffect'],
});

export default connect(mapStateToProps)(deleteModalHoc(MyXshare));

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});
