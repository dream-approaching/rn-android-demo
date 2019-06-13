import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import CommentItem from '@/components/Comment/Cat/CommentItem';
import CommentInput from '@/components/Comment/Cat/CommentInput';
import { FlatList } from 'react-native-gesture-handler';
// import { commentData } from '@/config/fakeData';
import { connect } from '@/utils/dva';
import Header from '@/components/Header';
import commentHoc from '@/components/pageComponent/commentHoc';
import NoData from '@/components/NoData';
import FooterLoading from '@/components/Loading/FooterLoading';
import FirstLoading from '@/components/Loading/FirstLoading';

class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
    const { screenProps } = this.props;
    this.type = 1 || JSON.parse(screenProps.nativeProps.params).contentType;
    this.contendId = 19 || JSON.parse(screenProps.nativeProps.params).contentId;
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
      allLoaded,
      placeholder,
      comment,
      loading,
      handleQueryNextPage,
      handleChangeText,
      onblur,
    } = this.props;
    const showNoData = !comment.commentList.length && !loading;
    return (
      <View style={styles.container}>
        <Header title={`${comment.commentListTotal}条评论`} />
        <FirstLoading loading={loading}>
          {(!showNoData && (
            <FlatList
              keyExtractor={item => `${item.id}`}
              onEndReachedThreshold={0.1}
              onEndReached={() => handleQueryNextPage(comment.commentList)}
              data={comment.commentList}
              ListFooterComponent={<FooterLoading allLoaded={allLoaded} />}
              renderItem={this.renderCommentItem}
              // keyboardShouldPersistTaps='handled'
            />
          )) || <NoData text='暂无评论' />}
          <CommentInput
            ref={ref => (this.refInputCon = ref)}
            handleChangeText={handleChangeText}
            handleSubmitComment={this.handleSubmitComment}
            textValue={textValue}
            placeholder={placeholder}
            onblur={onblur}
          />
        </FirstLoading>
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
