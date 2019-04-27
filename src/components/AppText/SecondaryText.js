import React from 'react';
import { Text, StyleSheet } from 'react-native';
import { themeColor, themeSize } from '@/config';

const styles = StyleSheet.create({
  textStyle: {
    color: themeColor.font.secondary, // #707070
    fontSize: themeSize.font.secondary, // 13
  },
});

export default class extends React.PureComponent {
  render() {
    const { children, style, ...rest } = this.props;
    return (
      <Text style={[styles.textStyle, style]} {...rest}>
        {children}
      </Text>
    );
  }
}
