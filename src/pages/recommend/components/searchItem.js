import React from 'react';
import { View, StyleSheet, Text, Image } from 'react-native';
import { scale, themeLayout } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import CommonText from '@/components/AppText/CommonText';
import { TouchableOpacity } from 'react-native-gesture-handler';

export default class CommentPage extends React.Component {
  render() {
    const { itemData, searchKey } = this.props;
    if (!itemData) return null;
    const { app_name_cn: title } = itemData;
    const highlightIndex = title.indexOf(searchKey);
    if (highlightIndex < 0) return null;
    const arr = [
      title.slice(0, highlightIndex),
      title.slice(highlightIndex, highlightIndex + searchKey.length),
      title.slice(highlightIndex + searchKey.length),
    ];
    return (
      <TouchableOpacity style={styles.itemCon}>
        <View style={styles.coverCon}>
          <Image resizeMode='cover' style={styles.cover} source={{ uri: itemData.app_logo }} />
        </View>
        <View style={styles.rightBody}>
          <Text>
            {arr.map((item, index) => {
              const highlightStyle = item === searchKey ? { color: '#fb716b' } : {};
              return (
                <CommonText key={index} style={[styles.searchTitle, highlightStyle]}>
                  {item}
                </CommonText>
              );
            })}
          </Text>
          <SecondaryText numberOfLines={1} ellipsizeMode='tail' style={styles.searchDesc}>
            {itemData.app_short_desc}
          </SecondaryText>
        </View>
      </TouchableOpacity>
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
