import React from 'react';
import { ImageBackground, StyleSheet, Image, TextInput, Keyboard } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { scale, themeLayout, themeColor } from '@/config';
import myImages from '@/utils/myImages';
import CommonText from '../AppText/CommonText';
import { isLogin, actionBeforeCheckLogin, gotoLogin } from '@/utils/utils';

export default class CommentPage extends React.Component {
  static defaultProps = {
    textValue: '',
    placeholder: '你觉得呢',
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
    const { dispatch, xshare } = this.props;
    const { isCollect } = this.state;
    dispatch({
      type: 'xshare/toggleArticleCollectEffect',
      payload: {
        type: xshare.articleDetail.type,
        opt: !isCollect ? 'add' : 'del',
        info_id: xshare.articleDetail.id,
      },
      successFn: () => {
        this.setState({
          isCollect: !isCollect,
        });
      },
    });
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
    } = this.props;
    const { keyboardShow, isCollect } = this.state;
    const disabled = textValue.length === 0;
    return (
      <ImageBackground
        resizeMode='stretch'
        source={{ uri: myImages.bgInput }}
        style={styles.inputCon}
      >
        {showLeftIcon && (
          <TouchableNativeFeedback onPress={leftIconAction}>
            <Image style={styles.leftIcon} source={{ uri: myImages.leftBack }} />
          </TouchableNativeFeedback>
        )}
        <TextInput
          ref={ref => (this.refInput = ref)}
          style={styles.inputStyle}
          onChangeText={handleChangeText}
          onFocus={this.onfocus}
          value={textValue}
          multiline
          placeholder={placeholder}
          placeholderTextColor={themeColor.placeholderColor}
        />
        {showCollection && !keyboardShow && (
          <TouchableNativeFeedback onPress={() => actionBeforeCheckLogin(this.handleToggleCollect)}>
            {isCollect ? (
              <Image style={styles.rightIcon} source={{ uri: myImages.commentCollectiono }} />
            ) : (
              <Image style={styles.rightIcon} source={{ uri: myImages.commentCollection }} />
            )}
          </TouchableNativeFeedback>
        )}
        {showShare && !keyboardShow && (
          <TouchableNativeFeedback onPress={this.handleShare}>
            <Image style={styles.rightIcon} source={{ uri: myImages.share }} />
          </TouchableNativeFeedback>
        )}
        {keyboardShow && (
          <TouchableNativeFeedback
            style={styles.submitBtn}
            onPress={disabled ? () => {} : handleSubmitComment}
          >
            <CommonText style={styles.submitText(disabled)}>发送</CommonText>
          </TouchableNativeFeedback>
        )}
      </ImageBackground>
    );
  }
}

const styles = StyleSheet.create({
  inputCon: {
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.padding(0, scale(13)),
    width: scale(360),
    height: scale(65),
  },
  leftIcon: {
    width: scale(19),
    height: scale(19),
    marginRight: scale(10),
  },
  rightIcon: {
    width: scale(22),
    height: scale(22),
    ...themeLayout.margin(0, scale(8)),
  },
  inputStyle: {
    ...themeLayout.border(),
    ...themeLayout.padding(scale(5), scale(10)),
    flex: 1,
    color: themeColor.font.secondary,
    borderRadius: scale(5),
  },
  submitBtn: {
    width: scale(60),
    justifyContent: 'center',
    alignItems: 'center',
  },
  submitText: disabled => {
    return {
      color: disabled ? '#999' : '#333',
    };
  },
});
