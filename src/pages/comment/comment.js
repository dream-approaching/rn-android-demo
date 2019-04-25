import React from 'react';
import { View } from 'react-native';
import CommentItem from '@/components/CommentItem';
import CommentInput from '@/components/CommentInput';
import ScrollView from '@/components/ScrollView';
import Header from './components/header';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  render() {
    const commentData = {
      avatar:
        'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1298508432,4221755458&fm=26&gp=0.jpg',
      name: '夏冬冬21',
      text: '首推【语音】赞到爆，用起来特别的舒服，界面很清爽，感觉非常舒服',
      reply: [
        {
          id: 1,
          name: '夏冬冬21',
          text: '首推【语音】赞到爆，用起来特别的舒服，界面很清爽，感觉非常舒服',
        },
        {
          id: 2,
          name: '夏冬冬21',
          text: '首推【语音】赞到爆，用起来特别的舒服，界面很清爽，感觉非常舒服',
        },
      ],
      replyTotal: 67,
      time: '2019-04-12',
      likeNum: 135,
    };
    return (
      <View style={{ flex: 1 }}>
        <Header />
        <ScrollView keyboardDismissMode='on-drag'>
          <CommentItem itemData={commentData} />
          <CommentItem itemData={commentData} />
          <CommentItem itemData={commentData} />
          <CommentItem itemData={commentData} />
          <CommentItem itemData={commentData} />
        </ScrollView>
        <CommentInput />
      </View>
    );
  }
}
