import React from 'react';
import { View, StyleSheet, ActivityIndicator, Text } from 'react-native';
import CommentItem from '@/components/CommentItem';
import CommentInput from '@/components/CommentInput';
// import { FlatList } from 'react-native-gesture-handler';
import { commentData } from '@/config/fakeData';
import PullRefreshView from '@/components/PullRefreshView/PullList';
import { scale } from '@/config';
import Header from './components/header';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    isLoadMore: false,
    isRefreshing: false,
  };

  onPullRelease = () => {
    this.setState({ isRefreshing: true });
    setTimeout(() => {
      this.setState({ isRefreshing: false });
    }, 2000);
  };

  ListFooterComponent = () => {
    const { isLoadMore } = this.state;
    if (isLoadMore) {
      return (
        <View
          style={{
            height: 40,
            alignItems: 'center',
            justifyContent: 'center',
            flexDirection: 'row',
          }}
        >
          <ActivityIndicator size="small" color="red" />
          <Text style={{ fontSize: 25, marginLeft: 5, color: '#333' }}>正在加载中……</Text>
        </View>
      );
    } else return <View />;
  };

  loadMore = () => {
    if (!this.state.isLoadMore) {
      this.setState({ isLoadMore: true });
      // 真实情况下，应在请求网络数据后的回调中修改isLoadMore
      setTimeout(() => {
        this.requestData();
        this.setState({ isLoadMore: false });
      }, 2000);
    }
  };

  requestData = () => {};

  render() {
    const { isRefreshing } = this.state;
    return (
      <View style={styles.container}>
        <Header />
        <PullRefreshView
          data={commentData}
          renderItem={({ item }) => <CommentItem itemData={item} />}
          keyExtractor={item => `${item.id}`}
          ListFooterComponent={this.ListFooterComponent}
          onEndReached={this.loadMore}
          onEndReachedThreshold={0.1}
          onPullRelease={this.onPullRelease}
          topIndicatorHeight={scale(60)}
          isRefreshing={isRefreshing}
        />
        <CommentInput />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: scale(18),
  },
  indicatorView: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    height: 60,
  },
  indicatorText: { color: '#333' },
});
