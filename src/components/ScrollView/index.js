import React from 'react';
import { ScrollView } from 'react-native-gesture-handler';

export default class extends React.PureComponent {
  render() {
    const { style, children } = this.props;
    return (
      <ScrollView style={{ flex: 1, ...style }} showsVerticalScrollIndicator={false}>
        {children}
      </ScrollView>
    );
  }
}
