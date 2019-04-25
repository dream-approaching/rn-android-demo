import React from 'react';
import { ScrollView } from 'react-native-gesture-handler';

export default class extends React.PureComponent {
  render() {
    const { style, children, ...rest } = this.props;
    return (
      <ScrollView style={{ flex: 1, ...style }} showsVerticalScrollIndicator={false} {...rest}>
        {children}
      </ScrollView>
    );
  }
}
