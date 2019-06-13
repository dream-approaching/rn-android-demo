import React from 'react';
import { View, Image, StyleSheet, BackHandler } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, themeCatSize } from '@/config';
import LargerText from '@/components/AppText/Cat/LargerText';
import myImages from '@/utils/myImages';

export default class LeftHeader extends React.PureComponent {
  static navigationOptions = {
    header: null,
  };

  static defaultProps = {
    showLeftIcon: true,
    title: '',
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
    const { showLeftIcon, title, leftComponent, rightComponent } = this.props;
    return (
      <View style={styles.container}>
        <View style={styles.leftPart}>
          <TouchableNativeFeedback onPress={this.handleBack}>
            <View style={styles.leftIconCon}>
              {showLeftIcon && (
                <Image style={styles.leftIcon} source={{ uri: myImages.leftBack }} />
              )}
            </View>
          </TouchableNativeFeedback>
          <TouchableNativeFeedback>
            <View>{leftComponent || <LargerText style={styles.title}>{title}</LargerText>}</View>
          </TouchableNativeFeedback>
        </View>
        <View style={styles.rightPart}>{rightComponent}</View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    height: 68,
    ...themeLayout.flex('row'),
    ...themeLayout.padding(0, 11, 0, 6),
    backgroundColor: '#fff',
    paddingTop: 20,
  },
  leftPart: {
    flex: 2,
    ...themeLayout.flex('row', 'flex-start'),
  },
  rightPart: {
    flex: 1,
    ...themeLayout.flex('row', 'flex-end'),
  },
  leftIconCon: {
    ...themeLayout.padding(10),
  },
  title: {
    fontSize: themeCatSize.font.superLarge,
  },
  leftIcon: {
    width: 20,
    height: 20,
  },
});
