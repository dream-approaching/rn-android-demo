import React, { Fragment } from 'react';
import { View, StyleSheet } from 'react-native';
import { themeLayout, themeCatSize } from '@/config';
import ActivityIndicator from '@/components/ActivityIndicator';

export default class LoadingUpContent extends React.PureComponent {
  render() {
    const { loading, children } = this.props;
    return (
      <Fragment>
        {loading && (
          <View style={styles.loadingCon}>
            <ActivityIndicator />
          </View>
        )}
        {children}
      </Fragment>
    );
  }
}

const styles = StyleSheet.create({
  loadingCon: {
    position: 'absolute',
    width: themeCatSize.screenWidth,
    top: 80,
    left: 0,
    ...themeLayout.flex(),
  },
});
