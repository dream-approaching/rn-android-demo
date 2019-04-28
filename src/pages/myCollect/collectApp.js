import React from 'react';
import { View, StyleSheet } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import CommonText from '@/components/AppText/CommonText';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  render() {
    return (
      <View style={styles.container}>
        <Header title='收藏应用' />
        <SpringScrollView>
          <CommonText>收藏应用</CommonText>
        </SpringScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});
