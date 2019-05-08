import React from 'react';
import { View, StyleSheet } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import { xfriendData } from '@/config/fakeData';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { themeLayout, scale } from '@/config';
import { connect } from '@/utils/dva';

class MoreShare extends React.Component {
  static navigationOptions = {
    header: null,
  };

  componentDidMount() {
    this.queryMoreShareDispatch();
  }

  queryMoreShareDispatch = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'search/queryMoreShareEffect',
      payload: 'data',
    });
  };

  render() {
    const { navigation } = this.props;
    return (
      <View style={styles.container}>
        <Header navigation={navigation} title="更多相关X友分享" />
        <SpringScrollView>
          <View style={styles.searchSectionList}>
            {xfriendData.map(item => (
              <XfriendItem key={item.name} itemData={item} islastOne={false} />
            ))}
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

export default connect(mapStateToProps)(MoreShare);

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  searchSectionList: {
    ...themeLayout.flex('column'),
    paddingBottom: scale(10),
    paddingTop: scale(5),
    // ...themeLayout.padding(scale(0), scale(20)),
  },
});
