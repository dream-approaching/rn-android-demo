import React from 'react';
import PullView from './PullView';
import { scale } from '@/config';

export default class extends React.Component {
  render() {
    const { onPullRelease, children, ...rest } = this.props;
    return (
      <PullView onPullRelease={onPullRelease} topIndicatorHeight={scale(60)} {...rest}>
        {children}
      </PullView>
    );
  }
}
