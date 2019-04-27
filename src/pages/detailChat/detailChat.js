import React from 'react';
import { View, Text } from 'react-native';

export default class DetailChat extends React.Component {
  static navigationOptions = {
    header: null,
  };

  render() {
    console.log('DetailChat render');
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>DetailChat Screen</Text>
      </View>
    );
  }
}
