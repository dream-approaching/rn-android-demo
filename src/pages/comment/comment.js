import React from 'react';
import { View, StyleSheet } from 'react-native';
import CommentItem from '@/components/CommentItem';
import CommentInput from '@/components/CommentInput';
// import { FlatList } from 'react-native-gesture-handler';
import { commentData2 } from '@/config/fakeData';
import { LargeList } from 'react-native-largelist-v3';
import Header from './components/header';
import { scale } from '@/config';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  renderIndexPath = ({ section, row }) => {
    return <CommentItem itemData={commentData2[section].items[row]} />;
  };

  render() {
    return (
      <View style={styles.container}>
        <Header />
        <LargeList
          heightForIndexPath={() => scale(50)}
          data={commentData2}
          renderIndexPath={this.renderIndexPath}
        />
        <CommentInput />
      </View>
    );
  }
}
// ({ item }) => <CommentItem itemData={item} />

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: scale(18),
  },
});
