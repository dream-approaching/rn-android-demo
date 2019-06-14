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
        <View style={styles.commentCon}>
          <TouchableNativeFeedback onPress={() => this.gotoPersonPage(itemData.mobilephone)}>
            <ImageWithDefault style={styles.avatar} source={{ uri: itemData.head_image }} />
          </TouchableNativeFeedback>
          <View style={styles.rightBody}>
            <View style={styles.headerBar}>
              <View>
                <CommonText>{itemData.commit_user}</CommonText>
                <SmallText>{itemData.timestr}</SmallText>
              </View>
              <LikeBtn
                type={LIKE_TYPE.comment}
                itemData={itemData}
                size={16}
                textStyle={styles.superSmallText}
              />
            </View>
            <TouchableNativeFeedback notOut tapArea={1} onPress={() => replyAction(itemData)}>
              <View>
                <CommonText style={[styles.replyText]}>{itemData.content}</CommonText>
              </View>
            </TouchableNativeFeedback>
          </View>
        </View>
        {!!itemData.detailtwo.length && (
          <View style={styles.replyCon}>
            {itemData.detailtwo.map(item => {
              const pathArr = item.path.split('-');
              const isThirdLevel = pathArr.length >= 3;
              return (
                <TouchableWithoutFeedback key={item.id} onPress={() => replyAction(item)}>
                  <View>
                    <Text style={[styles.textLineHeight(18), { flexWrap: 'wrap' }]}>
                      <TouchableWithoutFeedback
                        onPress={() => this.gotoPersonPage(item.mobilephone)}
                      >
                        <CommonText style={[styles.replyTitle]}>{item.commit_user}</CommonText>
                      </TouchableWithoutFeedback>
                      {isThirdLevel && (
                        <CommonText style={styles.replayText}>
                          回复
                          <TouchableWithoutFeedback
                            onPress={() => this.gotoPersonPage(item.pidinfo.mobilephone)}
                          >
                            <CommonText style={[styles.replyTitle]}>
                              {item.pidinfo.nick_name}
                            </CommonText>
                          </TouchableWithoutFeedback>
                        </CommonText>
                      )}
                      <TouchableWithoutFeedback onPress={() => replyAction(item)}>
                        <CommonText>：{item.content}</CommonText>
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
                <View style={{ marginTop: 5 }}>
                  <SecondaryText style={[styles.replyTitle, styles.textLineHeight(18)]}>
                    {`查看${itemData.count}条评论`}
                    <Text style={{ letterSpacing: -5 }}>＞＞</Text>
                  </SecondaryText>
                </View>
              </TouchableNativeFeedback>
            )}
          </View>
        )}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    ...themeLayout.flex('column', 'flex-start', 'flex-start'),
  },
  commentCon: {
    ...themeLayout.padding(0, 15),
    ...themeLayout.flex('row', 'center', 'flex-start'),
    ...themeLayout.margin(20, 0, 0),
  },
  rightBody: {
    flex: 1,
    marginLeft: 7,
  },
  avatar: {
    width: 30,
    height: 30,
    borderRadius: 25,
    backgroundColor: themeCatColor.bgF4,
  },
  replyCon: {
    ...themeLayout.padding(6, 10),
    ...themeLayout.margin(8, 37.5, 0),
    width: 300,
    backgroundColor: themeCatColor.bgF4,
    borderRadius: 8,
  },
  replyText: {
    marginTop: 5,
    lineHeight: 18,
  },
  replyTitle: {
    color: '#39536C',
  },
  headerBar: {
    ...themeLayout.flex('row', 'space-between'),
    height: 30,
  },
  superSmallText: {
    fontSize: 11,
  },
  textLineHeight: num => ({
    lineHeight: num,
  }),
});
