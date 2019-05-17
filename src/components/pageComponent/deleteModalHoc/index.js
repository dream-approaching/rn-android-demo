import React, { Fragment } from 'react';
import {
  View,
  StyleSheet,
  ActivityIndicator,
  // InteractionManager,
} from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, scale } from '@/config';
import CommonText from '@/components/AppText/CommonText';
import MyModal from '@/components/Modal';

const deleteModalHoc = Component => {
  const styles = StyleSheet.create({
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
  return class Hoc extends React.Component {
    static navigationOptions = {
      header: null,
    };

    state = { modalVisible: false, selectedItem: {} };

    handleShowDeleteModal = itemData => {
      this.setState({
        modalVisible: true,
        selectedItem: itemData,
      });
    };

    handleHideDeleteModal = async () => {
      await this.setState({
        modalVisible: false,
      });
    };

    handleConfirmDelete = () => {
      const { dispatch } = this.props;
      const { selectedItem } = this.state;
      dispatch({
        type: 'xshare/deleteXshareEffect',
        payload: { id: selectedItem.id },
        successFn: () => {
          // InteractionManager.runAfterInteractions(() => { });
          this.handleHideDeleteModal();
          this.hocref.handleDeleteCallBack(selectedItem);
        },
      });
    };

    render() {
      const { modalVisible } = this.state;
      const { deleteLoading } = this.props;
      return (
        <Fragment>
          <Component
            ref={ref => (this.hocref = ref)}
            handleShowDeleteModal={this.handleShowDeleteModal}
            handleConfirmDelete={this.handleConfirmDelete}
            {...this.state}
            {...this.props}
          />
          <MyModal hideModalAction={this.handleHideDeleteModal} isVisible={modalVisible}>
            <View style={styles.modalCon}>
              <View style={styles.modelTip}>
                <CommonText style={styles.modalBtnText('#303030')}>确定删除吗</CommonText>
              </View>
              <View style={styles.modalBtnCon}>
                <TouchableNativeFeedback onPress={this.handleHideDeleteModal}>
                  <View style={styles.modalBtn(false)}>
                    <CommonText style={styles.modalBtnText('#2f94ea')}>取消</CommonText>
                  </View>
                </TouchableNativeFeedback>
                <TouchableNativeFeedback onPress={this.handleConfirmDelete}>
                  <View style={styles.modalBtn(true)}>
                    {(deleteLoading && <ActivityIndicator color='#fb716b' />) || (
                      <CommonText style={styles.modalBtnText('#fb716b')}>确定</CommonText>
                    )}
                  </View>
                </TouchableNativeFeedback>
              </View>
            </View>
          </MyModal>
        </Fragment>
      );
    }
  };
};

export default deleteModalHoc;
