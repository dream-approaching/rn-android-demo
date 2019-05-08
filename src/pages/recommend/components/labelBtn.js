import React from 'react';
import { StyleSheet, Image, TouchableOpacity, View } from 'react-native';
import CommonText from '@/components/AppText/CommonText';
import myImages from '@/utils/myImages';
import { scale, themeLayout, themeColor } from '@/config';

export default class extends React.Component {
  render() {
    const { children, empty, pressAction = () => {} } = this.props;
    return (
      <TouchableOpacity onPress={pressAction}>
        <View style={styles.imgbg}>
          <CommonText style={styles.btnText}>{empty ? '+More' : children}</CommonText>
          {!empty && <Image style={styles.delImg} source={{ uri: myImages.btnDel }} />}
        </View>
      </TouchableOpacity>
    );
  }
}

const styles = StyleSheet.create({
  imgbg: {
    ...themeLayout.flex('row'),
    ...themeLayout.padding(scale(7), scale(15), scale(7), scale(15)),
    borderRadius: scale(8),
    overflow: 'hidden',
    marginRight: scale(10),
    backgroundColor: themeColor.primaryColor,
  },
  delImg: {
    width: scale(18),
    height: scale(18),
    marginLeft: scale(4),
    marginRight: -scale(3),
  },
  btnText: {
    color: '#fff',
    fontSize: scale(14),
  },
});
