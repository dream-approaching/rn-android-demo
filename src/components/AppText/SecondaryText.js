import React from 'react';
import { Text, StyleSheet } from 'react-native';
import { themeColor, themeSize } from '@/config';

const styles = StyleSheet.create({
  textStyle: {
    color: themeColor.font.secondary,
    fontSize: themeSize.font.secondary
  }
});

export default class extends React.PureComponent {
  render() {
    const { children, style } = this.props;
    return <Text style={[styles.textStyle, style]}>{children}</Text>;
  }
}
