import React from 'react';
import { TouchableNativeFeedback } from 'react-native';

export default class extends React.PureComponent {
  render() {
    const { children, color = '#f2f2f2', notOut = false, ...rest } = this.props;
    return (
      <TouchableNativeFeedback
        hitSlop={{ top: 20, left: 20, bottom: 20, right: 20 }}
        background={TouchableNativeFeedback.Ripple(color, !notOut)}
        {...rest}
      >
        {children}
      </TouchableNativeFeedback>
    );
  }
}
