import React from 'react';
import { View, StyleSheet, StatusBar, BackHandler } from 'react-native';
import CommentItem from '@/components/Comment/CommentItem';
import CommentInput from '@/components/Comment/CommentInput';
import { FlatList } from 'react-native-gesture-handler';
import { ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import SecondaryText from '@/components/AppText/SecondaryText';
import CommentSort from '@/components/Comment/CommentSort';
import { scale, themeLayout } from '@/config';
import { connect } from '@/utils/dva';
import SpringScrollView from '@/components/SpringScrollView';
import Header from './components/header';
import { COMMENT_TYPE } from '@/config/constants';
import commentHoc from '@/components/pageComponent/commentHoc';

class DetailChat extends React.Component {
  static navigationOptions = {
    header: null,
  };

  componentDidMount() {
    const { screenProps } = this.props;
    StatusBar.setBarStyle('dark-content', true);
    this.contendId = JSON.parse(screenProps.nativeProps.params).contentId;
    const data = {
      id: 0, // 上一页数据最小的  fabulous或者时间戳
      isFirst: true,
    };
    this.queryCommentDispatch(data);
  }

  componentWillUnmount() {
    this.props.dispatch({
      type: 'comment/saveCommentList',
      payload: [],
      isFirstPage: true,
    });
  }

  queryCommentDispatch = payload => {
    const data = {
      type: COMMENT_TYPE.chat,
      content_id: this.contendId,
      ...payload,
    };
    this.props.queryCommentDispatch('comment/queryCommentEffect', data);
  };

  handleSubmitComment = () => {
    const data = {
      type: COMMENT_TYPE.chat,
      content_id: this.contendId,
    };
    this.props.handleSubmitComment(data);
  };

  renderCommentItem = ({ item, index }) => (
    <CommentItem
      seeAllChildAction={this.handleSeeAllChild}
      replyAction={this.props.replyAction}
      itemData={item}
      index={index}
    />
  );

  handleSeeAllChild = (item, index) => {
    const { navigation } = this.props;
    navigation.navigate('ChildComment', {
      total: item.count,
      id: item.id,
      index,
      contendId: this.contendId,
      type: COMMENT_TYPE.chat,
    });
  };

  handleQuitActivity = () => {
    BackHandler.exitApp();
  };

  render() {
    const {
      textValue,
      activeTab,
      placeholder,
      allLoaded,
      comment,
      handleChangeSort,
      handleQueryNextPage,
      handleChangeText,
    } = this.props;
    return (
      <View style={styles.container}>
        <SpringScrollView
          ref={ref => (this.refScrollView = ref)}
          loadingFooter={ChineseNormalFooter}
          onLoading={() => handleQueryNextPage(comment.commentList)}
          allLoaded={allLoaded}
          bounces
        >
          <Header />
          <View style={styles.commentTitle}>
            <SecondaryText style={styles.commentTotal}>
              {comment.commentListTotal}个回答
            </SecondaryText>
            <CommentSort
              activeStyle={{ color: '#707070' }}
              activeTab={activeTab}
              changeSortAction={handleChangeSort}
            />
          </View>
          <FlatList
            keyExtractor={item => `${item.id}`}
            data={comment.commentList}
            renderItem={this.renderCommentItem}
          />
        </SpringScrollView>
        <CommentInput
          showLeftIcon
          leftIconAction={this.handleQuitActivity}
          showShare
          showCollection
          ref={ref => (this.refInputCon = ref)}
          handleChangeText={handleChangeText}
          handleSubmitComment={this.handleSubmitComment}
          textValue={textValue}
          placeholder={placeholder}
        />
      </View>
    );
  }
}

const mapStateToProps = ({ comment, loading }) => ({
  comment,
  loading: loading.effects['comment/queryXshareListEffect'],
});

export default connect(mapStateToProps)(commentHoc(DetailChat));

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
