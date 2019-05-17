import React from 'react';
import { View, Image, StyleSheet, BackHandler } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { scale, themeLayout, themeSize } from '@/config';
import LargerText from '@/components/AppText/LargerText';
import myImages from '@/utils/myImages';

export default class CommentPage extends React.PureComponent {
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
    const { showLeftIcon, title, centerComponent, rightComponent } = this.props;
    return (
      <View style={styles.container}>
        <TouchableNativeFeedback onPress={this.handleBack}>
          <View style={styles.leftIconCon}>
            {showLeftIcon && <Image style={styles.leftIcon} source={{ uri: myImages.leftBack }} />}
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
  container: {
    height: scale(68),
    ...themeLayout.borderSide('Bottom'),
    ...themeLayout.flex('row'),
    ...themeLayout.padding(0, scale(16), 0, scale(6)),
    backgroundColor: '#fff',
    paddingTop: scale(20),
  },
  titleCon: {
    ...themeLayout.flex('row'),
    flex: 2,
    marginLeft: -scale(20),
    flexWrap: 'nowrap',
  },
  title: {
    fontSize: themeSize.font.superLarge,
  },
  leftIconCon: {
    flex: 1,
    ...themeLayout.padding(scale(10)),
  },
  leftIcon: {
    width: scale(16),
    height: scale(16),
  },
  right: {
    flex: 1,
  },
});
