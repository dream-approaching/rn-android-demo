import React from 'react';
import { Text } from 'react-native';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  render() {
    console.log('empty render', 'render');
    return <Text style={{ color: 'transparent' }}>empty</Text>;
  }
}
