import React from 'react';
import { View, StyleSheet, Image, TouchableOpacity } from 'react-native';
import { themeLayout, scale, themeColor } from '@/config';
import CommonText from '@/components/AppText/CommonText';

export default class extends React.PureComponent {
  render() {
    const { itemData, islastOne } = this.props;
    return (
      <TouchableOpacity style={styles.container(islastOne)}>
        {itemData.img && <Image style={styles.leftImg} source={{ uri: itemData.img }} />}
        <View style={styles.itemRight}>
          <CommonText numberOfLines={2} style={styles.articleTitle}>
            {itemData.title}
          </CommonText>
        </View>
      </TouchableOpacity>
    );
  }
}

const styles = StyleSheet.create({
  container: isLastOne => {
    const obj = {
      flex: 1,
      ...themeLayout.flex('row', 'space-between'),
      ...themeLayout.margin(0, 0, 0, scale(8)),
      ...themeLayout.padding(scale(16), 0),
    };
    return isLastOne ? obj : { ...obj, ...themeLayout.borderSide() };
  },
  leftImg: {
    width: scale(57),
    height: scale(57),
    backgroundColor: themeColor.bgF4,
  },
  itemRight: {
    flex: 1,
    marginLeft: scale(18),
    ...themeLayout.flex('column', 'space-between', 'flex-start'),
  },
  articleTitle: {
    fontSize: scale(15),
    lineHeight: scale(24),
  },
});
