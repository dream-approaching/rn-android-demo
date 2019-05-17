import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { themeLayout, scale } from '@/config';
import myImages from '@/utils/myImages';
import CommonText from '@/components/AppText/CommonText';
import { actionBeforeCheckLogin } from '@/utils/utils';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';

export default class MenuItem extends React.PureComponent {
  handlePressButton = () => {
    const { item } = this.props;
    item.onPressAction && item.onPressAction();
  };

  render() {
    const { item } = this.props;
    return (
      <TouchableNativeFeedback
        notOut
        onPress={() => actionBeforeCheckLogin(this.handlePressButton)}
      >
        <View style={[styles.menuItemCon]}>
          <View style={styles.menuLeft}>
            {(item.icon && (
              <Image resizeMode='contain' style={styles.iconLeft} source={{ uri: item.icon }} />
            )) ||
              null}
            <CommonText style={styles.menuTitle}>{item.title}</CommonText>
          </View>
          <Image style={styles.iconRight} source={{ uri: myImages.next }} />
        </View>
      </TouchableNativeFeedback>
    );
  }
}

const styles = StyleSheet.create({
  menuItemCon: {
    ...themeLayout.flex('row', 'space-between', 'center'),
    ...themeLayout.borderSide('Bottom'),
    ...themeLayout.padding(0, scale(24), 0, scale(12)),
    height: scale(50),
  },
  menuLeft: {
    ...themeLayout.flex('row', 'flex-start', 'center'),
  },
  menuTitle: {
    marginLeft: scale(12),
  },
  iconLeft: {
    width: scale(18),
    height: scale(18),
  },
  iconRight: {
    width: scale(15),
    height: scale(15),
  },
});
