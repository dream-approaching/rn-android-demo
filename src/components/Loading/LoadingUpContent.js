import React, { Fragment } from 'react';
import { View } from 'react-native';
import { themeLayout } from '@/config';
import ActivityIndicator from '@/components/ActivityIndicator';

export default class LoadingUpContent extends React.PureComponent {
  render() {
    const { loading, children } = this.props;
    return (
      <Fragment>
        {loading && (
          <View style={{ position: 'absolute', width: '100%', top: 80, ...themeLayout.flex() }}>
            <ActivityIndicator />
          </View>
        )}
        {children}
      </Fragment>
    );
  }
}
