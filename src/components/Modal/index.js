import React from 'react';
import { StyleSheet, StatusBar } from 'react-native';
import Modal from 'react-native-modal';
import { scale, themeLayout, themeSize } from '@/config';

class MyModal extends React.Component {
  static defaultProps = {
    backdropColor: 'rgba(0,0,0,0.4)',
  };

  onModalWillShow = () => {
    const { backdropColor, onModalWillShow } = this.props;
    StatusBar.setBackgroundColor(backdropColor, true);
    onModalWillShow && onModalWillShow();
  };

  onModalWillHide = () => {
    const { onModalWillHide } = this.props;
    StatusBar.setBackgroundColor('rgba(0,0,0,0)', true);
    onModalWillHide && onModalWillHide();
  };

  render() {
    const { children, backdropColor, modalVisible, hideModalAction, style, ...rest } = this.props;
    return (
      <Modal
        backdropColor={backdropColor}
        onBackdropPress={hideModalAction}
        onBackButtonPress={hideModalAction}
        onModalWillShow={this.onModalWillShow}
        onModalWillHide={this.onModalWillHide}
        isVisible={modalVisible}
        style={styles.bottomModal}
        {...rest}
      >
        {children}
      </Modal>
    );
  }
}

export default MyModal;

const styles = StyleSheet.create({
  bottomModal: {
    justifyContent: 'flex-end',
    margin: 0,
  },
  modalBtn: isCancel => {
    const style = {
      backgroundColor: '#fff',
      width: themeSize.screenWidth,
      height: scale(61),
      ...themeLayout.flex(),
      ...themeLayout.borderSide('Top'),
    };
    return isCancel ? { ...style, marginTop: scale(5) } : style;
  },
});
