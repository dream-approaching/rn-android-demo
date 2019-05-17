import React from 'react';
import { View } from 'react-native';
import { themeLayout } from '@/config';
import ActivityIndicator from '@/components/ActivityIndicator';

export default class Loading extends React.PureComponent {
  render() {
    return (
      <View style={{ position: 'absolute', width: '100%', top: 80, ...themeLayout.flex() }}>
        <ActivityIndicator />
      </View>
    );
  }
}
