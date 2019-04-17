import React from 'react';
import { View, Text } from 'react-native';

export default class Recommend extends React.Component {
  render() {
    console.log('Recommend render');
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>Recommend Screen</Text>
      </View>
    );
  }
}
