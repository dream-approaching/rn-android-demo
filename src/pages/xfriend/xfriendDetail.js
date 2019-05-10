import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
// import { commentData } from '@/config/fakeData';
import { FlatList } from 'react-native-gesture-handler';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import CommentSort from '@/components/Comment/CommentSort';
import CommentInput from '@/components/Comment/CommentInput';
import SecondaryText from '@/components/AppText/SecondaryText';
import ChildItem from '@/components/Comment/ChildItem';
import { scale, themeLayout, themeColor, themeSize } from '@/config';
import { connect } from '@/utils/dva';
import commentHoc from '@/components/pageComponent/commentHoc';
import { COMMENT_TYPE } from '@/config/constants';

class XshareDetail extends React.Component {
  static navigationOptions = {
    header: null,
  };

  componentDidMount() {
    const { screenProps } = this.props;
    StatusBar.setBarStyle('dark-content', true);
    this.contendId = JSON.parse(screenProps.nativeProps.params).id;
    this.queryCommentDispatch({
      id: 0,
      isFirst: true,
    }); // 请求评论列表
  }

  componentWillUnmount() {
    this.props.dispatch({
      type: 'comment/saveAllCommentList',
      payload: [],
      isFirstPage: true,
    });
  }

  queryCommentDispatch = payload => {
    const data = {
      content_id: this.contendId,
      ...payload,
    };
    this.props.queryCommentDispatch('comment/queryAllCommentEffect', data);
  };

  renderCommentItem = ({ item }) => {
    return <ChildItem type='child' replyAction={this.props.replyAction} itemData={item} />;
  };

  handleSubmitComment = () => {
    const data = {
      type: COMMENT_TYPE.share,
      content_id: this.contendId,
    };
    this.props.handleSubmitComment(data);
  };

  render() {
    const {
      screenProps,
      comment,
      placeholder,
      textValue,
      activeTab,
      allLoaded,
      handleQueryNextPage,
      handleChangeSort,
      handleChangeText,
    } = this.props;
    return (
      <View style={styles.container}>
        <Header title='查看详情' />
        <SpringScrollView
          ref={ref => (this.refScrollView = ref)}
          loadingFooter={ChineseNormalFooter}
          onLoading={() => handleQueryNextPage(comment.allCommentList)}
          allLoaded={allLoaded}
          bounces
        >
          <XfriendItem noPress itemData={JSON.parse(screenProps.nativeProps.params)} />
          <View style={styles.replyCon}>
            <View style={styles.tabCon}>
              <SecondaryText style={styles.commentTotal}>
                {comment.allCommentListTotal}个回答
              </SecondaryText>
              <CommentSort
                activeStyle={{ color: themeColor.font.secondary }}
                activeTab={activeTab}
                changeSortAction={handleChangeSort}
              />
            </View>
            <FlatList
              keyExtractor={item => `${item.id}`}
              data={comment.allCommentList}
              renderItem={this.renderCommentItem}
            />
          </View>
        </SpringScrollView>
        <CommentInput
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
  loading: loading.effects['comment/queryAllCommentEffect'],
});

export default connect(mapStateToProps)(commentHoc(XshareDetail));

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
