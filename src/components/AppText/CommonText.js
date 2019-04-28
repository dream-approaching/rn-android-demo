import React from 'react';
import { Text, StyleSheet } from 'react-native';
import { themeColor, themeSize } from '@/config';

const styles = StyleSheet.create({
  textStyle: {
    // fontFamily: 'Verdana',
    color: themeColor.font.black, // #303030
    fontSize: themeSize.font.common, // 15
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
