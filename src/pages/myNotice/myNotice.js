import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import ScrollableTabView from 'react-native-scrollable-tab-view';
import CommonText from '@/components/AppText/CommonText';
import { FlatList } from 'react-native-gesture-handler';
import SpringScrollView from '@/components/SpringScrollView';
import { ChineseNormalHeader } from 'react-native-spring-scrollview/Customize';
import Header from '@/components/Header';
import { comment } from '@/config/fakeData';
import ChildItem from '@/components/Comment/ChildItem';
import TabBar from './components/TabBar';

class MyNotice extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = { test: 1 };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
  }

  handleChangeTab = avtiveTab => {
    console.log('%cargs:', 'color: #0e93e0;background: #aaefe5;', avtiveTab);
  };

  handleRefresh = () => {
    setTimeout(() => {
      this.setState({ test: this.state.test + 1 }, () => {
        this.refScrollView.endRefresh();
      });
    }, 2000);
  };

  renderCommentItem = ({ item }) => {
    return <ChildItem type="child" replyAction={() => {}} itemData={item} />;
  };

  render() {
    return (
      <View style={styles.container}>
        <Header title="我的通知" />
        <ScrollableTabView onChangeTab={this.handleChangeTab} renderTabBar={() => <TabBar />}>
          <SpringScrollView
            tabLabel="评论"
            ref={ref => (this.refScrollView = ref)}
            refreshHeader={ChineseNormalHeader}
            onRefresh={this.handleRefresh}
            bounces
          >
            <FlatList
              keyExtractor={item => `${item.id}`}
              // data={commentData}
              data={comment.allCommentList}
              renderItem={this.renderCommentItem}
            />
          </SpringScrollView>
          <CommonText showDot tabLabel="点赞">
            favorite
          </CommonText>
          <CommonText showDot tabLabel="系统通知">
            project
          </CommonText>
        </ScrollableTabView>
      </View>
    );
  }
}

export default MyNotice;

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});
