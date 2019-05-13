import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { scale, themeLayout, themeColor } from '@/config';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { OpenRnActivity } from '@/components/NativeModules';
import ImageWithDefault from '@/components/ImageWithDefault';
import myImages from '@/utils/myImages';
import { COMMENT_TYPE } from '@/config/constants';
import SmallText from '@/components/AppText/SmallText';
import CommonText from '@/components/AppText/CommonText';
import SecondaryText from '@/components/AppText/SecondaryText';

export default class extends React.PureComponent {
  gotoPersonPage = () => {
    const { itemData } = this.props;
    OpenRnActivity('myShare', JSON.stringify({ phone: itemData.mobilephone }));
  };

  render() {
    const { itemData } = this.props;
    const isShowText = +itemData.type === COMMENT_TYPE.app || +itemData.type === COMMENT_TYPE.share;
    return (
      <View style={styles.container}>
        <TouchableOpacity onPress={this.gotoPersonPage}>
          <View style={styles.avatarCon}>
            <ImageWithDefault style={styles.avatar} source={{ uri: itemData.head_image }} />
            {+itemData.is_big_v === 2 && (
              <Image style={styles.bigV} source={{ uri: myImages.approve }} />
            )}
          </View>
        </TouchableOpacity>
        <View style={styles.centerBody}>
          <CommonText>{itemData.nick_name}</CommonText>
          <SmallText>{itemData.timestr}</SmallText>
          <SecondaryText>{itemData.content}</SecondaryText>
          {itemData.base_content && (
            <SecondaryText style={styles.baseContent}>{itemData.base_content}</SecondaryText>
          )}
        </View>
        <View style={styles.rightBody}>
          {(isShowText && (
            <View style={styles.rightTextCon}>
              <SmallText numberOfLines={4}>{itemData.title}</SmallText>
            </View>
          )) || (
            <Image
              style={styles.rightImg}
              source={{ uri: itemData.title || myImages.defaultHeader }}
            />
          )}
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    ...themeLayout.padding(scale(16)),
    ...themeLayout.flex('row', 'center', 'flex-start'),
    ...themeLayout.borderSide(),
  },
  centerBody: {
    flex: 1,
    marginLeft: scale(16),
  },
  rightBody: {
    marginLeft: scale(30),
  },
  avatarCon: {
    width: scale(50),
    height: scale(50),
  },
  avatar: {
    width: scale(50),
    height: scale(50),
    borderRadius: scale(25),
    backgroundColor: themeColor.bgF4,
  },
  bigV: {
    position: 'absolute',
    bottom: 0,
    right: 0,
    zIndex: 1000,
    width: scale(14),
    height: scale(14),
  },
  textLineHeight: num => ({
    lineHeight: scale(num),
  }),
  baseContent: {
    color: themeColor.font.small,
  },
  rightTextCon: {
    width: scale(50),
    flexWrap: 'wrap',
  },
  rightImg: {
    width: scale(50),
    height: scale(50),
  },
});
