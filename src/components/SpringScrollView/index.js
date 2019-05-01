import React from 'react';
import { SpringScrollView } from 'react-native-spring-scrollview';

export default class extends React.PureComponent {
  endRefresh = () => this.refScrollView.endRefresh();

  endLoading = () => this.refScrollView.endLoading();

  render() {
    const { children, ...rest } = this.props;
    return (
      <SpringScrollView
        ref={ref => (this.refScrollView = ref)}
        bounces={false}
        showsVerticalScrollIndicator={false}
        {...rest}
      >
        {children}
      </SpringScrollView>
    );
  }
}
