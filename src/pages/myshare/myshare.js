import React from 'react';
import { View, StyleSheet } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import { ChineseNormalFooter, ChineseNormalHeader } from 'react-native-spring-scrollview/Customize';
import { scale, themeLayout } from '@/config';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { lastArr, navigateBeforeCheckLogin } from '@/utils/utils';
import { connect } from '@/utils/dva';
import { OpenRnActivity, GetUserInfo } from '@/components/NativeModules';
import Loading from '@/components/Loading/loading';

class MyXshare extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    allLoaded: false,
  };

  componentDidMount() {
    const data = {
      id: 0,
      isFirst: true,
    };
    this.queryOthershareListDispatch(data);
  }

  // 查询X友列表
  queryOthershareListDispatch = params => {
    const { dispatch } = this.props;
    const data = {
      pagesize: 20,
      othermobilephone: GetUserInfo.phone,
      ...params,
    };
    dispatch({
      type: 'xshare/queryOtherShareListEffect',
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
    this.queryOthershareListDispatch(data);
    this.setState({ allLoaded: false });
  };

  handleQueryNextPage = () => {
    const { xshare } = this.props;
    const lastItem = lastArr(xshare.otherShareList);
    console.log('%cxshare:', 'color: #0e93e0;background: #aaefe5;', lastItem);
    this.queryOthershareListDispatch({ id: lastItem.id });
  };

  gotoShare = () => {
    return navigateBeforeCheckLogin(() => OpenRnActivity('recommend'));
  };

  render() {
    const { allLoaded } = this.state;
    const { xshare, loading } = this.props;
    if (loading && !xshare.otherShareList.length) return <Loading />;
    return (
      <View style={styles.container}>
        <SpringScrollView
          ref={ref => (this.refScrollView = ref)}
          bounces
          loadingFooter={ChineseNormalFooter}
          onLoading={this.handleQueryNextPage}
          refreshHeader={ChineseNormalHeader}
          onRefresh={this.handleRefreshList}
          allLoaded={allLoaded}
        >
          {xshare.otherShareList.map(item => {
            return <XfriendItem origin='myPage' key={item.id} itemData={item} />;
          })}
        </SpringScrollView>
      </View>
    );
  }
}

const mapStateToProps = ({ xshare, loading }) => ({
  xshare,
  loading: loading.effects['xshare/queryOtherShareListEffect'],
});

export default connect(mapStateToProps)(MyXshare);

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
