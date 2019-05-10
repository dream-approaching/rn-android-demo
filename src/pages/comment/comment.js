import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import CommentItem from '@/components/Comment/CommentItem';
import CommentInput from '@/components/Comment/CommentInput';
import { FlatList } from 'react-native-gesture-handler';
// import { commentData } from '@/config/fakeData';
import { connect } from '@/utils/dva';
import SpringScrollView from '@/components/SpringScrollView';
import CommentSort from '@/components/Comment/CommentSort';
import { ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import Header from '@/components/Header';
import commentHoc from '@/components/pageComponent/commentHoc';
// import Loading from '@/components/Loading/loading';

class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
    const { screenProps } = this.props;
    this.type = 1 || JSON.parse(screenProps.nativeProps.params).type;
    this.contendId = 8 || JSON.parse(screenProps.nativeProps.params).contentId;
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
      type: this.type,
      content_id: this.contendId,
      ...payload,
    };
    this.props.queryCommentDispatch('comment/queryCommentEffect', data);
  };

  handleSubmitComment = () => {
    const data = {
      type: this.type,
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
      type: this.type,
    });
  };

  render() {
    const {
      textValue,
      activeTab,
      allLoaded,
      placeholder,
      comment,
      handleChangeSort,
      handleQueryNextPage,
      handleChangeText,
    } = this.props;
    // if (loading) return <Loading />;
    return (
      <View style={styles.container}>
        <Header
          title={`${comment.commentListTotal}条评论`}
          rightComponent={<CommentSort activeTab={activeTab} changeSortAction={handleChangeSort} />}
        />
        <SpringScrollView
          ref={ref => (this.refScrollView = ref)}
          loadingFooter={ChineseNormalFooter}
          onLoading={() => handleQueryNextPage(comment.commentList)}
          allLoaded={allLoaded}
          bounces
        >
          <FlatList
            keyExtractor={item => `${item.id}`}
            // data={commentData}
            data={comment.commentList}
            renderItem={this.renderCommentItem}
          />
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
  loading: loading.effects['comment/queryCommentEffect'],
  submitLoading: loading.effects['comment/submitCommentEffect'],
});

export default connect(mapStateToProps)(commentHoc(CommentPage));

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});
