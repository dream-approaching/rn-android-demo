import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import SecondaryText from '@/components/AppText/SecondaryText';
import { scale, themeLayout, themeSize } from '@/config';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import AppItem from '@/components/pageComponent/appItem';
import ArticleItem from '@/components/pageComponent/articleItem';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  render() {
    const avatar =
      'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1298508432,4221755458&fm=26&gp=0.jpg';
    const xFriendData = [
      {
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
        approve: false,
      },
      {
        avatar,
        name: '蜡笔小新2',
        time: '1557043585',
        label: ['工具', '笔记'],
        content:
          '推荐即可这款App找到自己人年轻人的同好社区年轻人的人年轻人的同好社区年轻人的人年轻人的同好社区年轻人的',
        app: {
          icon: avatar,
          name: '即刻2',
        },
        likeNum: 1213,
        commentNum: 2134,
        shareNum: 819,
        approve: true,
      },
    ];
    const appData = [
      {
        avatar,
        name: '即刻',
        desc: '好社区年轻人的人年轻人的同好社区年轻',
        label: '竞品',
      },
      {
        avatar,
        name: '即刻2',
        desc: '好社区年12轻人的人年轻人的同好人年轻人的同的同好人年轻人的同好社区年轻',
      },
    ];
    const articleData = [
      {
        avatar,
        name: '即刻即年轻人的同好人年轻人的刻即刻即刻即刻即刻即刻',
      },
      {
        avatar,
        name:
          '好社区年12轻人的人年轻人的同好人年轻人的同的同好人年轻人的同好社区年轻好社区年12轻人的人年轻人的同好人年轻人的同的同好人年轻人的同好社区年轻',
      },
    ];
    return (
      <View style={styles.container}>
        <SpringScrollView>
          <View style={styles.shareCon}>
            <Image style={styles.avatar} source={{ uri: avatar }} />
            <SecondaryText>点击这里分享你喜爱的应用吧~ </SecondaryText>
          </View>
          {xFriendData.map(item => {
            return <XfriendItem key={item.name} itemData={item} />;
          })}
          {appData.map(item => {
            return <AppItem key={item.name} itemData={item} />;
          })}
          {articleData.map(item => {
            return <ArticleItem key={item.name} itemData={item} />;
          })}
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
    marginBottom: scale(10),
  },
  avatar: {
    width: scale(28),
    height: scale(28),
    backgroundColor: 'lightblue',
    borderRadius: scale(14),
    marginRight: scale(10),
  },
});
