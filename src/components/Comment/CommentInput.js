import React from 'react';
import { ImageBackground, StyleSheet, Image, TextInput, Keyboard } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { scale, themeLayout, themeColor } from '@/config';
import myImages from '@/utils/myImages';
import CommonText from '../AppText/CommonText';

export default class CommentPage extends React.Component {
  static defaultProps = {
    textValue: '',
    placeholder: '你觉得呢',
    handleChangeText: () => {},
  };

  state = {
    keyboardShow: false,
  };

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

  render() {
    const {
      textValue,
      handleChangeText,
      placeholder,
      showLeftIcon,
      showCollection,
      showShare,
      handleSubmitComment,
    } = this.props;
    const { keyboardShow } = this.state;
    const disabled = textValue.length === 0;
    return (
      <ImageBackground
        resizeMode='stretch'
        source={{ uri: myImages.bgInput }}
        style={styles.inputCon}
      >
        {showLeftIcon && (
          <TouchableOpacity>
            <Image style={styles.leftIcon} source={{ uri: myImages.leftBack }} />
          </TouchableOpacity>
        )}
        <TextInput
          ref={ref => (this.refInput = ref)}
          style={styles.inputStyle}
          onChangeText={handleChangeText}
          value={textValue}
          multiline
          placeholder={placeholder}
          placeholderTextColor={themeColor.placeholderColor}
        />
        {showCollection && !keyboardShow && (
          <TouchableOpacity>
            <Image style={styles.rightIcon} source={{ uri: myImages.commentCollection }} />
          </TouchableOpacity>
        )}
        {showShare && !keyboardShow && (
          <TouchableOpacity>
            <Image style={styles.rightIcon} source={{ uri: myImages.share }} />
          </TouchableOpacity>
        )}
        {keyboardShow && (
          <TouchableOpacity
            style={styles.submitBtn}
            activeOpacity={disabled ? 1 : 0.2}
            onPress={disabled ? () => {} : handleSubmitComment}
          >
            <CommonText style={styles.submitText(disabled)}>发送</CommonText>
          </TouchableOpacity>
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
