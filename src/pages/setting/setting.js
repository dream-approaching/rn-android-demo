import React from 'react';
import { View, Text } from 'react-native';

export default class Setting extends React.Component {
  render() {
    console.log('Setting render');
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>Setting Screen</Text>
      </View>
    );
  }
}
