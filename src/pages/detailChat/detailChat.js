import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import CommentItem from '@/components/Comment/CommentItem';
import CommentInput from '@/components/Comment/CommentInput';
import { FlatList } from 'react-native-gesture-handler';
import { commentData } from '@/config/fakeData';
import { SpringScrollView } from 'react-native-spring-scrollview';
import { ChineseNormalHeader, ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import SecondaryText from '@/components/AppText/SecondaryText';
import CommentSort from '@/components/Comment/CommentSort';
import { scale, themeLayout } from '@/config';
import Header from './components/header';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    allLoaded: false,
    textValue: '',
    activeTab: 'new',
  };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
  }

  handleChangeText = text => {
    console.log('%ctext:', 'color: #0e93e0;background: #aaefe5;', text);
    this.setState({
      textValue: text,
    });
  };

  replyAction = item => {
    console.log('%citem:', 'color: #0e93e0;background: #aaefe5;', item);
    this.refInputCon.refInput.focus();
    this.handleChangeText(`回复${item.name}：`);
  };

  renderCommentItem = ({ item }) => <CommentItem replyAction={this.replyAction} itemData={item} />;

  handleChangeSort = item => {
    this.setState({
      activeTab: item.type,
    });
  };

  render() {
    const { textValue, activeTab } = this.state;
    return (
      <View style={styles.container}>
        <SpringScrollView
          ref={ref => (this.refScrollView = ref)}
          refreshHeader={ChineseNormalHeader}
          onRefresh={() => {
            setTimeout(() => {
              this.refScrollView.endRefresh();
            }, 3000);
          }}
          loadingFooter={ChineseNormalFooter}
          onLoading={() => {
            setTimeout(() => {
              this.refScrollView.endLoading();
            }, 3000);
          }}
          allLoaded={this.state.allLoaded}
        >
          <Header />
          <View style={styles.commentTitle}>
            <SecondaryText style={styles.commentTotal}>评论135</SecondaryText>
            <CommentSort
              activeStyle={{ color: '#707070' }}
              activeTab={activeTab}
              changeSortAction={this.handleChangeSort}
            />
          </View>
          <FlatList
            keyExtractor={item => `${item.id}`}
            data={commentData}
            renderItem={this.renderCommentItem}
          />
        </SpringScrollView>
        <CommentInput
          showLeftIcon
          showShare
          showCollection
          ref={ref => (this.refInputCon = ref)}
          handleChangeText={this.handleChangeText}
          textValue={textValue}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  commentTitle: {
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.padding(0, scale(16)),
    ...themeLayout.borderSide(),
    height: scale(46),
  },
  commentTotal: {
    fontSize: scale(16),
  },
});
