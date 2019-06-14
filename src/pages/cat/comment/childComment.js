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
    this.state = {
      isFirstTime: true,
    };
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
      type: 'catComment/saveChildCommentList',
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
    this.props.queryCommentDispatch('catComment/queryChildCommentEffect', data, () =>
      this.setState({ isFirstTime: false })
    );
  };

  handleSubmitComment = () => {
    const data = {
      type: COMMENT_TYPE.share,
      content_id: this.contendId,
      parent_id: this.parentId,
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
      catComment,
      navigation,
      handleQueryNextPage,
      handleChangeText,
      commentLoading,
      allLoaded,
      onblur,
    } = this.props;
    const { isFirstTime } = this.state;
    return (
      <View style={styles.container}>
        <Header navigation={navigation} title={`${this.total}条评论`} />
        <FirstLoading loading={commentLoading && isFirstTime}>
          <FlatList
            keyExtractor={item => `${item.id}`}
            onEndReachedThreshold={0.1}
            ListHeaderComponent={
              <ChildItem type='main' itemData={catComment.commentList[this.parentIndex]} />
            }
            onEndReached={() => handleQueryNextPage(catComment.childCommentList)}
            data={catComment.childCommentList}
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

const mapStateToProps = ({ catComment, loading }) => ({
  catComment,
  loading: loading.effects['catComment/submitCommentEffect'],
  commentLoading: loading.effects['catComment/queryChildCommentEffect'],
});

export default connect(mapStateToProps)(commentHoc(CommentPage));

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // backgroundColor: themeCatColor.bgF4,
  },
});
