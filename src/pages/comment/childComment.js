import React from 'react';
import { View, StyleSheet, StatusBar, Keyboard } from 'react-native';
import CommentInput from '@/components/Comment/CommentInput';
import { FlatList } from 'react-native-gesture-handler';
// import { commentData } from '@/config/fakeData';
import { connect } from '@/utils/dva';
import { themeColor, scale, themeLayout } from '@/config';
import SpringScrollView from '@/components/SpringScrollView';
import CommentSort from '@/components/Comment/CommentSort';
import ChildItem from './components/childItem';
import { ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import Header from '@/components/Header';
import { lastArr } from '@/utils/utils';

class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  constructor(props) {
    super(props);
    this.initialState = {
      allLoaded: false,
      textValue: '',
      atSomeone: null,
    };
    this.state = {
      ...this.initialState,
      activeTab: '2',
    };
    const { navigation } = props;
    this.parentId = navigation.getParam('id');
    this.parentIndex = navigation.getParam('index');
    this.total = navigation.getParam('total');
  }

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
    // 获取评论列表
    this.queryChildCommentDispatch(
      {
        type: 1,
        content_id: 8,
        sort: 2,
        isFirst: true,
      },
      {
        successFn: this.queryListSuccessFn,
      }
    );
  }

  queryListSuccessFn = () => {
    this.checkAllLoaded();
    this.refScrollView.endLoading();
  };

  queryChildCommentDispatch = (payload, { successFn }) => {
    console.log('%csuccessFn:', 'color: #0e93e0;background: #aaefe5;', successFn);
    const { dispatch } = this.props;
    dispatch({
      type: 'comment/queryChildCommentEffect',
      payload: { pagesize: 10, parent_id: this.parentId, ...payload },
      successFn,
    });
  };

  submitCommentDispatch = (payload, { successFn }) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'comment/submitCommentEffect',
      payload,
      successFn,
    });
  };

  checkAllLoaded = () => {
    console.log('%ccheckAllLoaded:', 'color: #0e93e0;background: #aaefe5;', 'checkAllLoaded');
    const { comment } = this.props;
    this.setState({
      allLoaded: +this.total <= comment.childCommentList.length,
    });
  };

  handleQueryNextPage = () => {
    const { comment } = this.props;
    if (!comment.commentList.length) return null;
    const { activeTab } = this.state;
    const isHotSort = +activeTab === 1;
    const lastItem = lastArr(comment.commentList);
    this.queryChildCommentDispatch(
      {
        type: 1,
        content_id: 8,
        sort: 2,
        id: isHotSort ? lastItem.fabulous : lastItem.created_time,
      },
      {
        successFn: this.queryListSuccessFn,
      }
    );
  };

  handleChangeText = text => {
    this.setState({
      textValue: text,
    });
  };

  replyAction = item => {
    console.log('%citem:', 'color: #0e93e0;background: #aaefe5;', item);
    this.refInputCon.refInput.focus();
    this.setState({
      atSomeone: {
        name: `回复${item.commit_user}：`,
        id: item.id,
      },
    });
    this.handleChangeText(`回复${item.commit_user}：`);
  };

  renderCommentItem = ({ item }) => {
    return <ChildItem type='child' replyAction={this.replyAction} itemData={item} />;
  };

  handleChangeSort = item => {
    this.setState({
      activeTab: item.type,
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
    console.log('%cactiveTab:', 'color: #0e93e0;background: #aaefe5;', activeTab);
    const { comment } = this.props;
    console.log('comment.childCommentList', comment.childCommentList);
    return (
      <View style={styles.container}>
        <Header title={`${this.total}条评论`} />
        <SpringScrollView
          ref={ref => (this.refScrollView = ref)}
          loadingFooter={ChineseNormalFooter}
          onLoading={this.handleQueryNextPage}
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
              <CommentSort activeTab={activeTab} changeSortAction={this.handleChangeSort} />
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
          handleChangeText={this.handleChangeText}
          handleSubmitComment={this.handleSubmitComment}
          textValue={textValue}
        />
      </View>
    );
  }
}

const mapStateToProps = ({ comment, loading }) => ({
  comment,
  loading: loading.effects['comment/submitCommentEffect'],
});

export default connect(mapStateToProps)(CommentPage);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: themeColor.bgF4,
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
