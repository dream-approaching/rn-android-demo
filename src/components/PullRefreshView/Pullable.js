/* eslint-disable */
import React, { Component } from 'react';
import {
  View,
  PanResponder,
  Animated,
  Easing,
  ActivityIndicator,
  StyleSheet,
  Text,
} from 'react-native';

// const padding = 2; //scrollview与外面容器的距离
const pullOkMargin = 100; // 下拉到ok状态时topindicator距离顶部的距离
const defaultDuration = 300;
const defaultTopIndicatorHeight = 60; // 顶部刷新指示器的高度
const defaultFlag = { pulling: false, pullok: false, pullrelease: false };
const flagPulling = { pulling: true, pullok: false, pullrelease: false };
const flagPullok = { pulling: false, pullok: true, pullrelease: false };
const flagPullrelease = { pulling: false, pullok: false, pullrelease: true };
const isDownGesture = (x, y) => {
  return y > 0 && y > Math.abs(x);
};
const isUpGesture = (x, y) => {
  return y < 0 && Math.abs(x) < Math.abs(y);
};
const isVerticalGesture = (x, y) => {
  return Math.abs(x) < Math.abs(y);
};

export default class extends Component {
  constructor(props) {
    super(props);
    this.pullable = this.props.refreshControl == null;
    this.defaultScrollEnabled = false; // !(this.props.onPulling || this.props.onPullOk || this.props.onPullRelease); //定义onPull***属性时scrollEnabled为false
    this.topIndicatorHeight = this.props.topIndicatorHeight
      ? this.props.topIndicatorHeight
      : defaultTopIndicatorHeight;
    this.defaultXY = { x: 0, y: this.topIndicatorHeight * -1 };
    this.pullOkMargin = this.props.pullOkMargin ? this.props.pullOkMargin : pullOkMargin;
    this.duration = this.props.duration ? this.props.duration : defaultDuration;
    this.state = Object.assign({}, props, {
      pullPan: new Animated.ValueXY(this.defaultXY),
      scrollEnabled: this.defaultScrollEnabled,
      flag: defaultFlag,
      height: 0,
    });
    this.gesturePosition = { x: 0, y: 0 };
    this.panResponder = PanResponder.create({
      onStartShouldSetPanResponder: this.onShouldSetPanResponder,
      onMoveShouldSetPanResponder: this.onShouldSetPanResponder,
      onPanResponderGrant: () => {},
      onPanResponderMove: this.onPanResponderMove,
      onPanResponderRelease: this.onPanResponderRelease,
      onPanResponderTerminate: this.onPanResponderRelease,
    });
    this.setFlag(defaultFlag);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.isRefreshing !== this.props.isRefreshing && !nextProps.isRefreshing) {
      this.resetDefaultXYHandler();
    }
  }

  onShouldSetPanResponder = (e, gesture) => {
    if (!this.pullable || !isVerticalGesture(gesture.dx, gesture.dy)) {
      // 不使用pullable,或非向上 或向下手势不响应
      return false;
    }
    if (!this.state.scrollEnabled) {
      this.lastY = this.state.pullPan.y._value;
      return true;
    } else {
      return false;
    }
  };

  onPanResponderMove = (e, gesture) => {
    this.gesturePosition = { x: this.defaultXY.x, y: gesture.dy };
    if (isUpGesture(gesture.dx, gesture.dy)) {
      // 向上滑动
      if (this.isPullState()) {
        this.resetDefaultXYHandler();
      } else if (this.props.onPushing && this.props.onPushing(this.gesturePosition)) {
        // do nothing, handling by this.props.onPushing
      } else if (this.listHeight > this.height) {
        this.scroll.scrollToOffset({ animated: true, offset: gesture.dy * -1 });
      }
    } else if (isDownGesture(gesture.dx, gesture.dy)) {
      // 下拉
      this.state.pullPan.setValue({ x: this.defaultXY.x, y: this.lastY + gesture.dy / 2 });
      if (gesture.dy < this.topIndicatorHeight + this.pullOkMargin) {
        // 正在下拉
        if (!this.flag.pulling) {
          this.props.onPulling && this.props.onPulling();
        }
        this.setFlag(flagPulling);
      } else {
        // 下拉到位
        if (!this.state.pullok) {
          this.props.onPullOk && this.props.onPullOk();
        }
        this.setFlag(flagPullok);
      }
    }
  };

  onPanResponderRelease = (e, gesture) => {
    if (this.flag.pulling) {
      // 没有下拉到位 重置状态
      this.resetDefaultXYHandler();
    }
    if (this.flag.pullok) {
      if (!this.flag.pullrelease) {
        if (this.props.onPullRelease) {
          this.props.onPullRelease();
        } else {
          setTimeout(() => {
            this.resetDefaultXYHandler();
          }, 3000);
        }
      }
      this.setFlag(flagPullrelease); // 完成下拉，已松开
      Animated.timing(this.state.pullPan, {
        toValue: { x: 0, y: 0 },
        easing: Easing.linear,
        duration: this.duration,
      }).start();
    }
  };

  onScroll = e => {
    const { scrollEnabled } = this.state;
    if (e.nativeEvent.contentOffset.y <= 0) {
      scrollEnabled && this.setState({ scrollEnabled: this.defaultScrollEnabled });
    } else if (!this.isPullState()) {
      !scrollEnabled && this.setState({ scrollEnabled: true });
    }
    // !scrollEnabled && this.setState({ scrollEnabled: true });
  };

  setFlag = flag => {
    if (this.flag === flag) return false;
    this.flag = flag;
    this.renderTopIndicator();
  };

  isPullState = () => this.flag.pulling || this.flag.pullok || this.flag.pullrelease;

  resetDefaultXYHandler = () => {
    this.flag = defaultFlag;
    this.state.pullPan.setValue(this.defaultXY);
  };

  onLayout = e => {
    const { width, height } = this.state;
    const { layout } = e.nativeEvent;
    if (width !== layout.width || height !== layout.height) {
      this.scrollContainer.setNativeProps({
        style: { width: layout.width, height: layout.height },
      });
      this.width = layout.width;
      this.height = layout.height;
    }
  };

  renderTopIndicator = () => {
    const { pulling, pullok, pullrelease } = this.flag;
    const { topIndicatorRender } = this.props;
    if (topIndicatorRender == null) {
      return this.defaultTopIndicatorRender(pulling, pullok, pullrelease, this.gesturePosition);
    } else {
      return topIndicatorRender(pulling, pullok, pullrelease, this.gesturePosition);
    }
  };

  /**
   使用setNativeProps解决卡顿问题
   make changes directly to a component without using state/props to trigger a re-render of the entire subtree
   */
  defaultTopIndicatorRender = (pulling, pullok, pullrelease) => {
    const hide = { position: 'absolute', left: 10000 };
    const show = { position: 'relative', left: 0 };

    const switchText = status => {
      const { refPulling, refPullok, refPullrelease } = this;
      refPulling && refPulling.setNativeProps({ style: status === 'pulling' ? show : hide });
      refPullok && refPullok.setNativeProps({ style: status === 'pullok' ? show : hide });
      refPullrelease &&
        refPullrelease.setNativeProps({ style: status === 'pullrelease' ? show : hide });
    };
    setTimeout(() => {
      if (this.isPullState()) {
        if (pulling) {
          switchText('pulling');
        } else if (pullok) {
          switchText('pullok');
        } else if (pullrelease) {
          switchText('pullrelease');
        }
      } else {
        switchText('pulling');
      }
    }, 1);
    return (
      <View style={styles.indicatorView}>
        <ActivityIndicator size="small" color="red" />
        <View ref={c => (this.refPulling = c)}>
          <Text style={styles.indicatorText}>下拉刷新</Text>
        </View>
        <View ref={c => (this.refPullok = c)}>
          <Text style={styles.indicatorText}>松开刷新</Text>
        </View>
        <View ref={c => (this.refPullrelease = c)}>
          <Text style={styles.indicatorText}>刷新中</Text>
        </View>
      </View>
    );
  };

  render() {
    const { refreshControl } = this.props;
    return (
      <View style={[styles.wrap, this.props.style]} onLayout={this.onLayout}>
        <Animated.View ref={c => (this.ani = c)} style={[this.state.pullPan.getLayout()]}>
          {this.renderTopIndicator()}
          <View
            ref={c => {
              this.scrollContainer = c;
            }}
            {...this.panResponder.panHandlers}
            style={{ width: this.state.width, height: this.state.height }}
          >
            {this.getScrollable(refreshControl)}
          </View>
        </Animated.View>
      </View>
    );
  }
}
const styles = StyleSheet.create({
  wrap: {
    flex: 1,
    flexGrow: 1,
    flexDirection: 'column',
    zIndex: -999,
  },
  hide: {
    position: 'absolute',
    left: 10000,
  },
  show: {
    position: 'relative',
    left: 0,
  },
  indicatorView: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    height: 60,
  },
  indicatorText: { color: '#333' },
});
