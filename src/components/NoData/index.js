import React from 'react';
import { View, StyleSheet } from 'react-native';
import { themeLayout, themeColor, themeSize, scale } from '@/config';
import SecondaryText from '../AppText/SecondaryText';

const styles = StyleSheet.create({
  container: {
    marginTop: scale(50),
    ...themeLayout.flex(),
  },
  textStyle: {
    color: themeColor.font.small, // #707070
    fontSize: themeSize.font.common, // 13
  },
});

export default class NoData extends React.PureComponent {
  render() {
    const { text } = this.props;
    return (
      <View style={styles.container}>
        <SecondaryText style={styles.textStyle}>{text}</SecondaryText>
      </View>
    );
  }
}
