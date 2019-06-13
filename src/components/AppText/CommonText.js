import React from 'react';
import { Text, StyleSheet } from 'react-native';
import { themeCatColor, themeCatSize } from '@/config';

const styles = StyleSheet.create({
  textStyle: {
    // fontFamily: 'Verdana',
    color: themeCatColor.font.black, // #303030
    fontSize: themeCatSize.font.common, // 15
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
