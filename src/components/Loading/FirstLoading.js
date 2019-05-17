import React from 'react';
import { View } from 'react-native';
import { themeLayout } from '@/config';
import ActivityIndicator from '@/components/ActivityIndicator';

export default class FirstLoading extends React.PureComponent {
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
