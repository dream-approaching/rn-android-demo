import React from 'react';
import { View, StyleSheet } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { scale, themeLayout, themeColor } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import CommonText from '@/components/AppText/CommonText';
import SmallText from '@/components/AppText/SmallText';
import LikeBtn from '@/components/Comment/likeBtn';
import { OpenActivity } from '@/components/NativeModules';
import { LIKE_TYPE } from '@/config/constants';
import ImageWithDefault from '../ImageWithDefault';

export default class ChildItem extends React.Component {
  gotoPersonPage = () => {
    const { itemData } = this.props;
    OpenActivity.openUserIndex(itemData.mobilephone);
  };

  render() {
    const { itemData, replyAction, type } = this.props;
    const isMain = type === 'main';
    return (
      <View style={[styles.mainComment, { backgroundColor: isMain ? '#fff' : 'transparent' }]}>
        <TouchableNativeFeedback onPress={this.gotoPersonPage}>
          <ImageWithDefault style={styles.avatar(isMain)} source={{ uri: itemData.head_image }} />
        </TouchableNativeFeedback>
        <View style={styles.rightBody(isMain)}>
          <View style={styles.userBar}>
            <SecondaryText style={{ color: isMain ? '#303030' : '#707070' }}>
              {itemData.commit_user}
            </SecondaryText>
            <LikeBtn type={LIKE_TYPE.comment} itemData={itemData} size={isMain ? 15 : 13} />
          </View>
          <SmallText>{itemData.timestr}</SmallText>
          <TouchableNativeFeedback onPress={() => replyAction(itemData)}>
            <CommonText style={[styles.replyText, styles.textLineHeight(20)]}>
              {itemData.content}
            </CommonText>
          </TouchableNativeFeedback>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  mainComment: {
    ...themeLayout.padding(scale(16), scale(16), 0),
    ...themeLayout.flex('row', 'center', 'flex-start'),
  },
  rightBody: isMain => {
    const style = {
      flex: 1,
      marginLeft: scale(8),
      paddingBottom: scale(13),
    };
    return isMain ? style : { ...style, ...themeLayout.borderSide() };
  },
  avatar: isMain => {
    const size = isMain ? 51 : 46;
    return {
      width: scale(size),
      height: scale(size),
      borderRadius: scale(25),
      backgroundColor: themeColor.bgF4,
    };
  },
  userBar: {
    ...themeLayout.flex('row', 'space-between'),
  },
  replyText: {
    marginTop: scale(3),
  },
  textLineHeight: num => ({
    lineHeight: scale(num),
  }),
});
