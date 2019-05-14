import React from 'react';
import { View, ActivityIndicator } from 'react-native';
import { themeLayout } from '@/config';

export default class WithLoading extends React.PureComponent {
  render() {
    const { loading, children } = this.props;
    if (loading) {
      return (
        <View style={{ position: 'absolute', width: '100%', top: 80, ...themeLayout.flex() }}>
          <ActivityIndicator />
        </View>
      );
    }
    return children;
  }
}
