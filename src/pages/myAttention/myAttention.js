import React from 'react';
import { View, StyleSheet, TouchableOpacity } from 'react-native';
import Header from '@/components/Header';
import CommonText from '@/components/AppText/CommonText';
import MyModal from '@/components/Modal';
import { scale, themeLayout } from '@/config';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    modalVisible: true,
  };

  showModal = () => {
    this.setState({
      modalVisible: true,
    });
  };

  handleHideModal = () => {
    this.setState({
      modalVisible: false,
    });
  };

  render() {
    const { modalVisible } = this.state;
    return (
      <View style={styles.container}>
        <Header title="我的关注" />
        <TouchableOpacity onPress={this.showModal}>
          <CommonText>删除</CommonText>
        </TouchableOpacity>
        <MyModal
          backdropOpacity={0.8}
          animationIn="zoomInDown"
          animationOut="zoomOutUp"
          animationInTiming={600}
          animationOutTiming={600}
          backdropTransitionInTiming={600}
          backdropTransitionOutTiming={600}
          hideModalAction={this.handleHideModal}
          isVisible={modalVisible}
        >
          <View style={styles.modalCon}>
            <View style={styles.modelTip}>
              <CommonText style={styles.modalBtnText('#303030')}>确定删除吗</CommonText>
            </View>
            <View style={styles.modalBtnCon}>
              <TouchableOpacity style={styles.modalBtn(false)} onPress={this.handleHideModal}>
                <CommonText style={styles.modalBtnText('#2f94ea')}>取消</CommonText>
              </TouchableOpacity>
              <TouchableOpacity style={styles.modalBtn(true)} onPress={this.handleConfirmDelete}>
                <CommonText style={styles.modalBtnText('#fb716b')}>确定</CommonText>
              </TouchableOpacity>
            </View>
          </View>
        </MyModal>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  modalCon: {
    width: scale(242),
    height: scale(107),
    ...themeLayout.flex('column'),
    alignSelf: 'center',
    backgroundColor: '#fff',
    borderRadius: scale(10),
  },
  modelTip: {
    height: scale(54),
    ...themeLayout.flex(),
  },
  modalBtnCon: {
    width: '100%',
    ...themeLayout.flex(),
    ...themeLayout.borderSide('Top'),
  },
  modalBtn: showBorder => {
    const style = {
      flex: 1,
      ...themeLayout.flex(),
      height: scale(54),
    };
    return showBorder ? { ...style, ...themeLayout.borderSide('Left') } : style;
  },
  modalBtnText: color => {
    return {
      fontSize: scale(19),
      color,
    };
  },
});
