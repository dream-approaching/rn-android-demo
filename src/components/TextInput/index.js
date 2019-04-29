import React from 'react';
import { StyleSheet, TextInput, View, Image } from 'react-native';
import { themeLayout, themeColor, scale } from '@/config';

export default class CommentPage extends React.Component {
  focus = () => this.refInput.focus();

  render() {
    const { style, leftIcon, rightIcon, conStyle, ...rest } = this.props;
    return (
      <View style={[styles.inputCon, conStyle]}>
        {leftIcon && <Image style={styles.leftIcon} source={{ uri: leftIcon }} />}
        <TextInput
          ref={ref => (this.refInput = ref)}
          style={[styles.inputStyle, style]}
          placeholderTextColor={themeColor.placeholderColor}
          {...rest}
        />
        {rightIcon && <Image style={styles.rightIcon} source={{ uri: rightIcon }} />}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  inputCon: {
    ...themeLayout.flex('row', 'flex-start'),
    backgroundColor: '#fff',
    paddingRight: scale(20),
  },
  leftIcon: {
    width: scale(24),
    height: scale(24),
    marginLeft: scale(8),
    marginRight: scale(4),
  },
  rightIcon: {
    width: scale(12),
    height: scale(12),
  },
  inputStyle: {
    color: themeColor.font.secondary,
    flex: 1,
  },
});
