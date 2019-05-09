import React from 'react';
import { View, StyleSheet } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import AppItem from '@/components/pageComponent/appItem';
import ArticleItem from '@/components/pageComponent/articleItem';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { themeLayout, scale } from '@/config';
import { ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import { connect } from '@/utils/dva';
import { lastArr } from '@/utils/utils';
import Loading from '@/components/Loading/loading';

class MoreSearch extends React.Component {
  static navigationOptions = {
    header: null,
  };

  constructor(props) {
    super(props);
    const { navigation } = props;
    this.state = {
      allLoaded: false,
    };
    this.type = navigation.getParam('type') || 1;
    this.searchKey = navigation.getParam('searchKey');
  }

  componentDidMount() {
    const data = {
      id: 0,
      isFirst: true,
    };
    this.querySearchDispatch(data);
  }

  componentWillUnmount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'search/saveSearchList',
      payload: [],
      isFirstPage: true,
    });
  }

  // 搜索接口
  querySearchDispatch = params => {
    const { dispatch } = this.props;
    const data = {
      pagesize: 20,
      search: this.searchKey,
      search_type: this.type,
      ...params,
    };
    dispatch({
      type: 'search/querySearchEffect',
      payload: data,
      successFn: response => {
        if (response.length === 0) {
          this.setState({
            allLoaded: true,
          });
        }
      },
      finallyFn: () => {
        this.refScrollView && this.refScrollView.endLoading();
      },
    });
  };

  queryNextPage = () => {
    const { search } = this.props;
    const lastItem = lastArr(search.searchList);
    console.log('%clastItem:', 'color: #0e93e0;background: #aaefe5;', lastItem);
    this.querySearchDispatch({ id: lastItem.id });
  };

  render() {
    const { navigation } = this.props;
    const { allLoaded } = this.state;
    const { search, loading } = this.props;
    const app = {
      2: {
        title: '更多相关App',
        component: AppItem,
      },
      3: {
        title: '更多相关文章',
        component: ArticleItem,
      },
      4: {
        title: '更多相关互动话题',
        component: ArticleItem,
      },
      5: {
        title: '更多相关X友分享',
        component: XfriendItem,
      },
    };
    const Compnt = app[+this.type].component;
    if (loading && !search.searchList.length) return <Loading />;
    return (
      <View style={styles.container}>
        <Header navigation={navigation} title={app[+this.type].title} />
        <SpringScrollView
          ref={ref => (this.refScrollView = ref)}
          bounces
          onLoading={this.queryNextPage}
          allLoaded={allLoaded}
          loadingFooter={ChineseNormalFooter}
        >
          <View style={styles.searchSectionList}>
            {search.searchList.map(item => (
              <Compnt key={item.id} itemData={item} islastOne={false} />
            ))}
          </View>
        </SpringScrollView>
      </View>
    );
  }
}

const mapStateToProps = ({ search, loading }) => ({
  search,
  loading: loading.effects['search/querySearchEffect'],
});

export default connect(mapStateToProps)(MoreSearch);

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
