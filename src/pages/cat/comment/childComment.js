import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import CommentInput from '@/components/Comment/Cat/CommentInput';
import { FlatList } from 'react-native-gesture-handler';
// import { commentData } from '@/config/fakeData';
import { connect } from '@/utils/dva';
import { themeCatColor } from '@/config';
import ChildItem from '@/components/Comment/Cat/ChildItem';
import Header from '@/components/Header';
import { COMMENT_TYPE } from '@/config/constants';
import commentHoc from '@/components/pageComponent/commentHoc';
import FirstLoading from '@/components/Loading/FirstLoading';
import FooterLoading from '@/components/Loading/FooterLoading';

class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  constructor(props) {
    super(props);
    const { navigation } = props;
    this.parentId = navigation.getParam('id');
    this.type = navigation.getParam('type');
    this.contendId = navigation.getParam('contendId');
    this.parentIndex = navigation.getParam('index');
    this.total = navigation.getParam('total');
  }

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
    const data = {
      id: 0,
      isFirst: true,
    };
    this.queryCommentDispatch(data);
  }

  componentWillUnmount() {
    this.props.dispatch({
      type: 'comment/saveChildCommentList',
      payload: [],
      isFirstPage: true,
    });
  }

  queryCommentDispatch = payload => {
    const data = {
      type: this.type,
      content_id: this.contendId,
      parent_id: this.parentId,
      ...payload,
    };
    this.props.queryCommentDispatch('comment/queryChildCommentEffect', data);
  };

  handleSubmitComment = () => {
    const data = {
      type: COMMENT_TYPE.chat,
      content_id: this.contendId,
    };
    this.props.handleSubmitComment(data);
  };

  renderCommentItem = ({ item }) => {
    return <ChildItem type='child' replyAction={this.props.replyAction} itemData={item} />;
  };

  render() {
    const {
      textValue,
      placeholder,
      comment,
      navigation,
      handleQueryNextPage,
      handleChangeText,
      commentLoading,
      allLoaded,
      onblur,
    } = this.props;
    return (
      <View style={styles.container}>
        <Header navigation={navigation} title={`${this.total}条评论`} />
        <FirstLoading loading={commentLoading}>
          <FlatList
            keyExtractor={item => `${item.id}_${Math.random()}`}
            onEndReachedThreshold={0.1}
            ListHeaderComponent={
              <ChildItem
                type='main'
                replyAction={this.replyAction}
                itemData={comment.commentList[this.parentIndex]}
              />
            }
            onEndReached={() => handleQueryNextPage(comment.childCommentList)}
            data={[
              ...comment.childCommentList,
              ...comment.childCommentList,
              ...comment.childCommentList,
            ]}
            ListFooterComponent={
              <FooterLoading bgColor={themeCatColor.bgF4} allLoaded={allLoaded} />
            }
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

const mapStateToProps = ({ comment, loading }) => ({
  comment,
  loading: loading.effects['comment/submitCommentEffect'],
  commentLoading: loading.effects['comment/queryChildCommentEffect'],
});

export default connect(mapStateToProps)(commentHoc(CommentPage));

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // backgroundColor: themeCatColor.bgF4,
  },
});
