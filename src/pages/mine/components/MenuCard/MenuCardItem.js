import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { themeLayout, scale } from '@/config';
import { TouchableOpacity } from 'react-native-gesture-handler';
import NavigationService from '@/utils/NavigationService';
import CommonText from '@/components/AppText/CommonText';

const styles = StyleSheet.create({
  menuItemCon: {
    ...themeLayout.flex('column', 'center', 'center'),
    ...themeLayout.padding(0, scale(12)),
  },
  menuTitle: {
    marginTop: scale(5),
    fontSize: scale(14),
  },
  iconStyle: {
    width: scale(28),
    height: scale(28),
  },
});

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
          {(item.icon && (
            <Image resizeMode='stretch' style={styles.iconStyle} source={{ uri: item.icon }} />
          )) ||
            null}
          <CommonText style={styles.menuTitle}>{item.title}</CommonText>
        </View>
      </TouchableOpacity>
    );
  }
}
