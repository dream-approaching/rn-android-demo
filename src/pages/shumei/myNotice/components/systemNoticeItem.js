import React from 'react';
import { View, StyleSheet } from 'react-native';
import { scale, themeLayout } from '@/config';
import SmallText from '@/components/AppText/SmallText';
import CommonText from '@/components/AppText/CommonText';

export default class extends React.PureComponent {
  render() {
    const { itemData } = this.props;
    return (
      <View style={styles.container}>
        <CommonText>{itemData.nick_name}</CommonText>
        <SmallText>{itemData.timestr}</SmallText>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    ...themeLayout.padding(scale(16), scale(16), scale(11)),
    ...themeLayout.flex('row', 'center', 'flex-start'),
    ...themeLayout.borderSide(),
  },
});
