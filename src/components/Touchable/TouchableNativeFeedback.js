import React from 'react';
import { TouchableNativeFeedback } from 'react-native';

export default class extends React.PureComponent {
  render() {
    const { children, color = '#f2f2f2', notOut = false, tapArea = 20, ...rest } = this.props;
    return (
      <TouchableNativeFeedback
        hitSlop={{ top: tapArea, left: tapArea, bottom: tapArea, right: tapArea }}
        background={TouchableNativeFeedback.Ripple(color, !notOut)}
        {...rest}
      >
        {children}
      </TouchableNativeFeedback>
    );
  }
}
