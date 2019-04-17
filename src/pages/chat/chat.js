import React from 'react';
import { View, Text } from 'react-native';

export default class Chat extends React.Component {
  render() {
    console.log('Chat render');
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>Chat Screen</Text>
      </View>
    );
  }
}
