import React from 'react';
import { View, Image, StyleSheet, BackHandler } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, themeCatSize } from '@/config';
import LargerText from '@/components/AppText/Cat/LargerText';
import myImages from '@/utils/myImages';

export default class Header extends React.PureComponent {
  static navigationOptions = {
    header: null,
  };

  static defaultProps = {
    showLeftIcon: true,
    title: '',
    backType: 'arrow',
    showBorder: true,
  };

  handleBack = () => {
    const { navigation, backAction } = this.props;
    if (backAction) {
      return backAction();
    }
    if (navigation) {
      return navigation.pop();
    }
    BackHandler.exitApp();
  };

  render() {
    const {
      showLeftIcon,
      title,
      centerComponent,
      rightComponent,
      backType,
      showBorder,
    } = this.props;
    return (
      <View style={styles.container(showBorder)}>
        <TouchableNativeFeedback onPress={this.handleBack}>
          <View style={styles.leftIconCon}>
            {showLeftIcon && (
              <Image
                style={styles.leftIcon}
                source={{ uri: backType === 'arrow' ? myImages.leftBack : myImages.delBack }}
              />
            )}
          </View>
        </TouchableNativeFeedback>
        <View style={styles.titleCon}>
          {centerComponent || <LargerText style={styles.title}>{title}</LargerText>}
        </View>
        <View style={styles.right}>{rightComponent}</View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: showBorder => {
    const obj = {
      height: 68,
      ...themeLayout.flex('row'),
      ...themeLayout.padding(0, 16, 0, 6),
      backgroundColor: '#fff',
      paddingTop: 20,
    };
    return showBorder ? { ...obj, ...themeLayout.borderSide('Bottom') } : obj;
  },
  titleCon: {
    ...themeLayout.flex('row'),
    flex: 2,
    marginLeft: -20,
    flexWrap: 'nowrap',
  },
  title: {
    fontSize: themeCatSize.font.superLarge,
  },
  leftIconCon: {
    flex: 1,
    ...themeLayout.padding(10),
  },
  leftIcon: {
    width: 14,
    height: 14,
  },
  right: {
    flex: 1,
    ...themeLayout.flex('row', 'flex-end'),
  },
});
