import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { scale, themeLayout, themeColor } from '@/config';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import myImages from '@/utils/myImages';
import CommonText from '@/components/AppText/CommonText';
import SecondaryText from '@/components/AppText/SecondaryText';
import ActivityIndicator from '../ActivityIndicator';

export default class extends React.PureComponent {
  render() {
    const {
      title,
      type,
      rightAction = () => {},
      color = themeColor.font.secondary,
      showLoading,
    } = this.props;
    return (
      <View style={styles.container}>
        <View style={styles.titleCon}>
          <View style={styles.leftBlock} />
          <CommonText style={[styles.titleText, { color }]}>{title}</CommonText>
          {showLoading && <ActivityIndicator size={12} />}
        </View>
        {type === 'more' && (
          <TouchableNativeFeedback onPress={rightAction}>
            <View style={styles.moreCon}>
              <SecondaryText>查看更多</SecondaryText>
              <Image style={styles.rightIcon} source={{ uri: myImages.next }} />
            </View>
          </TouchableNativeFeedback>
        )}
        {type === 'del' && (
          <TouchableNativeFeedback onPress={rightAction}>
            <Image style={styles.clearIcon} source={{ uri: myImages.btnClear }} />
          </TouchableNativeFeedback>
        )}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    ...themeLayout.flex('row', 'space-between'),
  },
  titleCon: {
    ...themeLayout.flex('row'),
  },
  leftBlock: {
    backgroundColor: themeColor.primaryColor,
    width: scale(3),
    height: scale(15),
    marginRight: scale(9),
  },
  moreCon: {
    ...themeLayout.flex('row'),
  },
  rightIcon: {
    width: scale(13),
    height: scale(13),
    marginLeft: scale(4),
  },
  clearIcon: {
    width: scale(22),
    height: scale(22),
  },
  titleText: {
    marginRight: scale(5),
  },
});
