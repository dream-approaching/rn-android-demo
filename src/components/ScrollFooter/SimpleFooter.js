import React from 'react';
import { View, StyleSheet } from 'react-native';
import { LoadingFooter } from 'react-native-spring-scrollview/LoadingFooter';
import { themeLayout } from '@/config';
import ActivityIndicator from '@/components/ActivityIndicator';
import { connect } from '@/utils/dva';
import SecondaryText from '@/components/AppText/Cat/SecondaryText';

class SimpleFooter extends LoadingFooter {
  static height = 50;

  render() {
    const { status } = this.state;
    const { canShowLoading } = this.props;
    return (
      <View style={styles.container}>
        {status !== 'allLoaded' && canShowLoading && <ActivityIndicator />}
        {status === 'allLoaded' && (
          <View style={styles.rContainer}>
            <SecondaryText>已经到底啦</SecondaryText>
          </View>
        )}
      </View>
    );
  }
}

const mapStateToProps = ({ global }) => ({ canShowLoading: global.canShowLoading });

function mergeProps(stateProps, dispatchProps, ownProps) {
  return Object.assign({}, ownProps, stateProps, dispatchProps);
}
export default connect(
  mapStateToProps,
  {},
  mergeProps,
  { forwardRef: true }
)(SimpleFooter);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    flexDirection: 'row',
  },
  rContainer: {
    marginTop: 10,
    ...themeLayout.flex(),
  },
});
