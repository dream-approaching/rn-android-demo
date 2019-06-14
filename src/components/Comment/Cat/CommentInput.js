import React from 'react';
import { View, StyleSheet, Image, Keyboard } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, themeCatColor, themeCatSize } from '@/config';
import myImages from '@/utils/myImages';
import CommonText from '@/components/AppText/Cat/CommonText';
import { isLogin, actionBeforeCheckLogin, gotoLogin } from '@/utils/utils';
import { immediateTimer } from '@/config/constants';
import { RnCallBack } from '@/components/NativeModules';
import MyTextInput from '@/components/TextInput';

export default class CommentPage extends React.Component {
  static defaultProps = {
    textValue: '',
    placeholder: '留下你的精彩评论吧',
    handleChangeText: () => {},
  };

  constructor(props) {
    super(props);
    this.state = {
      keyboardShow: false,
      isCollect: props.xshare ? props.xshare.articleDetail.is_favorites : false,
    };
  }

  componentDidMount() {
    this.keyboardDidShowListener = Keyboard.addListener('keyboardDidShow', this.keyboardDidShow);
    this.keyboardDidHideListener = Keyboard.addListener('keyboardDidHide', this.keyboardDidHide);
  }

  componentWillUnmount() {
    this.keyboardDidShowListener.remove();
    this.keyboardDidHideListener.remove();
    // this.timer && clearTimeout(this.timer);
  }

  keyboardDidShow = () => {
    this.setState({
      keyboardShow: true,
    });
  };

  keyboardDidHide = () => {
    this.setState({
      keyboardShow: false,
    });
  };

  onfocus = () => {
    if (!isLogin()) {
      this.refInput.blur();
      gotoLogin();
    }
  };

  handleShare = () => {
    console.log('handleShare');
  };

  handleToggleCollect = () => {
    const { dispatch, xshare, nativePosition, commentNum } = this.props;
    const { isCollect } = this.state;
    this.setState({
      isCollect: !isCollect,
    });
    const callbackData = {
      collection: !isCollect,
      position: +nativePosition,
      commentNum,
    };
    RnCallBack.callBackFirstFragment(JSON.stringify(callbackData));
    if (this.timer) clearTimeout(this.timer);
    this.timer = setTimeout(() => {
      dispatch({
        type: 'xshare/toggleArticleCollectEffect',
        payload: {
          type: xshare.articleDetail.type,
          opt: this.state.isCollect ? 'add' : 'del',
          info_id: xshare.articleDetail.id,
        },
      });
    }, immediateTimer);
  };

  render() {
    const {
      textValue,
      handleChangeText,
      placeholder,
      showLeftIcon,
      showShare,
      handleSubmitComment,
      leftIconAction,
      showCollection,
      onblur = () => {},
    } = this.props;
    const { keyboardShow, isCollect } = this.state;
    const disabled = textValue.length === 0;
    return (
      <View style={styles.inputCon}>
        {showLeftIcon && (
          <TouchableNativeFeedback onPress={leftIconAction}>
            <View style={styles.iconCon(13)}>
              <Image style={styles.leftIcon} source={{ uri: myImages.leftBack }} />
            </View>
          </TouchableNativeFeedback>
        )}
        <MyTextInput
          ref={ref => (this.refInput = ref)}
          conStyle={styles.inputConStyle(showLeftIcon)}
          style={styles.inputStyle}
          onChangeText={handleChangeText}
          onFocus={this.onfocus}
          onBlur={onblur}
          value={textValue}
          // rightIcon={myImages.inputRight}
          placeholder={placeholder}
          placeholderTextColor={themeCatColor.placeholderColor}
        />
        {showCollection && !keyboardShow && (
          <TouchableNativeFeedback
            tapArea={1}
            onPress={() => actionBeforeCheckLogin(this.handleToggleCollect)}
          >
            <View style={styles.iconCon()}>
              {isCollect ? (
                <Image style={styles.rightIcon} source={{ uri: myImages.commentCollectiono }} />
              ) : (
                <Image style={styles.rightIcon} source={{ uri: myImages.commentCollection }} />
              )}
            </View>
          </TouchableNativeFeedback>
        )}
        {showShare && !keyboardShow && (
          <TouchableNativeFeedback tapArea={1} onPress={this.handleShare}>
            <View style={styles.iconCon()}>
              <Image style={styles.rightIcon} source={{ uri: myImages.detailShare }} />
            </View>
          </TouchableNativeFeedback>
        )}
        <TouchableNativeFeedback tapArea={1} onPress={disabled ? () => {} : handleSubmitComment}>
          <View style={styles.submitBtn}>
            <CommonText style={styles.submitText(disabled)}>发送</CommonText>
          </View>
        </TouchableNativeFeedback>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  inputCon: {
    ...themeLayout.flex('row', 'space-between'),
    width: themeCatSize.screenWidth,
    height: 44,
    ...themeLayout.borderSide('Top'),
  },
  iconCon: (hori = 8) => {
    return {
      ...themeLayout.padding(10, hori),
    };
  },
  leftIcon: {
    width: 19,
    height: 19,
  },
  rightIcon: {
    width: 22,
    height: 22,
  },
  inputConStyle: showLeftIcon => {
    return {
      flex: 1,
      marginLeft: showLeftIcon ? 0 : 13,
      ...themeLayout.border(themeCatSize.minBorder, '#bbb'),
      borderRadius: 15,
    };
  },
  inputStyle: {
    ...themeLayout.padding(5, 14),
    color: themeCatColor.font.black,
    height: 30,
    fontSize: themeCatSize.font.larger,
  },
  submitBtn: {
    width: 60,
    justifyContent: 'center',
    alignItems: 'center',
  },
  submitText: disabled => {
    return {
      color: disabled ? '#999' : '#333',
      fontSize: 16,
    };
  },
});
