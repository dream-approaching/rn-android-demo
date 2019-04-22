import React from 'react';
import { View, Image, StyleSheet } from 'react-native';
import LargerText from '@/components/AppText/LargerText';
import SecondaryText from '@/components/AppText/SecondaryText';
import myImages from '@/utils/images';
import { scale, themeLayout } from '@/config';
import { TouchableOpacity } from 'react-native-gesture-handler';

export default class extends React.PureComponent {
  render() {
    const { style, data } = this.props;
    return (
      <View style={[style, styles.container]}>
        <TouchableOpacity>
          <Image style={styles.avatar} source={{ uri: data.avatar }} />
        </TouchableOpacity>
        <TouchableOpacity style={styles.userData}>
          <View style={styles.textCon}>
            <LargerText style={styles.nameText}>{data.name}</LargerText>
            <SecondaryText>编辑个人资料</SecondaryText>
          </View>
          <Image style={styles.iconRight} source={myImages.next} />
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    ...themeLayout.flex('row', 'space-between', 'center'),
    ...themeLayout.padding(0, scale(38), 0, scale(26))
  },
  avatar: {
    width: scale(70),
    height: scale(70),
    borderRadius: scale(35)
  },
  userData: {
    ...themeLayout.flex('row', 'space-between', 'center'),
    width: scale(180)
  },
  nameText: {
    marginBottom: scale(2)
  },
  iconRight: {
    width: scale(15),
    height: scale(15)
  }
});
