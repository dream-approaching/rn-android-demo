import React from 'react';
import { View, StyleSheet } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import { appData } from '@/config/fakeData';
import AppItem from '@/components/pageComponent/appItem';
import { themeLayout, scale } from '@/config';
import { connect } from '@/utils/dva';

class MoreApp extends React.Component {
  static navigationOptions = {
    header: null,
  };

  componentDidMount() {
    this.queryMoreAppDispatch();
  }

  queryMoreAppDispatch = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'search/queryMoreAppEffect',
      payload: 'data',
    });
  };

  render() {
    const { navigation } = this.props;
    return (
      <View style={styles.container}>
        <Header navigation={navigation} title="更多相关App" />
        <SpringScrollView>
          <View style={styles.searchSectionList}>
            {appData.map(item => <AppItem key={item.name} itemData={item} islastOne={false} />)}
          </View>
        </SpringScrollView>
      </View>
    );
  }
}

const mapStateToProps = ({ search, loading }) => ({
  search,
  loading: loading.effects['search/queryAppEffect'],
});

export default connect(mapStateToProps)(MoreApp);

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
