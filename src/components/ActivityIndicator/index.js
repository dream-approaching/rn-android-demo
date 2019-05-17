import React from 'react';
import { ActivityIndicator } from 'react-native';
import { scale } from '@/config';

export default class extends React.PureComponent {
  render() {
    const { ...rest } = this.props;
    return <ActivityIndicator color='#707070' size={scale(23)} {...rest} />;
  }
}
