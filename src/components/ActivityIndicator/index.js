import React from 'react';
import { ActivityIndicator } from 'react-native';

export default class extends React.PureComponent {
  render() {
    const { ...rest } = this.props;
    return <ActivityIndicator color='#707070' size={23} {...rest} />;
  }
}
