import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import SecondaryText from '@/components/AppText/SecondaryText';
import { scale, themeLayout, themeSize } from '@/config';
import XfriendItem from '@/components/pageComponent/xfriend';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  render() {
    const avatar =
      'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1298508432,4221755458&fm=26&gp=0.jpg';
    const itemData = {
      avatar,
      name: '蜡笔小新',
      time: '1557043585',
      label: ['工具', '笔记'],
      content:
        '推荐即可这款App找到自己人年轻人的同好社区年轻人的人年轻人的同好社区年轻人的人年轻人的同好社区年轻人的',
      app: {
        icon: avatar,
        name: '即刻',
      },
      likeNum: 123,
      commentNum: 234,
      shareNum: 89,
    };
    return (
      <View style={styles.container}>
        <SpringScrollView>
          <View style={styles.shareCon}>
            <Image style={styles.avatar} source={{ uri: avatar }} />
            <SecondaryText>点击这里分享你喜爱的应用吧~ </SecondaryText>
          </View>
          <XfriendItem itemData={itemData} />
        </SpringScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  shareCon: {
    ...themeLayout.flex('row'),
    ...themeLayout.border(scale(1)),
    ...themeLayout.padding(scale(5), 0),
    borderRadius: scale(6),
    width: scale(260),
    alignSelf: 'center',
    elevation: themeSize.minBorder * 2,
    marginTop: scale(16),
  },
  avatar: {
    width: scale(28),
    height: scale(28),
    backgroundColor: 'lightblue',
    borderRadius: scale(14),
    marginRight: scale(10),
  },
});
