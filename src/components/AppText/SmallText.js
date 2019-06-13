import React from 'react';
import { Text, StyleSheet } from 'react-native';
import { themeCatColor, themeCatSize } from '@/config';

const styles = StyleSheet.create({
  textStyle: {
    color: themeCatColor.font.small, // #a7a7a7
    fontSize: themeCatSize.font.small, // 12
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
