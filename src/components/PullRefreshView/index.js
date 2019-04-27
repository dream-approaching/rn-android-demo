import React from 'react';
import { View, StyleSheet, ActivityIndicator, Text } from 'react-native';
import { PullView } from 'react-native-pull';
import { scale } from '@/config';

export default class extends React.Component {
  componentWillUnmount() {
    this.loadingTimer && clearTimeout(this.loadingTimer);
  }

  onPullRelease = resolve => {
    setTimeout(() => {
      resolve();
    }, 3000);
  };

  topIndicatorRender = (pulling, pullok, pullrelease) => {
    const hide = { position: 'absolute', left: 10000 };
    const show = { position: 'relative', left: 0 };

    const switchText = status => {
      const { refPulling, refPullok, refPullrelease } = this;
      refPulling && refPulling.setNativeProps({ style: status === 'pulling' ? show : hide });
      refPullok && refPullok.setNativeProps({ style: status === 'pullok' ? show : hide });
      refPullrelease &&
        refPullrelease.setNativeProps({ style: status === 'pullrelease' ? show : hide });
    };

    this.loadingTimer = setTimeout(() => {
      if (pulling) {
        switchText('pulling');
      } else if (pullok) {
        switchText('pullok');
      } else if (pullrelease) {
        switchText('pullrelease');
      }
    }, 1);

    return (
      <View style={styles.indicatorView}>
        <ActivityIndicator size='small' color='gray' />
        <Text style={styles.indicatorText} ref={c => (this.refPulling = c)}>
          下拉刷新
        </Text>
        <Text style={styles.indicatorText} ref={c => (this.refPullok = c)}>
          松开刷新
        </Text>
        <Text style={styles.indicatorText} ref={c => (this.refPullrelease = c)}>
          玩命刷新中
        </Text>
      </View>
    );
  };

  render() {
    const { onPullRelease, children, ...rest } = this.props;
    return (
      <PullView
        onPullRelease={onPullRelease}
        topIndicatorRender={this.topIndicatorRender}
        topIndicatorHeight={scale(60)}
        {...rest}
      >
        {children}
      </PullView>
    );
  }
}

const styles = StyleSheet.create({
  indicatorView: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    height: 60,
  },
  indicatorText: { color: '#333' },
});
