import React from 'react';
import { View, StyleSheet, Image, BackHandler, TextInput, Keyboard } from 'react-native';
import Header from '@/components/Header';
import { DragToSortTags } from '@/components/Drag/index.js';
import { themeLayout, themeCatColor, scale, themeCatSize } from '@/config';
import myImages from '@/utils/myImages';
import CommonText from '@/components/AppText/Cat/CommonText';
import SmallText from '@/components/AppText/Cat/SmallText';
import MyModal from '@/components/Modal';
import RNFetchBlob from 'react-native-fetch-blob';
import { connect } from '@/utils/dva';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { addLocationListener, start, stop } from 'react-native-amap-geolocation';
import { lastArr } from '@/utils/utils';
import { uploadImg, uploadVideo } from '@/utils/uploadImg';

class CatPublish extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    tags: [],
    textValue: '',
    // showDeleteBox: false,
    // wantDelete: false,
    modalVisible: false,
  };

  componentDidMount() {
    const { dispatch } = this.props;
    this.type = 'video';
    // 获取当前位置
    this.locationListener = addLocationListener(location => {
      console.log('%clocation:', 'color: #0e93e0;background: #aaefe5;', location);
      if (location) {
        stop();
        dispatch({ type: 'catPublish/saveSelectAddress', payload: location.poiName });
        this.searchAddress(location.poiName);
      }
    });
    setTimeout(() => start(), 20);
    if (this.type !== 'video') {
      this.setState({
        tags: [
          { id: 1, src: 'file:///storage/emulated/0/MagazineUnlock/hua.jpg' },
          { id: 2, src: 'file:///storage/emulated/0/MagazineUnlock/test.jpg' },
        ],
      });
    } else {
      this.setState({
        tags: [{ id: 1, src: 'file:///storage/emulated/0/DCIM/Camera/video.mp4' }],
      });
    }

    this.backHandler = BackHandler.addEventListener('hardwareBackPress', this.handleHardBackPress);
  }

  componentWillUnmount() {
    this.backHandler.remove();
    this.locationListener.remove();
  }

  searchAddress = async keywords => {
    const { dispatch } = this.props;
    dispatch({
      type: 'catPublish/queryDefaultAddressListEffect',
      payload: { keywords },
    });
  };

  handleHardBackPress = () => {
    const { navigation } = this.props;
    const { textValue } = this.state;
    const sortTag = this.dragRef.getSortedTags();
    if (navigation.isFocused() && (sortTag.length > 0 || textValue.length > 0)) {
      this.setState({ modalVisible: true });
      return true;
    }
    return false;
  };

  handleBackPress = () => {
    if (!this.handleHardBackPress()) {
      this.handleQuitActivity();
    }
  };

  handleQuitActivity = () => BackHandler.exitApp();

  handleHideDeleteModal = async () => {
    await this.setState({
      modalVisible: false,
    });
  };

  handleGiveUpBtn = async () => {
    this.handleQuitActivity();
    await this.setState({
      modalVisible: false,
    });
  };

  handleSaveBtn = async () => {
    this.handleQuitActivity();
    await this.setState({
      modalVisible: false,
    });
  };

  // setDeleteBox = bool => this.setState({ showDeleteBox: bool });

  // setWantDelete = bool => this.setState({ wantDelete: bool });

  gotoChooseLocation = () => {
    const { navigation } = this.props;
    console.log('%cnavigation:', 'color: #0e93e0;background: #aaefe5;', navigation);
    // BackHandler.removeEventListener('hardwareBackPress', this.handleHardBackPress);
    navigation.navigate('ChooseLocation');
  };

  onTagsSorted = (...args) => {
    console.log('onTagsSorted', args);
  };

  onSelectChange = (...args) => {
    console.log('onSelectChange', args);
  };

  handleDelImg = item => {
    this.dragRef.handleDelImg(item);
  };

  handleChangeText = value => {
    this.setState({
      textValue: value,
    });
  };

  handlePublish = async () => {
    const { textValue } = this.state;
    const { dispatch, catPublish } = this.props;
    if (!textValue.length) return null;
    Keyboard.dismiss();
    dispatch({
      type: 'global/showGlobalLoadingEffect',
      payload: { status: true, time: 2000 },
      successFn: () => {
        console.log('%csuccessFn:', 'color: #0e93e0;background: #aaefe5;', 'successFn');
        this.handleQuitActivity();
      },
    });
    if (this.type !== 'video') {
      const imgArr = [];
      const sortTag = this.dragRef.getSortedTags();
      await sortTag.map(async item => {
        const { size, format } = await RNFetchBlob.fs.stat(item.src).then(res => {
          return { size: res.size, format: lastArr(res.filename.split('.')) };
        });
        const url = await RNFetchBlob.fs
          .readFile(item.src, 'base64')
          .then(async base64 => {
            const response = await uploadImg({ format, size, base64 });
            console.log('%cresponse:', 'color: #0e93e0;background: #aaefe5;', response);
            return response.data.imgurl;
          })
          .catch(err => {
            console.log(`reading error: ${err}`);
          });
        imgArr.push(url);
        if (imgArr.length === sortTag.length) {
          console.log('上传完成，正在提交');
          dispatch({
            type: 'catPublish/submitPublishEffect',
            payload: {
              content: textValue,
              img: imgArr.join(','),
              location: catPublish.selectedAddress,
            },
            successFn: () => {
              console.log('成功');
            },
          });
        }
      });
    } else {
      // is video
      const { tags } = this.state;
      const video = tags[0];
      const { format } = await RNFetchBlob.fs
        .stat(video.src)
        .then(res => ({ format: lastArr(res.filename.split('.')) }));
      try {
        const res = await uploadVideo({
          path: video.src,
          title: textValue,
          location: catPublish.selectedAddress,
          videotype: format,
        });
        console.log('%cres:', 'color: #0e93e0;background: #aaefe5;', res);
      } catch (err) {
        console.log('%cerr:', 'color: #0e93e0;background: #aaefe5;', err);
      }
    }
  };

  renderHeaderRight = () => {
    return (
      <TouchableNativeFeedback onPress={this.handlePublish} notOut>
        <View style={styles.headerRightCon}>
          <SmallText style={styles.headerRightText}>发布</SmallText>
        </View>
      </TouchableNativeFeedback>
    );
  };

  renderTag = item => {
    return (
      <View>
        <View style={styles.imgItem}>
          <Image resizeMode='cover' style={styles.avatar} source={{ uri: item.src }} />
          <TouchableNativeFeedback onPress={() => this.handleDelImg(item)}>
            <View style={styles.imgDelCon}>
              <Image style={styles.imgDel} source={{ uri: myImages.delBack }} />
            </View>
          </TouchableNativeFeedback>
        </View>
      </View>
    );
  };

  render() {
    const { tags, modalVisible, textValue } = this.state;
    const { catPublish } = this.props;
    return (
      <View style={styles.container}>
        <Header
          backAction={this.handleBackPress}
          showBorder={false}
          backType='del'
          rightComponent={this.renderHeaderRight()}
        />
        <View style={styles.inputCon}>
          <TextInput
            ref={ref => (this.refInput = ref)}
            style={styles.inputStyle}
            onChangeText={this.handleChangeText}
            value={textValue}
            multiline
            placeholder='说说此时想说的...'
            placeholderTextColor={themeCatColor.placeholderColor}
          />
        </View>
        <View style={styles.imgCon}>
          <DragToSortTags
            tags={tags}
            ref={ref => (this.dragRef = ref)}
            onSelectChange={this.onSelectChange}
            onTagsSorted={this.onTagsSorted}
            renderTag={this.renderTag}
            vibration={100}
            selectable={false}
            marginHorizontal={scale(5)}
            marginVertical={scale(5)}
            type={this.type}
            // wantDelete={wantDelete}
            // setDeleteBox={this.setDeleteBox}
            // setWantDelete={this.setWantDelete}
          />
        </View>
        <TouchableNativeFeedback notOut onPress={this.gotoChooseLocation}>
          <View style={styles.locationCon}>
            <View style={styles.leftCon}>
              <Image style={styles.locationLeft} source={{ uri: myImages.delBack }} />
              <CommonText style={styles.locationText}>
                {catPublish.selectedAddress || '添加位置'}
              </CommonText>
            </View>
            <Image style={styles.locationRight} source={{ uri: myImages.delBack }} />
          </View>
        </TouchableNativeFeedback>
        {/* {showDeleteBox && (
          <View style={styles.deleteBox}>
            <CommonText>{wantDelete ? '松手删除' : '拖到此处松手可删除'}</CommonText>
          </View>
        )} */}
        <MyModal
          style={{ top: -50 }}
          hideModalAction={this.handleHideDeleteModal}
          isVisible={modalVisible}
        >
          <View style={styles.modalCon}>
            <View style={styles.modelTip}>
              <CommonText style={styles.modalTitle}>保留本次编辑?</CommonText>
            </View>
            <View style={styles.modalBtnCon}>
              <TouchableNativeFeedback onPress={this.handleGiveUpBtn}>
                <View style={styles.modalBtn(false)}>
                  <CommonText style={styles.modalBtnText('#222')}>不保留</CommonText>
                </View>
              </TouchableNativeFeedback>
              <TouchableNativeFeedback onPress={this.handleSaveBtn}>
                <View style={styles.modalBtn(true)}>
                  <CommonText style={styles.modalBtnText('#F84D3B')}>保留</CommonText>
                </View>
              </TouchableNativeFeedback>
            </View>
          </View>
        </MyModal>
      </View>
    );
  }
}

const mapStateToProps = ({ catPublish, global }) => ({
  catPublish,
  global,
});

export default connect(mapStateToProps)(CatPublish);

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  imgCon: {
    ...themeLayout.padding(0, scale(11)),
  },
  inputCon: {
    ...themeLayout.margin(0, scale(16)),
    ...themeLayout.borderSide('Bottom'),
  },
  locationCon: {
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.margin(0, scale(16)),
    ...themeLayout.borderSide('Top'),
    ...themeLayout.borderSide('Bottom'),
    height: 55,
  },
  leftCon: {
    ...themeLayout.flex('row'),
  },
  locationLeft: {
    width: scale(15),
    height: scale(15),
    marginRight: scale(10),
  },
  locationRight: {
    width: scale(5),
    height: scale(10),
  },
  locationText: {
    color: '#777',
    fontSize: themeCatSize.font.larger,
  },
  imgItem: {
    backgroundColor: 'lightblue',
    borderRadius: scale(8),
    overflow: 'hidden',
  },
  avatar: {
    width: scale(106),
    height: scale(106),
  },
  deleteBox: {
    position: 'absolute',
    bottom: 0,
    width: '100%',
    height: 100,
    backgroundColor: 'rgba(220,0,0,0.3)',
  },
  modalCon: {
    width: 242,
    height: 107,
    ...themeLayout.flex('column'),
    alignSelf: 'center',
    backgroundColor: '#fff',
    borderRadius: 10,
  },
  modelTip: {
    height: 54,
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
      height: 54,
    };
    return showBorder ? { ...style, ...themeLayout.borderSide('Left') } : style;
  },
  modalTitle: {
    color: '#222',
    fontSize: 16,
  },
  modalBtnText: color => {
    return {
      fontSize: 17,
      color,
    };
  },
  headerRightCon: {
    width: 53,
    height: 24,
    borderRadius: 12,
    backgroundColor: themeCatColor.primaryColor,
    ...themeLayout.flex('row'),
  },
  headerRightText: {
    color: '#fff',
    fontSize: 12,
  },
  imgDelCon: {
    position: 'absolute',
    top: 0,
    right: 0,
  },
  imgDel: {
    width: scale(15),
    height: scale(15),
  },
  inputStyle: {
    color: themeCatColor.font.black,
    fontSize: themeCatSize.font.larger,
    height: 150,
    lineHeight: scale(22),
    textAlignVertical: 'top',
  },
});
