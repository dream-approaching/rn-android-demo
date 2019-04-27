import React from 'react';
import { View, StyleSheet } from 'react-native';
import CommentItem from '@/components/CommentItem';
import CommentInput from '@/components/CommentInput';
import { FlatList } from 'react-native-gesture-handler';
import { commentData } from '@/config/fakeData';
import { SpringScrollView } from 'react-native-spring-scrollview';
import { ChineseNormalHeader, ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import Header from './components/header';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    allLoaded: false,
    textValue: '',
  };

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

  render() {
    const { textValue } = this.state;
    return (
      <View style={styles.container}>
        <Header />
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
          <FlatList
            keyExtractor={item => `${item.id}`}
            data={commentData}
            renderItem={this.renderCommentItem}
          />
        </SpringScrollView>
        <CommentInput
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
});
