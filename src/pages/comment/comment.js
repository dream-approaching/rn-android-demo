import React from 'react';
import { View, StyleSheet } from 'react-native';
import CommentItem from '@/components/CommentItem';
import CommentInput from '@/components/CommentInput';
import { FlatList } from 'react-native-gesture-handler';
import { commentData } from '@/config/fakeData';
import PullRefreshView from '@/components/PullRefreshView';
import { scale } from '@/config';
import Header from './components/header';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  onPullRelease = resolve => {
    setTimeout(() => {
      resolve();
    }, 3000);
  };

  render() {
    return (
      <View style={styles.container}>
        <Header />
        <PullRefreshView onPullRelease={this.onPullRelease}>
          <FlatList
            keyExtractor={item => `${item.id}`}
            data={commentData}
            renderItem={({ item }) => <CommentItem itemData={item} />}
          />
        </PullRefreshView>
        <CommentInput />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: scale(18),
  },
});
