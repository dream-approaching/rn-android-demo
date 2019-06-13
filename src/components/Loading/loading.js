import React from 'react';
import { View, StyleSheet } from 'react-native';
import { themeLayout, themeCatSize } from '@/config';
import ActivityIndicator from '@/components/ActivityIndicator';

export default class Loading extends React.PureComponent {
  render() {
    return (
      <View style={styles.container}>
        <ActivityIndicator style={{ marginTop: -350 }} />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    width: themeCatSize.screenWidth,
    height: themeCatSize.screenHeight,
    ...themeLayout.flex(),
    backgroundColor: 'rgba(0,0,0,0.2)',
    zIndex: 10000000,
  },
});
