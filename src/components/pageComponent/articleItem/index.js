import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { themeLayout, scale } from '@/config';
import CommonText from '@/components/AppText/CommonText';

export default class extends React.PureComponent {
  render() {
    const { itemData } = this.props;
    console.log('%citemData:', 'color: #0e93e0;background: #aaefe5;', itemData);
    return (
      <TouchableOpacity style={styles.container}>
        <Image style={styles.leftImg} source={{ uri: itemData.avatar }} />
        <View style={styles.itemRight}>
          <CommonText numberOfLines={3} style={styles.articleTitle}>
            {itemData.name}
          </CommonText>
        </View>
      </TouchableOpacity>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.borderSide(),
    ...themeLayout.margin(0, scale(16), 0, scale(26)),
    ...themeLayout.padding(scale(16), 0),
  },
  leftImg: {
    width: scale(57),
    height: scale(57),
  },
  itemRight: {
    flex: 1,
    marginLeft: scale(18),
    ...themeLayout.flex('column', 'space-between', 'flex-start'),
  },
  articleTitle: {
    fontSize: scale(15),
    lineHeight: scale(20),
  },
});
