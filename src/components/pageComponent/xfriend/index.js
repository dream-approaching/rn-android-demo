import React from 'react';
import { Text } from 'react-native';

export default class extends React.PureComponent {
  render() {
    const { itemData } = this.props;
    console.log('%citemData:', 'color: #0e93e0;background: #aaefe5;', itemData);
    return <Text>{itemData.name}</Text>;
  }
}
