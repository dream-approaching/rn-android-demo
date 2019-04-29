import React from 'react';
import { View, StyleSheet, Image, TextInput } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { scale, themeLayout, themeColor } from '@/config';
import myImages from '@/utils/myImages';

export default class CommentPage extends React.Component {
  static defaultProps = {
    textValue: '',
    placeholder: '你觉得呢',
    handleChangeText: () => {},
  };

  render() {
    const {
      textValue,
      handleChangeText,
      placeholder,
      showLeftIcon,
      showCollection,
      showShare,
    } = this.props;
    return (
      <View style={styles.inputCon}>
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
          placeholder={placeholder}
          placeholderTextColor={themeColor.placeholderColor}
        />
        {showCollection && (
          <TouchableOpacity>
            <Image style={styles.rightIcon} source={{ uri: myImages.commentCollection }} />
          </TouchableOpacity>
        )}
        {showShare && (
          <TouchableOpacity>
            <Image style={styles.rightIcon} source={{ uri: myImages.share }} />
          </TouchableOpacity>
        )}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  inputCon: {
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.padding(scale(11), scale(13)),
    elevation: 30,
    backgroundColor: '#fff',
    // shadowOffset: scale(20),
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
    height: scale(35),
    flex: 1,
    color: themeColor.font.secondary,
  },
});
