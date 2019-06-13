import React from 'react';
import { View, StyleSheet, Text, TouchableWithoutFeedback } from 'react-native';
import { themeLayout, themeCatColor } from '@/config';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import SecondaryText from '@/components/AppText/Cat/SecondaryText';
import CommonText from '@/components/AppText/Cat/CommonText';
import SmallText from '@/components/AppText/Cat/SmallText';
import LikeBtn from '@/components/Comment/Cat/likeBtn';
import { OpenActivity } from '@/components/NativeModules';
import { LIKE_TYPE } from '@/config/constants';
import ImageWithDefault from '@/components/ImageWithDefault';

export default class extends React.PureComponent {
  gotoPersonPage = mobilephone => {
    OpenActivity.openUserIndex(mobilephone);
  };

  render() {
    const { itemData, replyAction, seeAllChildAction, index } = this.props;

    return (
      <View style={styles.container}>
        <TouchableNativeFeedback onPress={() => this.gotoPersonPage(itemData.mobilephone)}>
          <ImageWithDefault style={styles.avatar} source={{ uri: itemData.head_image }} />
        </TouchableNativeFeedback>
        <View style={styles.rightBody}>
          <View style={styles.headerBar}>
            <SecondaryText>{itemData.commit_user}</SecondaryText>
            <LikeBtn
              type={LIKE_TYPE.comment}
              itemData={itemData}
              size={13}
              textStyle={styles.superSmallText}
            />
          </View>
          <TouchableNativeFeedback tapArea={1} onPress={() => replyAction(itemData)}>
            <CommonText style={[styles.replyText, styles.textLineHeight(20)]}>
              {itemData.content}
            </CommonText>
          </TouchableNativeFeedback>
          {!!itemData.detailtwo.length && (
            <View style={styles.replyCon}>
              {itemData.detailtwo.map(item => {
                const pathArr = item.path.split('-');
                const isThirdLevel = pathArr.length >= 3;
                return (
                  <TouchableWithoutFeedback key={item.id} onPress={() => replyAction(item)}>
                    <View style={{ marginTop: 3 }}>
                      <Text style={[styles.textLineHeight(18), { flexWrap: 'wrap' }]}>
                        <TouchableWithoutFeedback
                          onPress={() => this.gotoPersonPage(item.mobilephone)}
                        >
                          <SecondaryText style={[styles.replyTitle]}>
                            {item.commit_user}
                          </SecondaryText>
                        </TouchableWithoutFeedback>
                        {isThirdLevel && (
                          <SecondaryText style={styles.replayText}>
                            回复
                            <TouchableWithoutFeedback
                              onPress={() => this.gotoPersonPage(item.pidinfo.mobilephone)}
                            >
                              <SecondaryText style={[styles.replyTitle]}>
                                {item.pidinfo.nick_name}
                              </SecondaryText>
                            </TouchableWithoutFeedback>
                          </SecondaryText>
                        )}
                        <TouchableWithoutFeedback onPress={() => replyAction(item)}>
                          <SecondaryText>：{item.content}</SecondaryText>
                        </TouchableWithoutFeedback>
                      </Text>
                    </View>
                  </TouchableWithoutFeedback>
                );
              })}
              {+itemData.count > 2 && (
                <TouchableNativeFeedback
                  tapArea={1}
                  onPress={() => seeAllChildAction(itemData, index)}
                >
                  <View style={{ marginTop: 3 }}>
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
          </View>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    ...themeLayout.padding(0, 16),
    ...themeLayout.flex('row', 'center', 'flex-start'),
    ...themeLayout.margin(16, 0, 0),
  },
  rightBody: {
    flex: 1,
    marginLeft: 8,
    paddingBottom: 13,
    ...themeLayout.borderSide(),
  },
  avatar: {
    width: 46,
    height: 46,
    borderRadius: 25,
    backgroundColor: themeCatColor.bgF4,
  },
  replyCon: {
    ...themeLayout.padding(5, 8),
    marginTop: 6,
    backgroundColor: themeCatColor.bgF4,
    borderRadius: 2,
  },
  replyText: {
    marginTop: 3,
  },
  replyTitle: {
    color: '#6a83a2',
  },
  bottomBar: {
    ...themeLayout.flex('row', 'space-between'),
    marginTop: 6,
  },
  headerBar: {
    ...themeLayout.flex('row', 'space-between'),
  },
  superSmallText: {
    fontSize: 11,
  },
  textLineHeight: num => ({
    lineHeight: num,
  }),
});
