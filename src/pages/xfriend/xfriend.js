import React from 'react';
import { View, StyleSheet, Image, TouchableOpacity } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import SecondaryText from '@/components/AppText/SecondaryText';
import { ChineseNormalFooter, ChineseNormalHeader } from 'react-native-spring-scrollview/Customize';
import { scale, themeLayout } from '@/config';
import { xfriendData } from '@/config/fakeData';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { lastArr, navigateBeforeCheckLogin } from '@/utils/utils';
import { connect } from '@/utils/dva';
import { OpenRnActivity } from '@/components/NativeModules';

class Xshare extends React.Component {
  static navigationOptions = {
    header: null,
  };

  componentDidMount() {
    this.queryXshareListDispatch();
  }

  queryXshareListDispatch = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'xshare/queryXshareListEffect',
      payload: 'data',
    });
  };

  handleRefreshList = () => {
    setTimeout(() => {
      this.refScrollView.endRefresh();
    }, 2000);
  };

  handleQueryNextPage = () => {
    const { xshare } = this.props;
    console.log('%cxshare:', 'color: #0e93e0;background: #aaefe5;', xshare);
    // if (!comment.commentList.length) return null;
    // const { activeTab } = this.state;
    // const isHotSort = +activeTab === 1;
    // const lastItem = lastArr(comment.commentList);
    this.queryXshareListDispatch();
    console.log('%clastArr:', 'color: #0e93e0;background: #aaefe5;', lastArr);
    setTimeout(() => {
      this.refScrollView.endLoading();
    }, 2000);
  };

  gotoShare = () => {
    return navigateBeforeCheckLogin(() => OpenRnActivity('recommend'));
  };

  render() {
    const avatar =
      'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1298508432,4221755458&fm=26&gp=0.jpg';
    return (
      <View style={styles.container}>
        <SpringScrollView
          ref={ref => (this.refScrollView = ref)}
          bounces
          loadingFooter={ChineseNormalFooter}
          onLoading={this.handleQueryNextPage}
          refreshHeader={ChineseNormalHeader}
          onRefresh={this.handleRefreshList}
        >
          <TouchableOpacity onPress={this.gotoShare} style={styles.shareCon}>
            <Image style={styles.avatar} source={{ uri: avatar }} />
            <SecondaryText>点击这里分享你喜爱的应用吧~ </SecondaryText>
          </TouchableOpacity>
          {xfriendData.map(item => {
            return <XfriendItem key={item.name} itemData={item} />;
          })}
        </SpringScrollView>
      </View>
    );
  }
}

const mapStateToProps = ({ xshare, loading }) => ({
  xshare,
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
