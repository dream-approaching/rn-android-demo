import React from 'react';
import { Text, StyleSheet } from 'react-native';
import { themeCatColor, themeCatSize } from '@/config';

const styles = StyleSheet.create({
  textStyle: {
    color: themeCatColor.font.secondary, // #707070
    fontSize: themeCatSize.font.secondary, // 13
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
