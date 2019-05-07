import React from 'react';
import { View, ImageBackground, StyleSheet, Image } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { themeLayout, scale } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import SmallText from '@/components/AppText/SmallText';
import CommonText from '@/components/AppText/CommonText';
import myImages from '@/utils/myImages';

export default class extends React.PureComponent {
  render() {
    const { itemData } = this.props;
    console.log('%citemData:', 'color: #0e93e0;background: #aaefe5;', itemData);
    return (
      <TouchableOpacity style={styles.container}>
        <Image style={styles.appIcon} source={{ uri: itemData.avatar }} />
        <View style={styles.itemRight}>
          <CommonText style={styles.appTitle}>{itemData.name}</CommonText>
          <SecondaryText style={styles.desc} numberOfLines={1}>
            {itemData.desc}
          </SecondaryText>
          <View>
            {itemData.label && (
              <ImageBackground
                resizeMode="contain"
                source={{ uri: myImages.bgLabel }}
                style={styles.bgLabel}
              >
                <SmallText style={styles.labelText}>{itemData.label}</SmallText>
              </ImageBackground>
            )}
          </View>
        </View>
      </TouchableOpacity>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    ...themeLayout.flex('row', 'space-between', 'flex-start'),
    ...themeLayout.borderSide(),
    ...themeLayout.margin(0, scale(26)),
    ...themeLayout.padding(scale(16), 0),
  },
  appIcon: {
    width: scale(57),
    height: scale(57),
    borderRadius: scale(10),
  },
  itemRight: {
    flex: 1,
    marginLeft: scale(18),
    height: scale(57),
    ...themeLayout.flex('column', 'space-between', 'flex-start'),
  },
  desc: {
    flexWrap: 'wrap',
  },
  bgLabel: {
    width: scale(29),
    height: scale(14),
    ...themeLayout.flex(),
  },
  appTitle: {
    fontSize: scale(16),
  },
  labelText: {
    fontSize: scale(8),
    color: '#fff',
  },
});
