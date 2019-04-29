import React from 'react';
import { StyleSheet, TextInput } from 'react-native';
import { themeColor } from '@/config';

export default class CommentPage extends React.Component {
  render() {
    const { style, ...rest } = this.props;
    return (
      <TextInput
        ref={ref => (this.refInput = ref)}
        style={[styles.inputStyle, style]}
        placeholderTextColor={themeColor.placeholderColor}
        {...rest}
      />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  inputStyle: {
    color: themeColor.font.secondary,
  },
});
