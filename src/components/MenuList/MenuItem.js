import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { themeLayout, scale, themeColor, themeSize } from '@/config';
import { TouchableOpacity } from 'react-native-gesture-handler';
import NavigationService from '@/navigation/NavigationService';
import myImages from '@/utils/images';
import CommonText from '@/components/AppText/CommonText';

const styles = StyleSheet.create({
  menuItemCon: {
    ...themeLayout.flex('row', 'space-between', 'center'),
    ...themeLayout.borderSide('Bottom'),
    ...themeLayout.padding(0, scale(14)),
    width: scale(320),
    height: scale(50),
    backgroundColor: themeColor.white
  },
  menuLeft: {
    ...themeLayout.flex('row', 'flex-start', 'center')
  },
  menuTitle: {
    marginLeft: scale(12)
  },
  iconRight: {
    width: scale(15),
    height: scale(15)
  }
});

export default class MenuItem extends React.PureComponent {
  handlePressButton = () => {
    const { item, onPressAction = () => {} } = this.props;
    if (item.navigatePath) {
      NavigationService.navigate(item.navigatePath, {});
    } else {
      onPressAction();
    }
  };

  render() {
    const { item } = this.props;
    console.log('item.leftIcon', item);
    return (
      <TouchableOpacity onPress={this.handlePressButton}>
        <View
          style={[styles.menuItemCon, { marginTop: scale(item.marginTop) || themeSize.minBorder }]}
        >
          <View style={styles.menuLeft}>
            {(item.leftIcon && <Image style={styles.iconRight} source={item.leftIcon} />) || null}
            <CommonText style={styles.menuTitle}>{item.title}</CommonText>
          </View>
          <View style={styles.menuRight}>
            <Image style={styles.iconRight} source={myImages.next} />
          </View>
        </View>
      </TouchableOpacity>
    );
  }
}
