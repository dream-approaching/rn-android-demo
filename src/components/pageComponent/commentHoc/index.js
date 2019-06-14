import React from 'react';
import { Keyboard } from 'react-native';
import { COMMENT_SORT, COMMENT_TYPE } from '@/config/constants';
import { lastArr, isLogin } from '@/utils/utils';
import { RnCallBack } from '@/components/NativeModules';

const commentHoc = Component => {
  return class Hoc extends React.Component {
    static navigationOptions = {
      header: null,
    };

    initialState = {
      atSomeone: null,
      placeholder: '留下你的精彩评论吧',
      textValue: '',
      allLoaded: false,
    };

    state = { ...this.initialState, activeTab: COMMENT_SORT.new };

    queryCommentDispatch = (dispatchType, data, successFn) => {
      const { dispatch } = this.props;
      const payload = {
        ...data,
        pagesize: 10,
        sort: this.state.activeTab,
      };
      dispatch({
        type: dispatchType,
        payload,
        successFn: response => {
          if (response.length === 0) {
            this.setState({
              allLoaded: true,
            });
          }
          successFn && successFn();
        },
        finallyFn: () => {
          this.hocref.refScrollView && this.hocref.refScrollView.endLoading();
        },
      });
    };

    onblur = () => {
      this.setState(this.initialState);
    };

    handleChangeText = text => {
      this.setState({
        textValue: text,
      });
    };

    replyAction = item => {
      console.log('%citem:', 'color: #0e93e0;background: #aaefe5;', item);
      if (!isLogin()) return false;
      this.hocref.refInputCon.refInput.focus();
      this.setState({
        atSomeone: {
          name: `回复${item.commit_user}：`,
          id: item.id,
        },
      });
      this.setState({
        placeholder: `回复${item.commit_user}：`,
      });
    };

    handleQueryNextPage = data => {
      const { activeTab } = this.state;
      const isHotSort = +activeTab === 1;
      const lastItem = lastArr(data);
      const queryId = isHotSort ? lastItem.fabulous : lastItem.created_time;
      if (+queryId === 0) {
        this.setState({
          allLoaded: true,
        });
        return this.hocref.refScrollView && this.hocref.refScrollView.endLoading();
      }
      this.hocref.queryCommentDispatch({ id: queryId });
    };

    handleSubmitComment = (data, successFn) => {
      const { atSomeone, textValue } = this.state;
      data.content = textValue;
      if (atSomeone) {
        data.parent_id = atSomeone.id;
      }
      this.submitCommentDispatch(data, response => {
        this.setState(this.initialState);
        if (successFn) {
          successFn();
        } else {
          this.hocref.queryCommentDispatch({ isFirst: true });
        }
        // 互动话题tab评论时回调参与人数给本地
        if (data.type === COMMENT_TYPE.chat && +data.position === 1) {
          const callbackData = {
            joinNum: response.data.join_people,
            position: +data.position,
          };
          RnCallBack.callTopicFg(JSON.stringify(callbackData));
        }
        // 探索tab评论时回调
        if (data.position === '0') {
          const callbackData = {
            commentNum: atSomeone ? data.commentNum : +data.commentNum + 1,
            position: +data.position,
            collection: data.collection,
          };
          RnCallBack.callBackFirstFragment(JSON.stringify(callbackData));
        }
      });
    };

    submitCommentDispatch = (payload, successFn) => {
      const { dispatch } = this.props;
      dispatch({
        type: 'catComment/submitCommentEffect',
        payload,
        successFn: response => {
          Keyboard.dismiss();
          successFn(response);
        },
      });
    };

    render() {
      return (
        <Component
          ref={ref => (this.hocref = ref)}
          replyAction={this.replyAction}
          handleSubmitComment={this.handleSubmitComment}
          handleChangeText={this.handleChangeText}
          handleQueryNextPage={this.handleQueryNextPage}
          queryCommentDispatch={this.queryCommentDispatch}
          onblur={this.onblur}
          {...this.state}
          {...this.props}
        />
      );
    }
  };

  // 约定：包装显示名字以便于调试
  // Hoc.displayName = `WithSubscription(${Component.displayName || Component.name || 'Component'})`;

  // 设置ref
  // const forwardRef = (props, ref) => {
  //   return <Hoc forwardedRef={ref} {...props} />;
  // };

  // return React.forwardRef(forwardRef);
};

export default commentHoc;
