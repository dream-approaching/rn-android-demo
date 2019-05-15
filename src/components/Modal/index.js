import React from 'react';
import { StatusBar } from 'react-native';
import Modal from 'react-native-modal';

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
        {...rest}
      >
        {children}
      </Modal>
    );
  }
}

export default MyModal;
