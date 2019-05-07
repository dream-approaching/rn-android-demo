import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { scale, themeLayout, themeColor } from '@/config';
import { TouchableOpacity } from 'react-native-gesture-handler';
import myImages from '@/utils/myImages';
import CommonText from '@/components/AppText/CommonText';
import SecondaryText from '@/components/AppText/SecondaryText';
import NavigationService from '@/utils/NavigationService';

export default class CommentPage extends React.PureComponent {
  handleNavigate = () => {
    const { navigate } = this.props;
    NavigationService.navigate(navigate, {});
  };

  render() {
    const { title, type, rightAction, color = themeColor.font.secondary } = this.props;
    return (
      <View style={styles.container}>
        <View style={styles.titleCon}>
          <View style={styles.leftBlock} />
          <CommonText style={[styles.titleText, { color }]}>{title}</CommonText>
        </View>
        {type === 'more' && (
          <TouchableOpacity style={styles.moreCon} onPress={this.handleNavigate}>
            <SecondaryText>查看更多</SecondaryText>
            <Image style={styles.rightIcon} source={{ uri: myImages.next }} />
          </TouchableOpacity>
        )}
        {type === 'del' && (
          <TouchableOpacity style={styles.cancelBtn} onPress={rightAction}>
            <Image style={styles.clearIcon} source={{ uri: myImages.btnClear }} />
          </TouchableOpacity>
        )}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    ...themeLayout.flex('row', 'space-between'),
  },
  titleCon: {
    ...themeLayout.flex('row'),
  },
  leftBlock: {
    backgroundColor: themeColor.primaryColor,
    width: scale(3),
    height: scale(15),
    marginRight: scale(9),
  },
  moreCon: {
    ...themeLayout.flex('row'),
  },
  rightIcon: {
    width: scale(13),
    height: scale(13),
    marginLeft: scale(4),
  },
  clearIcon: {
    width: scale(22),
    height: scale(22),
  },
});
