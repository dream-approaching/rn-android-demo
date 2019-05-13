import React from 'react';
import { View, ActivityIndicator } from 'react-native';
import { themeLayout } from '@/config';

export default class Loading extends React.PureComponent {
  render() {
    return (
      <View style={{ position: 'absolute', width: '100%', top: 80, ...themeLayout.flex() }}>
        <ActivityIndicator />
      </View>
    );
  }
}
