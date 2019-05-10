import React from 'react';
import { Keyboard } from 'react-native';
import { COMMENT_SORT } from '@/config/constants';
import { lastArr } from '@/utils/utils';

const commentHoc = Component => {
  return class Hoc extends React.Component {
    static navigationOptions = {
      header: null,
    };

    initialState = {
      atSomeone: null,
      placeholder: '你觉得呢',
      textValue: '',
      allLoaded: false,
    };

    state = { ...this.initialState, activeTab: COMMENT_SORT.hot };

    queryCommentDispatch = (dispatchType, data) => {
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
        },
        finallyFn: () => {
          this.hocref.refScrollView && this.hocref.refScrollView.endLoading();
        },
      });
    };

    handleChangeText = text => {
      this.setState({
        textValue: text,
      });
    };

    handleChangeSort = async item => {
      await this.setState({
        activeTab: item.type,
      });
      this.hocref.queryCommentDispatch({ isFirst: true });
    };

    replyAction = item => {
      console.log('%citem:', 'color: #0e93e0;background: #aaefe5;', item);
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
      const { activeTab } = this.props;
      const isHotSort = +activeTab === 1;
      const lastItem = lastArr(data);
      this.hocref.queryCommentDispatch({
        id: isHotSort ? lastItem.fabulous : lastItem.created_time,
      });
    };

    handleSubmitComment = data => {
      const { atSomeone, textValue } = this.state;
      data.content = textValue;
      if (atSomeone) {
        data.parent_id = atSomeone.id;
      }
      this.submitCommentDispatch(data, () => {
        this.setState(this.initialState);
        Keyboard.dismiss();
        this.hocref.queryCommentDispatch({ isFirst: true });
      });
    };

    submitCommentDispatch = (payload, successFn) => {
      const { dispatch } = this.props;
      dispatch({
        type: 'comment/submitCommentEffect',
        payload,
        successFn: () => {
          Keyboard.dismiss();
          successFn();
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
          handleChangeSort={this.handleChangeSort}
          handleQueryNextPage={this.handleQueryNextPage}
          queryCommentDispatch={this.queryCommentDispatch}
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
