import React from 'react';
import { View, StyleSheet, Image, TouchableOpacity } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import SecondaryText from '@/components/AppText/SecondaryText';
import { ChineseNormalFooter, ChineseNormalHeader } from 'react-native-spring-scrollview/Customize';
import { scale, themeLayout } from '@/config';
import { xfriendData } from '@/config/fakeData';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { lastArr } from '@/utils/utils';
import { OpenRnActivity } from '@/components/NativeModules';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  handleRefreshList = () => {
    setTimeout(() => {
      this.refScrollView.endRefresh();
    }, 2000);
  };

  handleQueryNextPage = () => {
    // const { comment } = this.props;
    // if (!comment.commentList.length) return null;
    // const { activeTab } = this.state;
    // const isHotSort = +activeTab === 1;
    // const lastItem = lastArr(comment.commentList);
    // this.queryCommentDispatch(
    //   {
    //     type: 1,
    //     content_id: 8,
    //     sort: 2,
    //     id: isHotSort ? lastItem.fabulous : lastItem.created_time,
    //   },
    //   {
    //     successFn: this.queryListSuccessFn,
    //   }
    // );
    console.log('%clastArr:', 'color: #0e93e0;background: #aaefe5;', lastArr);
    setTimeout(() => {
      this.refScrollView.endLoading();
    }, 2000);
  };

  gotoShare = () => {
    return OpenRnActivity('recommend');
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
