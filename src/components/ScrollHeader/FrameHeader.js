import React, { Fragment } from 'react';
import { View, StyleSheet, Text, Animated } from 'react-native';
import { RefreshHeader } from 'react-native-spring-scrollview/RefreshHeader';
import { scale } from '@/config';

const frameImg2 = {
  waiting: ['icon_rf_03'],
  pulling: ['icon_rf_02'],
  pullingEnough: ['icon_rf_03'],
  pullingCancel: ['icon_rf_02'],
  refreshing: ['icon_rf_03', 'icon_rf_04', 'icon_rf_05', 'icon_rf_06'],
  rebound: ['icon_rf_07'],
};

let index = 0;
class FrameHeader extends RefreshHeader {
  static height = scale(40);

  componentWillUnmount = () => {
    clearInterval(this.intervalTimer);
  };

  onStateChange = (oldStatus, newStatus) => {
    console.log(
      '%coldStatus, newStatus:',
      'color: #0e93e0;background: #aaefe5;',
      oldStatus,
      newStatus
    );
    if (
      oldStatus === 'refreshing' ||
      oldStatus === 'rebound' ||
      newStatus === 'rebound' ||
      newStatus === 'refreshing'
    ) {
      this.setState({ status: newStatus });
    }
  };

  renderIcon = () => {
    const { status } = this.state;
    if (frameImg2[status].length > 1) {
      this.intervalTimer && clearInterval(this.intervalTimer);
      this.intervalTimer = setInterval(() => {
        index += 1;
        if (+index === frameImg2[status].length) index = 0;
        const arr = [0, 1, 2, 3];
        for (let i = 0, len = arr.length; i < len; i++) {
          const ref = this[`refImg_${arr[i]}`];
          if (arr[i] === index) {
            ref && ref.setNativeProps({ style: { zIndex: 100 } });
          } else {
            ref && ref.setNativeProps({ style: { zIndex: 0 } });
          }
        }
      }, 300);
    } else {
      this.intervalTimer && clearInterval(this.intervalTimer);
    }
    const { maxHeight, offset } = this.props;
    return (
      <Fragment>
        {frameImg2[status].map((item, indexd) => {
          return (
            <Animated.Image
              key={item}
              source={{
                uri: item,
              }}
              resizeMode='contain'
              ref={ref => (this[`refImg_${indexd}`] = ref)}
              style={{
                width: scale(20),
                height: scale(40),
                position: 'absolute',
                left: '48%',
                transform: [
                  {
                    scaleY: offset.interpolate({
                      inputRange: [-maxHeight - 1, -maxHeight, -maxHeight / 2 - 1, -maxHeight / 2],
                      outputRange: [1, 1, 0, 0],
                    }),
                  },
                ],
              }}
            />
          );
        })}
      </Fragment>
    );
  };

  render() {
    const { status } = this.state;
    return (
      <View style={styles.container}>
        {this.renderIcon()}
        {status === 'rebound' && <Text style={styles.text}>刷新完成</Text>}
      </View>
    );
  }
}

export default FrameHeader;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    flexDirection: 'row',
  },
  text: {
    marginVertical: 5,
    fontSize: 15,
    color: '#666',
    textAlign: 'center',
    width: 140,
  },
});
