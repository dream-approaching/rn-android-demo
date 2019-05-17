import React from 'react';
import { View, StyleSheet } from 'react-native';
import { RefreshHeader } from 'react-native-spring-scrollview/RefreshHeader';
import { scale } from '@/config';
import ActivityIndicator from '@/components/ActivityIndicator';

class SimpleHeader extends RefreshHeader {
  static height = scale(40);

  onStateChange(oldStatus, newStatus) {
    if (oldStatus === 'refreshing' || newStatus === 'refreshing') {
      this.setState({ status: newStatus });
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <ActivityIndicator />
      </View>
    );
  }
}

export default SimpleHeader;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    flexDirection: 'row',
  },
});
