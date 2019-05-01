import React from 'react';
import { View, Image, StyleSheet, Text } from 'react-native';
import { scale, themeLayout, themeColor } from '@/config';
import { TouchableOpacity } from 'react-native-gesture-handler';
import SecondaryText from '@/components/AppText/SecondaryText';
import CommonText from '@/components/AppText/CommonText';
import SmallText from '@/components/AppText/SmallText';

export default class extends React.PureComponent {
  render() {
    const { itemData, replyAction } = this.props;
    return (
      <View style={styles.container}>
        <TouchableOpacity>
          <Image style={styles.avatar} source={{ uri: itemData.head_image }} />
        </TouchableOpacity>
        <View style={styles.rightBody}>
          <SecondaryText>{itemData.commit_user}</SecondaryText>
          <TouchableOpacity onPress={() => replyAction(itemData)}>
            <CommonText style={[styles.replyText, styles.textLineHeight(20)]}>
              {itemData.content}
            </CommonText>
          </TouchableOpacity>
          {!!itemData.detailtwo.length && (
            <View style={styles.replyCon}>
              {itemData.detailtwo.map(item => {
                return (
                  <TouchableOpacity
                    key={item.id}
                    style={{ marginTop: scale(3) }}
                    onPress={() => replyAction(item)}
                  >
                    <Text style={[styles.textLineHeight(18), { flexWrap: 'wrap' }]}>
                      <SecondaryText style={[styles.replyTitle]}>
                        {item.commit_user}：
                      </SecondaryText>
                      <SecondaryText>{item.content}</SecondaryText>
                    </Text>
                  </TouchableOpacity>
                );
              })}
              {+itemData.count > 2 && (
                <TouchableOpacity style={{ marginTop: scale(3) }}>
                  <SecondaryText style={[styles.replyTitle, styles.textLineHeight(18)]}>
                    {`共${itemData.count}条回复＞`}
                  </SecondaryText>
                </TouchableOpacity>
              )}
            </View>
          )}
          <View style={styles.bottomBar}>
            <SmallText>{itemData.created_time}</SmallText>
            <TouchableOpacity style={styles.likeCon}>
              <Image style={styles.likeIcon} source={{ uri: 'ic_like' }} />
              <SmallText style={styles.superSmallText}>{itemData.fabulous}</SmallText>
            </TouchableOpacity>
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
  likeCon: {
    ...themeLayout.flex('row', 'space-between'),
  },
  likeIcon: {
    width: scale(12),
    height: scale(12),
  },
  superSmallText: {
    fontSize: scale(11),
    marginLeft: scale(3),
  },
  textLineHeight: num => ({
    lineHeight: scale(num),
  }),
});
