import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { themeLayout } from '@/config';
import myImages from '@/utils/myImages';
import CommonText from '@/components/AppText/Cat/CommonText';
import { actionBeforeCheckLogin } from '@/utils/utils';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';

export default class MenuItem extends React.PureComponent {
  handlePressButton = () => {
    const { item } = this.props;
    item.onPressAction && item.onPressAction();
  };

  pressMenu = () => {
    const { item } = this.props;
    if (item.notNeedAuth) {
      this.handlePressButton();
    } else {
      actionBeforeCheckLogin(this.handlePressButton);
    }
  };

  render() {
    const { item, islast } = this.props;
    return (
      <TouchableNativeFeedback tapArea={1} notOut onPress={this.pressMenu}>
        <View style={[styles.menuItemCon(islast)]}>
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
  menuItemCon: islast => {
    const obj = {
      ...themeLayout.flex('row', 'space-between', 'center'),
      ...themeLayout.padding(0, 24, 0, 12),
      height: 56,
    };
    return islast ? obj : { ...obj, ...themeLayout.borderSide('Bottom') };
  },
  menuLeft: {
    ...themeLayout.flex('row', 'flex-start', 'center'),
  },
  menuTitle: {
    marginLeft: 12,
  },
  iconLeft: {
    width: 18,
    height: 18,
  },
  iconRight: {
    width: 15,
    height: 15,
  },
});
