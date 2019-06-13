import React from 'react';
import { View, Image, StyleSheet } from 'react-native';
import LargerText from '@/components/AppText/LargerText';
import SecondaryText from '@/components/AppText/SecondaryText';
import myImages from '@/utils/myImages';
import { scale, themeLayout } from '@/config';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { OpenActivity } from '@/components/NativeModules';
import ImageWithDefault from '@/components/ImageWithDefault';
import { actionBeforeCheckLogin } from '@/utils/utils';

export default class extends React.PureComponent {
  gotoEditUser = () => {
    OpenActivity.openUserData();
  };

  gotoPersonPage = () => {
    const { userInfo } = this.props;
    OpenActivity.openUserIndex(userInfo.mobilephone);
  };

  render() {
    const { style, data, userInfo } = this.props;
    return (
      <View style={[style, styles.container]}>
        <TouchableNativeFeedback
          tapArea={1}
          onPress={() => actionBeforeCheckLogin(this.gotoPersonPage)}
        >
          <View style={styles.avatarCon}>
            <ImageWithDefault style={styles.avatar} source={{ uri: data.avatar }} />
          </View>
        </TouchableNativeFeedback>
        <View style={styles.userData}>
          <View>
            <TouchableNativeFeedback
              tapArea={1}
              onPress={() => actionBeforeCheckLogin(this.gotoPersonPage)}
            >
              <View style={styles.padding(20, 20, 0, 0)}>
                <LargerText style={styles.nameText}>{data.name}</LargerText>
              </View>
            </TouchableNativeFeedback>
            <TouchableNativeFeedback
              tapArea={1}
              onPress={() => actionBeforeCheckLogin(this.gotoEditUser)}
            >
              <View style={styles.padding(0, 20, 20, 0)}>
                <SecondaryText style={styles.editText}>
                  {userInfo ? '编辑个人资料' : '登录体验更多功能'}
                </SecondaryText>
              </View>
            </TouchableNativeFeedback>
          </View>
          <TouchableNativeFeedback onPress={() => actionBeforeCheckLogin(this.gotoPersonPage)}>
            <View style={styles.iconCon}>
              <Image style={styles.iconRight} source={{ uri: myImages.nextWhite }} />
            </View>
          </TouchableNativeFeedback>
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
  avatarCon: {
    width: scale(70),
    height: scale(70),
    borderRadius: scale(35),
    overflow: 'hidden',
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
    fontSize: scale(18),
  },
  editText: {
    color: '#fff',
  },
  padding: (top, right, bottom, left) => {
    return {
      ...themeLayout.padding(scale(top), scale(right), scale(bottom), scale(left)),
    };
  },
  iconCon: { ...themeLayout.padding(scale(42)) },
  iconRight: {
    width: scale(15),
    height: scale(15),
  },
});
