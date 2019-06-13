import React from 'react';
import { View, StyleSheet } from 'react-native';
import { themeLayout } from '@/config';
import ActivityIndicator from '@/components/ActivityIndicator';

export default class FirstLoading extends React.PureComponent {
  render() {
    const { loading, children } = this.props;
    if (loading) {
      return (
        <View style={styles.loadingCon}>
          <ActivityIndicator />
        </View>
      );
    }
    return children;
  }
}

const styles = StyleSheet.create({
  loadingCon: { position: 'absolute', width: '100%', top: 80, ...themeLayout.flex() },
});
