import React from 'react';
import { View, Text } from 'react-native';

export default class DetailWebview extends React.Component {
  render() {
    console.log('DetailWebview render');
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>DetailWebview Screen</Text>
      </View>
    );
  }
}
