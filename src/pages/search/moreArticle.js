import React from 'react';
import { View, StyleSheet } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import { articleData } from '@/config/fakeData';
import ArticleItem from '@/components/pageComponent/articleItem';
import { themeLayout, scale } from '@/config';
import { connect } from '@/utils/dva';

class MoreArticle extends React.Component {
  static navigationOptions = {
    header: null,
  };

  componentDidMount() {
    this.queryMoreArticleDispatch();
  }

  queryMoreArticleDispatch = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'search/queryMoreArticleEffect',
      payload: 'data',
    });
  };

  render() {
    return (
      <View style={styles.container}>
        <Header backAction={this.backAction} title="更多相关文章" />
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

export default connect(mapStateToProps)(MoreArticle);

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
