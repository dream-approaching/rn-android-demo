import React from 'react';
import { View, Image, StyleSheet } from 'react-native';
import { scale, themeLayout, themeSize } from '@/config';
import LargerText from '@/components/AppText/LargerText';
import { TouchableOpacity } from 'react-native-gesture-handler';
import NavigationService from '@/utils/NavigationService';
import myImages from '@/utils/images';

export default class CommentPage extends React.PureComponent {
  static navigationOptions = {
    header: null,
  };

  static defaultProps = {
    showLeftIcon: true,
    title: '',
  };

  handleBack = () => {
    const { backAction } = this.props;
    if (backAction) {
      return backAction();
    }
    NavigationService.navigate('Mine', {});
  };

  render() {
    const { showLeftIcon, title, centerComponent, rightComponent } = this.props;
    return (
      <View style={styles.container}>
        <View style={styles.leftIconCon}>
          {showLeftIcon && (
            <TouchableOpacity onPress={this.handleBack}>
              <Image style={styles.leftIcon} source={{ uri: myImages.leftBack }} />
            </TouchableOpacity>
          )}
        </View>
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
    flex: 1,
    marginLeft: -scale(20),
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
