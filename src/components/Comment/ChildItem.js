import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { scale, themeLayout, themeColor } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import CommonText from '@/components/AppText/CommonText';
import SmallText from '@/components/AppText/SmallText';
import moment from '@/components/moment';
import LikeBtn from '@/components/Comment/likeBtn';
import { OpenRnActivity } from '@/components/NativeModules';

export default class Chat extends React.Component {
  gotoPersonPage = () => {
    const { itemData } = this.props;
    console.log('%citemData:', 'color: #0e93e0;background: #aaefe5;', itemData);
    OpenRnActivity('myShare', JSON.stringify({ phone: itemData.mobilephone }));
  };

  render() {
    const { itemData, replyAction, type } = this.props;
    const isMain = type === 'main';
    return (
      <View style={[styles.mainComment, { backgroundColor: isMain ? '#fff' : 'transparent' }]}>
        <TouchableOpacity onPress={this.gotoPersonPage}>
          <Image style={styles.avatar(isMain)} source={{ uri: itemData.head_image }} />
        </TouchableOpacity>
        <View style={styles.rightBody(isMain)}>
          <View style={styles.userBar}>
            <SecondaryText style={{ color: isMain ? '#303030' : '#707070' }}>
              {itemData.commit_user}
            </SecondaryText>
            <LikeBtn size={isMain ? 15 : 13} likeNum={itemData.fabulous} />
          </View>
          <SmallText>{moment(itemData.created_time * 1000).fromNow(true)}</SmallText>
          <TouchableOpacity onPress={() => replyAction(itemData)}>
            <CommonText style={[styles.replyText, styles.textLineHeight(20)]}>
              {itemData.content}
            </CommonText>
          </TouchableOpacity>
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
