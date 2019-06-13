import React from 'react';
import { View, StyleSheet } from 'react-native';
import { themeLayout } from '@/config';
import ActivityIndicator from '@/components/ActivityIndicator';
import SecondaryText from '@/components/AppText/Cat/SecondaryText';

export default class FooterLoading extends React.PureComponent {
  render() {
    const { allLoaded, bgColor = 'transparent' } = this.props;
    return (
      <View style={[styles.footerCon, { backgroundColor: bgColor }]}>
        {!allLoaded && <ActivityIndicator />}
        <SecondaryText style={styles.footerText}>
          {(allLoaded && '没有更多内容了') || '努力加载中...'}
        </SecondaryText>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  loadingCon: { position: 'absolute', width: '100%', top: 80, ...themeLayout.flex() },
  footerCon: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    paddingVertical: 10,
  },
  footerText: { marginLeft: 20, fontSize: 12, color: '#666' },
});
