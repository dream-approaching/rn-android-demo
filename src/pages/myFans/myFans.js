import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import { FlatList } from 'react-native-gesture-handler';
import { comment } from '@/config/fakeData';
import SpringScrollView from '@/components/SpringScrollView';
import ChildItem from '@/components/Comment/ChildItem';
// import { ChineseNormalHeader } from 'react-native-spring-scrollview/Customize';
import Header from '@/components/Header';
import ChineseNormalHeader from './scrollHeader';

class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = { test: 0 };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
  }

  renderCommentItem = ({ item }) => {
    return <ChildItem type="child" replyAction={() => {}} itemData={item} />;
  };

  handleRefresh = () => {
    setTimeout(() => {
      this.setState({ test: this.state.test + 1 }, () => {
        this.refScrollView.endRefresh();
      });
    }, 2000);
  };

  render() {
    // if (loading) return <Loading />;
    return (
      <View style={styles.container}>
        <Header title={this.state.test} />
        <SpringScrollView
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
      </View>
    );
  }
}

export default CommentPage;

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});
