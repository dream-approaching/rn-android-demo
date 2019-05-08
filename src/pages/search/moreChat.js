import React from 'react';
import { View, StyleSheet } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import { articleData } from '@/config/fakeData';
import ArticleItem from '@/components/pageComponent/articleItem';
import { themeLayout, scale } from '@/config';
import { connect } from '@/utils/dva';

class MoreChat extends React.Component {
  static navigationOptions = {
    header: null,
  };

  componentDidMount() {
    this.queryMoreChatDispatch();
  }

  queryMoreChatDispatch = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'search/queryMoreChatEffect',
      payload: 'data',
    });
  };

  render() {
    const { navigation } = this.props;
    return (
      <View style={styles.container}>
        <Header navigation={navigation} title="更多相关互动话题" />
        <SpringScrollView>
          <View style={styles.searchSectionList}>
            {articleData.map(item => (
              <ArticleItem key={item.name} itemData={item} islastOne={false} />
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

export default connect(mapStateToProps)(MoreChat);

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
