import React from 'react';
import { View, StyleSheet, TouchableOpacity, AppState } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import SecondaryText from '@/components/AppText/SecondaryText';
import { ChineseNormalFooter, ChineseNormalHeader } from 'react-native-spring-scrollview/Customize';
import { scale, themeLayout } from '@/config';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { lastArr, navigateBeforeCheckLogin } from '@/utils/utils';
import { connect } from '@/utils/dva';
import { OpenRnActivity } from '@/components/NativeModules';
import Loading from '@/components/Loading/loading';
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
    console.log('%cxshare:', 'color: #0e93e0;background: #aaefe5;', lastItem);
    this.queryXshareListDispatch({ id: lastItem.id });
  };

  gotoShare = () => {
    return navigateBeforeCheckLogin(() => OpenRnActivity('recommend'));
  };

  render() {
    const { allLoaded } = this.state;
    const { xshare, loading } = this.props;
    const avatar =
      'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1298508432,4221755458&fm=26&gp=0.jpg';
    if (loading && !xshare.xshareList.length) return <Loading />;
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
          <TouchableOpacity onPress={this.gotoShare} style={styles.shareCon}>
            <ImageWithDefault style={styles.avatar} source={{ uri: avatar }} />
            <SecondaryText>点击这里分享你喜爱的应用吧~ </SecondaryText>
          </TouchableOpacity>
          {xshare.xshareList.map(item => {
            return <XfriendItem key={item.id} itemData={item} />;
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
});

export default connect(mapStateToProps)(Xshare);

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
