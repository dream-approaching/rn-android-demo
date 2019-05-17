import React from 'react';
import { View, StyleSheet, Text } from 'react-native';
import { scale, themeLayout, themeColor } from '@/config';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import SecondaryText from '@/components/AppText/SecondaryText';
import CommonText from '@/components/AppText/CommonText';
import SmallText from '@/components/AppText/SmallText';
import LikeBtn from '@/components/Comment/likeBtn';
import { OpenActivity } from '@/components/NativeModules';
import { LIKE_TYPE } from '@/config/constants';
import ImageWithDefault from '../ImageWithDefault';

export default class extends React.PureComponent {
  gotoPersonPage = () => {
    const { itemData } = this.props;
    OpenActivity.openUserIndex(itemData.mobilephone);
  };

  render() {
    const { itemData, replyAction, seeAllChildAction, index } = this.props;

    return (
      <View style={styles.container}>
        <TouchableNativeFeedback onPress={this.gotoPersonPage}>
          <ImageWithDefault style={styles.avatar} source={{ uri: itemData.head_image }} />
        </TouchableNativeFeedback>
        <View style={styles.rightBody}>
          <SecondaryText>{itemData.commit_user}</SecondaryText>
          <TouchableNativeFeedback onPress={() => replyAction(itemData)}>
            <CommonText style={[styles.replyText, styles.textLineHeight(20)]}>
              {itemData.content}
            </CommonText>
          </TouchableNativeFeedback>
          {!!itemData.detailtwo.length && (
            <View style={styles.replyCon}>
              {itemData.detailtwo.map(item => {
                return (
                  <TouchableNativeFeedback key={item.id} onPress={() => replyAction(item)}>
                    <View style={{ marginTop: scale(3) }}>
                      <Text style={[styles.textLineHeight(18), { flexWrap: 'wrap' }]}>
                        <SecondaryText style={[styles.replyTitle]}>
                          {item.commit_user}：
                        </SecondaryText>
                        <SecondaryText>{item.content}</SecondaryText>
                      </Text>
                    </View>
                  </TouchableNativeFeedback>
                );
              })}
              {+itemData.count > 2 && (
                <TouchableNativeFeedback onPress={() => seeAllChildAction(itemData, index)}>
                  <View style={{ marginTop: scale(3) }}>
                    <SecondaryText style={[styles.replyTitle, styles.textLineHeight(18)]}>
                      {`共${itemData.count}条回复＞`}
                    </SecondaryText>
                  </View>
                </TouchableNativeFeedback>
              )}
            </View>
          )}
          <View style={styles.bottomBar}>
            <SmallText>{itemData.timestr}</SmallText>
            <LikeBtn
              type={LIKE_TYPE.comment}
              itemData={itemData}
              size={12}
              textStyle={styles.superSmallText}
            />
          </View>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    ...themeLayout.padding(0, scale(16)),
    ...themeLayout.flex('row', 'center', 'flex-start'),
    ...themeLayout.margin(scale(16), 0, 0),
  },
  rightBody: {
    flex: 1,
    marginLeft: scale(8),
    paddingBottom: scale(13),
    ...themeLayout.borderSide(),
  },
  avatar: {
    width: scale(50),
    height: scale(50),
    borderRadius: scale(25),
    backgroundColor: themeColor.bgF4,
  },
  replyCon: {
    ...themeLayout.padding(scale(5), scale(8)),
    marginTop: scale(6),
    backgroundColor: themeColor.bgF4,
    borderRadius: scale(2),
  },
  replyText: {
    marginTop: scale(3),
  },
  replyTitle: {
    color: '#6a83a2',
  },
  bottomBar: {
    ...themeLayout.flex('row', 'space-between'),
    marginTop: scale(6),
  },
  superSmallText: {
    fontSize: scale(11),
  },
  textLineHeight: num => ({
    lineHeight: scale(num),
  }),
});
