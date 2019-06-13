import React from 'react';
import { View, StyleSheet } from 'react-native';
import { themeLayout, themeCatColor, themeCatSize } from '@/config';
import SecondaryText from '@/components/AppText/Cat/SecondaryText';

const styles = StyleSheet.create({
  container: {
    marginTop: 50,
    ...themeLayout.flex(),
  },
  textStyle: {
    color: themeCatColor.font.small, // #707070
    fontSize: themeCatSize.font.common, // 13
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
