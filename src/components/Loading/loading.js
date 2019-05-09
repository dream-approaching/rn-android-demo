import React from 'react';
import { View, ActivityIndicator } from 'react-native';

export default class Loading extends React.PureComponent {
  render() {
    return (
      <View style={{ marginTop: 30 }}>
        <ActivityIndicator />
      </View>
    );
  }
}
