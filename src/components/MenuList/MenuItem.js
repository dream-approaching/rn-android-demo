import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { themeLayout, scale } from '@/config';
import { TouchableOpacity } from 'react-native-gesture-handler';
import NavigationService from '@/navigation/NavigationService';
import myImages from '@/utils/images';
import CommonText from '@/components/AppText/CommonText';

export default class MenuItem extends React.PureComponent {
  handlePressButton = () => {
    const { item } = this.props;
    if (item.navigatePath) {
      NavigationService.navigate(item.navigatePath, {});
    } else {
      item.onPressAction && item.onPressAction();
    }
  };

  render() {
    const { item } = this.props;
    return (
      <TouchableOpacity onPress={this.handlePressButton}>
        <View style={[styles.menuItemCon]}>
          <View style={styles.menuLeft}>
            {(item.icon && (
              <Image resizeMode='contain' style={styles.iconLeft} source={item.icon} />
            )) ||
              null}
            <CommonText style={styles.menuTitle}>{item.title}</CommonText>
          </View>
          <Image style={styles.iconRight} source={myImages.next} />
        </View>
      </TouchableOpacity>
    );
  }
}

const styles = StyleSheet.create({
  menuItemCon: {
    ...themeLayout.flex('row', 'space-between', 'center'),
    ...themeLayout.borderSide('Bottom'),
    ...themeLayout.padding(0, scale(24), 0, scale(12)),
    height: scale(50)
  },
  menuLeft: {
    ...themeLayout.flex('row', 'flex-start', 'center')
  },
  menuTitle: {
    marginLeft: scale(12)
  },
  iconLeft: {
    width: scale(18),
    height: scale(18)
  },
  iconRight: {
    width: scale(15),
    height: scale(15)
  }
});
