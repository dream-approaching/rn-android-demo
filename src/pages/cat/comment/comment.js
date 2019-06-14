import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import CommentItem from '@/components/Comment/Cat/CommentItem';
import CommentInput from '@/components/Comment/Cat/CommentInput';
import { FlatList } from 'react-native-gesture-handler';
// import { commentData } from '@/config/fakeData';
import { connect } from '@/utils/dva';
import Header from '@/components/Header';
import commentHoc from '@/components/pageComponent/commentHoc';
import FooterLoading from '@/components/Loading/FooterLoading';
import FirstLoading from '@/components/Loading/FirstLoading';

class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    isFirstTime: true,
  };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
    const { navigation } = this.props;
    this.type = navigation.getParam('type');
    this.contendId = navigation.getParam('contendId');
    console.log(
      '%cthis.contendId:',
      'color: #0e93e0;background: #aaefe5;',
      this.type,
      this.contendId
    );
    const data = {
      id: 0, // 上一页数据最小的  fabulous或者时间戳
      isFirst: true,
    };
    this.queryCommentDispatch(data);
  }

  componentWillUnmount() {
    this.props.dispatch({
      type: 'catComment/saveCommentList',
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
    this.props.queryCommentDispatch('catComment/queryCommentEffect', data, () =>
      this.setState({ isFirstTime: false })
    );
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
    navigation.navigate('CatChildComment', {
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
      catComment,
      loading,
      handleQueryNextPage,
      handleChangeText,
      onblur,
    } = this.props;
    const { isFirstTime } = this.state;
    return (
      <View style={styles.container}>
        <Header
          title={`${+catComment.commentListTotal ? `${catComment.commentListTotal}条` : ''}评论`}
        />
        <FirstLoading loading={loading && isFirstTime}>
          <FlatList
            keyExtractor={item => `${item.id}`}
            onEndReachedThreshold={0.1}
            onEndReached={() => handleQueryNextPage(catComment.commentList)}
            data={catComment.commentList}
            ListFooterComponent={<FooterLoading allLoaded={allLoaded} />}
            renderItem={this.renderCommentItem}
          />
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

const mapStateToProps = ({ catComment, loading }) => ({
  catComment,
  loading: loading.effects['catComment/queryCommentEffect'],
  submitLoading: loading.effects['catComment/submitCommentEffect'],
});

export default connect(mapStateToProps)(commentHoc(CommentPage));

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});
