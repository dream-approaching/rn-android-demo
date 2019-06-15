import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { themeLayout, themeCatColor } from '@/config';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import myImages from '@/utils/myImages';
import CommonText from '@/components/AppText/Cat/CommonText';
import SecondaryText from '@/components/AppText/Cat/SecondaryText';

export default class extends React.PureComponent {
  render() {
    const {
      title,
      type,
      rightAction = () => {},
      color = themeCatColor.font.black,
      fontWeight = 'normal',
    } = this.props;
    return (
      <View style={styles.container}>
        <View style={styles.titleCon}>
          {/* <View style={styles.leftBlock} /> */}
          <CommonText style={[styles.titleText, { color, fontWeight }]}>{title}</CommonText>
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
            <View style={styles.clearIconCon}>
              <Image style={styles.clearIcon} source={{ uri: myImages.btnClear }} />
            </View>
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
    backgroundColor: themeCatColor.primaryColor,
    width: 3,
    height: 15,
    marginRight: 9,
  },
  moreCon: {
    ...themeLayout.flex('row'),
  },
  rightIcon: {
    width: 13,
    height: 13,
    marginLeft: 4,
  },
  clearIconCon: {
    ...themeLayout.padding(5, 0, 0, 5),
  },
  clearIcon: {
    width: 15,
    height: 15,
  },
  titleText: {
    marginRight: 5,
  },
});
