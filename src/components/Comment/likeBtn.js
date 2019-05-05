import React from 'react';
import { StyleSheet, Image } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { scale, themeLayout } from '@/config';
import SmallText from '@/components/AppText/SmallText';
import myImages from '@/utils/myImages';

export default class extends React.Component {
  render() {
    const { size = 14, likeNum, textStyle } = this.props;
    return (
      <TouchableOpacity style={styles.likeCon}>
        <Image style={styles.likeIcon(size)} source={{ uri: myImages.thumb }} />
        <SmallText style={textStyle}>{likeNum}</SmallText>
      </TouchableOpacity>
    );
  }
}

const styles = StyleSheet.create({
  likeCon: {
    ...themeLayout.flex('row', 'space-between'),
  },
  likeIcon: size => {
    return {
      width: scale(size),
      height: scale(size),
      marginRight: scale(3),
    };
  },
});
