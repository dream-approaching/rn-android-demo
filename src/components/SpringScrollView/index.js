import React from 'react';
import { SpringScrollView } from 'react-native-spring-scrollview';

export default class extends React.PureComponent {
  render() {
    const { children, ...rest } = this.props;
    return (
      <SpringScrollView bounces={false} showsVerticalScrollIndicator={false} {...rest}>
        {children}
      </SpringScrollView>
    );
  }
}
