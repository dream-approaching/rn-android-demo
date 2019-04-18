import React from 'react';
import { Text, StyleSheet } from 'react-native';
import { themeColor, themeSize } from '@/config';

const styles = StyleSheet.create({
  commonText: {
    // fontFamily: 'Verdana',
    color: themeColor.font.common,
    fontSize: themeSize.font.common
  }
});

export default class extends React.PureComponent {
  render() {
    const { children, style } = this.props;
    return <Text style={[styles.commonText, style]}>{children}</Text>;
  }
}
