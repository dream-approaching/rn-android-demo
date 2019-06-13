import React from 'react';
import { View, StyleSheet } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import AppItem from '@/components/pageComponent/appItem';
import ArticleItem from '@/components/pageComponent/articleItem';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import { themeLayout, scale } from '@/config';
import { connect } from '@/utils/dva';
import { lastArr } from '@/utils/utils';
import SimpleFooter from '@/components/ScrollFooter/SimpleFooter';
import FirstLoading from '@/components/Loading/FirstLoading';

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
        title: '更多相关莓友分享',
        component: XfriendItem,
      },
    };
    const Compnt = app[+this.type].component;
    return (
      <View style={styles.container}>
        <Header navigation={navigation} title={app[+this.type].title} />
        <FirstLoading loading={loading && !search.searchList.length}>
          <SpringScrollView
            ref={ref => (this.refScrollView = ref)}
            bounces
            onLoading={this.queryNextPage}
            allLoaded={allLoaded}
            loadingFooter={SimpleFooter}
          >
            <View style={styles.searchSectionList}>
              {search.searchList.map(item => (
                <Compnt key={item.id} itemData={item} islastOne={false} />
              ))}
            </View>
          </SpringScrollView>
        </FirstLoading>
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
    ...themeLayout.padding(scale(5), scale(16), scale(10)),
  },
});
