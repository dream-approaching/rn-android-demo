import React from 'react';
import { StyleSheet, ImageBackground, Image, TouchableOpacity } from 'react-native';
import CommonText from '@/components/AppText/CommonText';
import myImages from '@/utils/myImages';
import { scale, themeLayout } from '@/config';

export default class extends React.Component {
  render() {
    const { children, empty } = this.props;
    return (
      <TouchableOpacity>
        <ImageBackground resizeMode="cover" style={styles.imgbg} source={{ uri: myImages.bg }}>
          <CommonText style={styles.btnText}>{empty ? '+More' : children}</CommonText>
          {!empty && <Image style={styles.delImg} source={{ uri: myImages.btnDel }} />}
        </ImageBackground>
      </TouchableOpacity>
    );
  }
}

const styles = StyleSheet.create({
  imgbg: {
    ...themeLayout.flex('row'),
    ...themeLayout.padding(scale(7), scale(14), scale(7), scale(14)),
    borderRadius: scale(10),
    overflow: 'hidden',
    marginRight: scale(10),
  },
  delImg: {
    width: scale(18),
    height: scale(18),
    marginLeft: scale(3),
  },
  btnText: {
    color: '#fff',
    fontSize: scale(14),
  },
});
