import React from 'react';
import { View, StyleSheet, StatusBar, Button } from 'react-native';
import ScrollableTabView from 'react-native-scrollable-tab-view';
import CommonText from '@/components/AppText/CommonText';
import { FlatList, TouchableOpacity } from 'react-native-gesture-handler';
import SpringScrollView from '@/components/SpringScrollView';
import { ChineseNormalHeader } from 'react-native-spring-scrollview/Customize';
import Header from '@/components/Header';
import { notice } from '@/config/fakeData';
import Modal from 'react-native-modal';
import CommentLikeItem from './components/commentLikeItem';
import TabBar from './components/TabBar';
import { themeSize, scale, themeLayout } from '@/config';
// import Modal from '@/components/Modal';

class MyNotice extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = { test: 1, modalVisible: false };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
  }

  handleChangeTab = avtiveTab => {
    console.log('%cargs:', 'color: #0e93e0;background: #aaefe5;', avtiveTab);
  };

  handleRefresh = () => {
    setTimeout(() => {
      this.setState({ test: this.state.test + 1 }, () => {
        this.refScrollView.endRefresh();
      });
    }, 2000);
  };

  handleShowModal = () => {
    console.log('%chandleShowModal:', 'color: #0e93e0;background: #aaefe5;', 'handleShowModal');

    this.setState({
      modalVisible: true,
    });
    StatusBar.setHidden(true);
  };

  renderCommentItem = ({ item }) => {
    return <CommentLikeItem showModalAction={this.handleShowModal} itemData={item} />;
  };

  toggleModal = () => {
    this.setState({ modalVisible: !this.state.modalVisible });
    StatusBar.setHidden(false);
  };

  render() {
    const { modalVisible } = this.state;
    console.log('%cmodalVisible:', 'color: #0e93e0;background: #aaefe5;', modalVisible);
    return (
      <View style={styles.container}>
        <Header title="我的通知" />
        <Modal
          backdropColor="rgba(0,0,0,0.2)"
          onBackdropPress={this.toggleModal}
          onBackButtonPress={this.toggleModal}
          isVisible={modalVisible}
          style={styles.bottomModal}
        >
          <View>
            <TouchableOpacity style={styles.modalBtn(false)} onPress={this.toggleModal}>
              <CommonText>回复</CommonText>
            </TouchableOpacity>
            <TouchableOpacity style={styles.modalBtn(false)} onPress={this.toggleModal}>
              <CommonText>详情</CommonText>
            </TouchableOpacity>
          </View>
          <TouchableOpacity style={styles.modalBtn(true)} onPress={this.toggleModal}>
            <CommonText>取消</CommonText>
          </TouchableOpacity>
        </Modal>
        <ScrollableTabView onChangeTab={this.handleChangeTab} renderTabBar={() => <TabBar />}>
          <SpringScrollView
            tabLabel="评论"
            ref={ref => (this.refScrollView = ref)}
            refreshHeader={ChineseNormalHeader}
            onRefresh={this.handleRefresh}
            bounces
          >
            <FlatList
              keyExtractor={item => `${item.id}`}
              // data={commentData}
              data={notice.commentList}
              renderItem={this.renderCommentItem}
            />
          </SpringScrollView>
          <CommonText tabLabel="点赞">点赞</CommonText>
          <CommonText showDot tabLabel="系统通知">
            project
          </CommonText>
        </ScrollableTabView>
      </View>
    );
  }
}

export default MyNotice;

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  bottomModal: {
    justifyContent: 'flex-end',
    margin: 0,
  },
  modalBtn: isCancel => {
    const style = {
      backgroundColor: '#fff',
      height: scale(61),
      ...themeLayout.flex(),
      ...themeLayout.borderSide('Top'),
    };
    return isCancel ? { ...style, marginTop: scale(5) } : style;
  },
});
