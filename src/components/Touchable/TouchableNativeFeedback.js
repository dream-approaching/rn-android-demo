import React from 'react';
import { TouchableNativeFeedback } from 'react-native';
import { debounce2 } from '@/utils/utils';

export default class extends React.PureComponent {
  render() {
    const {
      children,
      color = '#f2f2f2',
      notOut = false,
      tapArea = 30,
      onPress,
      ...rest
    } = this.props;
    return (
      <TouchableNativeFeedback
        onPress={debounce2(onPress, 300)}
        hitSlop={{ top: tapArea, left: tapArea, bottom: tapArea, right: tapArea }}
        background={TouchableNativeFeedback.Ripple(color, !notOut)}
        {...rest}
      >
        {children}
      </TouchableNativeFeedback>
    );
  }
}
