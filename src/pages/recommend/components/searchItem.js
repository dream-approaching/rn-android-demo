import React from 'react';
import { View, StyleSheet, Text } from 'react-native';
import { scale, themeLayout } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import CommonText from '@/components/AppText/CommonText';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { spiltHighlightText } from '@/utils/utils';
import ImageWithDefault from '@/components/ImageWithDefault';

export default class CommentPage extends React.Component {
  render() {
    const { itemData, searchKey, gotoAppAction = () => {} } = this.props;
    if (!itemData) return null;
    const { app_name_cn: title, app_short_desc: desc } = itemData;
    const titleArr = spiltHighlightText(title, searchKey);
    const descArr = spiltHighlightText(desc, searchKey);
    return (
      <TouchableOpacity onPress={gotoAppAction} style={styles.itemCon}>
        <View style={styles.coverCon}>
          <ImageWithDefault
            resizeMode='cover'
            style={styles.cover}
            source={{ uri: itemData.app_logo }}
          />
        </View>
        <View style={styles.rightBody}>
          <Text>
            {titleArr.map((item, index) => {
              const highlightStyle = item === searchKey ? { color: '#fb716b' } : {};
              return (
                <CommonText key={index} style={[styles.searchTitle, highlightStyle]}>
                  {item}
                </CommonText>
              );
            })}
          </Text>
          <Text>
            {descArr.map((item, index) => {
              const highlightStyle = item === searchKey ? { color: '#fb716b' } : {};
              return (
                <SecondaryText
                  key={index}
                  numberOfLines={1}
                  style={[styles.searchDesc, highlightStyle]}
                >
                  {item}
                </SecondaryText>
              );
            })}
          </Text>
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
