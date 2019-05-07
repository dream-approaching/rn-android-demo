import React from 'react';
import { View, StyleSheet } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import { appData } from '@/config/fakeData';
import AppItem from '@/components/pageComponent/appItem';
import { themeLayout, scale } from '@/config';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  backAction = () => {
    const { navigation } = this.props;
    navigation.pop();
  };

  render() {
    return (
      <View style={styles.container}>
        <Header backAction={this.backAction} title='更多相关App' />
        <SpringScrollView>
          <View style={styles.searchSectionList}>
            {appData.map(item => (
              <AppItem key={item.name} itemData={item} islastOne={false} />
            ))}
          </View>
        </SpringScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  searchSectionList: {
    ...themeLayout.flex('column'),
    paddingBottom: scale(10),
    paddingTop: scale(5),
    ...themeLayout.padding(scale(0), scale(20)),
  },
});
