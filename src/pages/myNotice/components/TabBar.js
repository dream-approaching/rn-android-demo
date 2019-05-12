/* eslint-disable */

import { themeColor, scale, themeLayout } from '@/config';

const React = require('react');
const { ViewPropTypes } = (ReactNative = require('react-native'));
const PropTypes = require('prop-types');
const createReactClass = require('create-react-class');

const { StyleSheet, Text, View, Animated } = ReactNative;
const Button = require('react-native-scrollable-tab-view/Button');

const DefaultTabBar = createReactClass({
  propTypes: {
    goToPage: PropTypes.func,
    activeTab: PropTypes.number,
    tabs: PropTypes.array,
    backgroundColor: PropTypes.string,
    activeTextColor: PropTypes.string,
    inactiveTextColor: PropTypes.string,
    textStyle: Text.propTypes.style,
    tabStyle: ViewPropTypes.style,
    renderTab: PropTypes.func,
    underlineStyle: ViewPropTypes.style,
  },

  getDefaultProps() {
    return {
      activeTextColor: themeColor.font.black,
      inactiveTextColor: themeColor.font.black,
      backgroundColor: null,
    };
  },

  renderTab(name, page, isTabActive, onPressHandler, showDot) {
    const { activeTextColor, inactiveTextColor } = this.props;
    const textColor = isTabActive ? activeTextColor : inactiveTextColor;

    return (
      <Button
        style={{ flex: 1 }}
        key={name}
        accessible
        accessibilityLabel={name}
        accessibilityTraits="button"
        onPress={() => onPressHandler(page)}
      >
        <View style={[styles.tab, this.props.tabStyle]}>
          <Text style={[{ color: textColor }, styles.textStyle]}>{name}</Text>
          {showDot && <View style={styles.dot} />}
        </View>
      </Button>
    );
  },

  render() {
    const { containerWidth } = this.props;
    const numberOfTabs = this.props.tabs.length;
    const tabUnderlineStyle = {
      position: 'absolute',
      width: containerWidth / numberOfTabs / 3,
      left: containerWidth / numberOfTabs / 3,
      bottom: 1,
      height: scale(2),
      backgroundColor: themeColor.primaryColor,
      borderRadius: scale(2),
    };

    const translateX = this.props.scrollValue.interpolate({
      inputRange: [0, 1],
      outputRange: [0, containerWidth / numberOfTabs],
    });
    return (
      <View
        style={[styles.tabs, { backgroundColor: this.props.backgroundColor }, this.props.style]}
      >
        {this.props.tabs.map((name, page) => {
          const isTabActive = this.props.activeTab === page;
          const renderTab = this.props.renderTab || this.renderTab;
          return renderTab(name, page, isTabActive, this.props.goToPage);
        })}
        <Animated.View
          style={[
            tabUnderlineStyle,
            {
              transform: [{ translateX }],
            },
            this.props.underlineStyle,
          ]}
        />
      </View>
    );
  },
});

const styles = StyleSheet.create({
  tab: {
    flex: 1,
    ...themeLayout.flex('row'),
  },
  tabs: {
    height: scale(40),
    flexDirection: 'row',
    justifyContent: 'space-around',
    ...themeLayout.border(),
  },
  textStyle: {
    fontSize: scale(16),
  },
  dot: {
    backgroundColor: '#ff5c69',
    width: scale(6),
    height: scale(6),
    borderRadius: scale(3),
    marginLeft: scale(4),
    marginRight: -scale(6),
  },
});

module.exports = DefaultTabBar;
