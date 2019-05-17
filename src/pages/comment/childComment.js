import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import CommentInput from '@/components/Comment/CommentInput';
import { FlatList } from 'react-native-gesture-handler';
// import { commentData } from '@/config/fakeData';
import { connect } from '@/utils/dva';
import { themeColor, scale, themeLayout } from '@/config';
import SpringScrollView from '@/components/SpringScrollView';
import CommentSort from '@/components/Comment/CommentSort';
import ChildItem from '@/components/Comment/ChildItem';
import { ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import Header from '@/components/Header';
import { COMMENT_TYPE } from '@/config/constants';
import commentHoc from '@/components/pageComponent/commentHoc';
import FirstLoading from '@/components/Loading/FirstLoading';

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
      activeTab,
      allLoaded,
      placeholder,
      comment,
      navigation,
      handleChangeSort,
      handleQueryNextPage,
      handleChangeText,
      commentLoading,
    } = this.props;
    console.log('comment.childCommentList', comment.childCommentList);
    return (
      <View style={styles.container}>
        <Header navigation={navigation} title={`${this.total}条评论`} />
        <FirstLoading loading={commentLoading}>
          <SpringScrollView
            ref={ref => (this.refScrollView = ref)}
            loadingFooter={ChineseNormalFooter}
            onLoading={() => handleQueryNextPage(comment.childCommentList)}
            allLoaded={allLoaded}
            bounces
          >
            <ChildItem
              type='main'
              replyAction={this.replyAction}
              itemData={comment.commentList[this.parentIndex]}
            />
            <View style={styles.replyCon}>
              <View style={styles.tabCon}>
                <CommentSort activeTab={activeTab} changeSortAction={handleChangeSort} />
              </View>
              <FlatList
                keyExtractor={item => `${item.id}`}
                data={comment.childCommentList}
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
    // backgroundColor: themeColor.bgF4,
  },
  tabCon: {
    height: scale(31),
    ...themeLayout.flex('row', 'flex-end'),
    ...themeLayout.padding(0, scale(16)),
    ...themeLayout.border(),
  },
  replyCon: {
    backgroundColor: themeColor.bgF4,
  },
});
