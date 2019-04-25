import React from 'react';
import { View, Image, StyleSheet } from 'react-native';
import CommonText from '@/components/AppText/CommonText';
import myImages from '@/utils/images';
import { scale, themeLayout } from '@/config';
import MenuCardItem from './MenuCardItem';

export default class extends React.PureComponent {
  render() {
    const { menu, style } = this.props;
    return (
      <View style={style}>
        <View style={styles.cardTitle}>
          <Image resizeMode='contain' style={styles.icon} source={{uri: myImages.collection}} />
          <CommonText style={styles.titleText}>我的收藏</CommonText>
        </View>
        <View style={styles.cardListCon}>
          {menu.map(item => {
            return <MenuCardItem key={item.title} item={item} />;
          })}
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  cardTitle: {
    ...themeLayout.flex('row', 'flex-start'),
    ...themeLayout.padding(0, scale(12))
  },
  titleText: {
    marginLeft: scale(12)
  },
  cardListCon: {
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.padding(0, 0, 0, scale(30)),
    width: '100%'
  },
  icon: {
    width: scale(18),
    height: scale(18)
  }
});
