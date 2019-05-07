import React from 'react';
import { View, StyleSheet, Keyboard, StatusBar } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import { xfriendData, commentData } from '@/config/fakeData';
import { FlatList } from 'react-native-gesture-handler';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import CommentSort from '@/components/Comment/CommentSort';
import CommentInput from '@/components/Comment/CommentInput';
import SecondaryText from '@/components/AppText/SecondaryText';
import ChildItem from '@/components/Comment/ChildItem';
import { scale, themeLayout, themeColor, themeSize } from '@/config';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  initialState = {
    allLoaded: false,
    textValue: '',
  };

  state = {
    ...this.initialState,
    activeTab: '2',
  };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
  }

  renderCommentItem = ({ item }) => {
    return <ChildItem type='child' replyAction={this.replyAction} itemData={item} />;
  };

  handleChangeSort = item => {
    this.setState({
      activeTab: item.type,
    });
  };

  handleChangeText = text => {
    this.setState({
      textValue: text,
    });
  };

  handleSubmitComment = () => {
    const { textValue, atSomeone } = this.state;
    const data = {
      type: 1,
      content_id: 8,
      content: textValue,
    };
    if (atSomeone) {
      const { name } = atSomeone;
      const withoutName = new Set([...textValue].filter(x => !new Set(name).has(x)));
      data.content = [...withoutName].join('');
      data.parent_id = atSomeone.id;
    }
    const successFn = () => {
      this.setState(this.initialState);
      Keyboard.dismiss();
      this.queryChildCommentDispatch({
        type: 1,
        content_id: 8,
        sort: 2,
      });
    };
    this.submitCommentDispatch(data, { successFn });
  };

  render() {
    const { textValue, activeTab, allLoaded } = this.state;
    return (
      <View style={styles.container}>
        <Header title='查看详情' />
        <SpringScrollView
          ref={ref => (this.refScrollView = ref)}
          loadingFooter={ChineseNormalFooter}
          onLoading={this.handleQueryNextPage}
          allLoaded={allLoaded}
          bounces
        >
          <XfriendItem itemData={xfriendData[0]} />
          <View style={styles.replyCon}>
            <View style={styles.tabCon}>
              <SecondaryText style={styles.commentTotal}>135个回答</SecondaryText>
              <CommentSort
                activeStyle={{ color: themeColor.font.secondary }}
                activeTab={activeTab}
                changeSortAction={this.handleChangeSort}
              />
            </View>
            <FlatList
              keyExtractor={item => `${item.id}`}
              data={commentData}
              renderItem={this.renderCommentItem}
            />
          </View>
        </SpringScrollView>
        <CommentInput
          ref={ref => (this.refInputCon = ref)}
          handleChangeText={this.handleChangeText}
          handleSubmitComment={this.handleSubmitComment}
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
  tabCon: {
    height: scale(31),
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.padding(0, scale(16)),
    ...themeLayout.border(),
  },
  replyCon: {
    backgroundColor: themeColor.bgF4,
  },
  commentTotal: {
    fontSize: themeSize.font.common,
  },
});
