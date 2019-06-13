import React from 'react';
import { View, StyleSheet, Text } from 'react-native';
import { scale, themeLayout } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import CommonText from '@/components/AppText/CommonText';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { spiltHighlightText } from '@/utils/utils';
import ImageWithDefault from '@/components/ImageWithDefault';

export default class SearchItem extends React.Component {
  render() {
    const { itemData, searchKey, gotoAppAction = () => {} } = this.props;
    if (!itemData) return null;
    const { app_name_cn: title, app_short_desc: desc } = itemData;
    const titleArr = spiltHighlightText(title, searchKey);
    const descArr = spiltHighlightText(desc, searchKey);
    return (
      <TouchableNativeFeedback onPress={gotoAppAction}>
        <View style={styles.itemCon}>
          <View style={styles.coverCon}>
            <ImageWithDefault
              resizeMode='cover'
              style={styles.cover}
              source={{ uri: itemData.app_logo }}
            />
          </View>
          <View style={styles.rightBody}>
            <Text numberOfLines={1}>
              {titleArr.map((item, index) => {
                const highlightStyle = item === searchKey ? { color: '#fb716b' } : {};
                return (
                  <CommonText key={index} style={[styles.searchTitle, highlightStyle]}>
                    {item}
                  </CommonText>
                );
              })}
            </Text>
            <Text numberOfLines={1}>
              {descArr.map((item, index) => {
                const highlightStyle = item === searchKey ? { color: '#fb716b' } : {};
                return (
                  <SecondaryText key={index} style={[styles.searchDesc, highlightStyle]}>
                    {item}
                  </SecondaryText>
                );
              })}
            </Text>
          </View>
        </View>
      </TouchableNativeFeedback>
    );
  }
}

const styles = StyleSheet.create({
  itemCon: {
    ...themeLayout.flex('row'),
    ...themeLayout.padding(scale(8), 0),
    ...themeLayout.borderSide(),
  },
  coverCon: {
    ...themeLayout.border(),
    borderRadius: scale(12),
    overflow: 'hidden',
  },
  cover: {
    width: scale(61),
    height: scale(61),
  },
  rightBody: {
    flex: 1,
    marginLeft: scale(13),
  },
  searchTitle: {
    fontSize: scale(16),
  },
  searchDesc: {
    marginTop: scale(5),
  },
});
