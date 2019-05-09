import React from 'react';
import { View, Image, StyleSheet } from 'react-native';
import LargerText from '@/components/AppText/LargerText';
import SecondaryText from '@/components/AppText/SecondaryText';
import myImages from '@/utils/myImages';
import { scale, themeLayout } from '@/config';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { OpenActivity } from '@/components/NativeModules';

export default class extends React.PureComponent {
  gotoEditUser = () => {
    OpenActivity.openUserData();
  };

  gotoPersonPage = () => {
    OpenActivity.openUserIndex('18845299535');
  };

  render() {
    const { style, data } = this.props;
    return (
      <View style={[style, styles.container]}>
        <TouchableOpacity onPress={this.gotoPersonPage}>
          <Image style={styles.avatar} source={{ uri: data.avatar }} />
        </TouchableOpacity>
        <View style={styles.userData}>
          <View style={styles.textCon}>
            <TouchableOpacity onPress={this.gotoPersonPage}>
              <LargerText style={styles.nameText}>{data.name}</LargerText>
            </TouchableOpacity>
            <TouchableOpacity onPress={this.gotoEditUser}>
              <SecondaryText style={styles.editText}>编辑个人资料</SecondaryText>
            </TouchableOpacity>
          </View>
          <TouchableOpacity style={styles.iconCon} onPress={this.gotoPersonPage}>
            <Image style={styles.iconRight} source={{ uri: myImages.nextWhite }} />
          </TouchableOpacity>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    ...themeLayout.flex('row', 'space-between', 'center'),
    ...themeLayout.padding(scale(42), 0, 0, scale(26)),
  },
  avatar: {
    width: scale(70),
    height: scale(70),
    borderRadius: scale(35),
  },
  userData: {
    ...themeLayout.flex('row', 'space-between', 'center'),
    width: scale(250),
  },
  nameText: {
    marginBottom: scale(3),
    color: '#fff',
  },
  editText: {
    color: '#fff',
  },
  iconCon: { ...themeLayout.padding(scale(42)) },
  iconRight: {
    width: scale(15),
    height: scale(15),
  },
});
