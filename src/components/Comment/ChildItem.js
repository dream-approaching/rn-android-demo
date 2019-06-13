import React from 'react';
import { View, StyleSheet, Text, TouchableWithoutFeedback } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, themeCatColor } from '@/config';
import SecondaryText from '@/components/AppText/Cat/SecondaryText';
import SmallText from '@/components/AppText/Cat/SmallText';
import LikeBtn from '@/components/Comment/Cat/likeBtn';
import { OpenActivity } from '@/components/NativeModules';
import { LIKE_TYPE } from '@/config/constants';
import ImageWithDefault from '@/components/ImageWithDefault';

export default class ChildItem extends React.Component {
  gotoPersonPage = mobilephone => {
    OpenActivity.openUserIndex(mobilephone);
  };

  render() {
    const { itemData, replyAction = () => {}, type, origin } = this.props;
    const isMain = type === 'main';
    const isXshare = origin === 'xshare';
    const pathArr = itemData.path.split('-');
    const showReplyBtn = pathArr.length >= (isXshare ? 2 : 3);
    return (
      <View style={[styles.mainComment, { backgroundColor: isMain ? '#fff' : 'transparent' }]}>
        <TouchableNativeFeedback onPress={() => this.gotoPersonPage(itemData.mobilephone)}>
          <ImageWithDefault style={styles.avatar(isMain)} source={{ uri: itemData.head_image }} />
        </TouchableNativeFeedback>
        <View style={styles.rightBody(isMain)}>
          <View style={styles.userBar}>
            <SecondaryText style={{ color: isMain ? '#707070' : '#707070' }}>
              {itemData.commit_user}
            </SecondaryText>
            <LikeBtn type={LIKE_TYPE.comment} itemData={itemData} size={isMain ? 15 : 13} />
          </View>
          <SmallText>{itemData.timestr}</SmallText>
          <TouchableWithoutFeedback onPress={() => replyAction(itemData)}>
            <Text style={[styles.replyText, styles.textLineHeight(20), { flexWrap: 'wrap' }]}>
              <SecondaryText>
                {showReplyBtn && (
                  <SecondaryText style={styles.replayText}>
                    回复
                    <TouchableWithoutFeedback
                      onPress={() => this.gotoPersonPage(itemData.pidinfo.mobilephone)}
                    >
                      <SecondaryText style={[styles.replyTitle]}>
                        @{itemData.pidinfo.nick_name}：
                      </SecondaryText>
                    </TouchableWithoutFeedback>
                  </SecondaryText>
                )}
              </SecondaryText>
              <TouchableWithoutFeedback onPress={() => replyAction(itemData)}>
                <SecondaryText style={styles.contentText}>{itemData.content}</SecondaryText>
              </TouchableWithoutFeedback>
            </Text>
          </TouchableWithoutFeedback>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  mainComment: {
    ...themeLayout.padding(16, 16, 0),
    ...themeLayout.flex('row', 'center', 'flex-start'),
  },
  rightBody: isMain => {
    const style = {
      flex: 1,
      marginLeft: 8,
      paddingBottom: 13,
    };
    return isMain ? style : { ...style, ...themeLayout.borderSide() };
  },
  avatar: isMain => {
    const size = isMain ? 46 : 38;
    return {
      width: size,
      height: size,
      borderRadius: 25,
      backgroundColor: themeCatColor.bgF4,
    };
  },
  userBar: {
    ...themeLayout.flex('row', 'space-between'),
  },
  replyText: {
    marginTop: 3,
  },
  textLineHeight: num => ({
    lineHeight: num,
  }),
  replyTitle: {
    color: '#6a83a2',
  },
  contentText: {
    color: themeCatColor.font.black,
  },
});
