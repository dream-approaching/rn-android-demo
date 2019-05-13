import React, { Fragment } from 'react';
import { View, StyleSheet, StatusBar, BackHandler } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import { TouchableOpacity } from 'react-native-gesture-handler';
import SectionTitle from '@/components/SectionTitle';
import { themeLayout, scale, themeColor, themeSize } from '@/config';
import CommonText from '@/components/AppText/CommonText';
import XfriendItem from '@/components/pageComponent/xfriendItem';
import AppItem from '@/components/pageComponent/appItem';
import ArticleItem from '@/components/pageComponent/articleItem';
import { connect } from '@/utils/dva';
import SearchBar from './components/searchBar';
import Loading from '@/components/Loading/loading';

class Search extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    searchKey: '',
    isSearching: false,
  };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
    this.queryHistorySearchDispatch();
    this.queryHotClassifyDispatch();
  }

  // 查询历史搜索
  queryHistorySearchDispatch = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'search/queryHistorySearchEffect',
      payload: {
        id: 0,
        pagesize: 10, // 只显示10条历史搜索
      },
    });
  };

  // 查询热门分类
  queryHotClassifyDispatch = () => {
    const { dispatch } = this.props;
    const data = {
      id: 0,
      pagesize: 50,
    };
    dispatch({
      type: 'recommend/queryHotClassifyEffect',
      payload: data,
    });
  };

  // 搜索接口
  querySearchDispatch = (search, searchType = 1) => {
    const { dispatch } = this.props;
    const data = {
      id: 0,
      pagesize: 20,
      search,
      search_type: searchType,
    };
    // 搜索之前先清空上次的数据
    dispatch({ type: 'search/saveSearchAll', payload: {} });
    dispatch({
      type: 'search/querySearchEffect',
      payload: data,
    });
  };

  handleSelectClassify = searchKey => {
    this.handleChangeSearchKey(searchKey);
  };

  handleChangeSearchKey = value => {
    const { isSearching } = this.state;
    this.setState({ searchKey: value, isSearching: true });
    if (value.length) {
      !isSearching && this.setState({ isSearching: true });
    } else {
      this.setState({ isSearching: false });
    }
    this.querySearchDispatch(value);
  };

  handleCleanText = () => {
    this.setState({ searchKey: '', isSearching: false });
  };

  handleQuitActivity = () => {
    BackHandler.exitApp();
  };

  handleGotoMore = item => {
    const { navigation } = this.props;
    const { searchKey } = this.state;
    navigation.navigate('MoreSearch', { type: item.type, searchKey });
  };

  renderDidNotSearch = () => {
    const { search, recommend } = this.props;
    return (
      <Fragment>
        <View style={[styles.historyCon, styles.sectionCon]}>
          <SectionTitle
            title='历史搜索'
            type={search.historySearchList.length > 0 ? 'del' : null}
          />
          <View style={styles.historyList}>
            {search.historySearchList.map(item => {
              return (
                <TouchableOpacity
                  onPress={() => this.handleSelectClassify(item.content)}
                  style={styles.historyItem}
                  key={item.id}
                >
                  <CommonText>{item.content}</CommonText>
                </TouchableOpacity>
              );
            })}
          </View>
        </View>
        <View style={[styles.hotCon, styles.sectionCon]}>
          <SectionTitle title='热门分类' />
          <View style={styles.hotList}>
            {recommend.hotClassify.map(item => {
              return (
                <TouchableOpacity
                  onPress={() => this.handleSelectClassify(item.label)}
                  style={styles.hotItem}
                  key={item.id}
                >
                  <CommonText>{item.label}</CommonText>
                </TouchableOpacity>
              );
            })}
          </View>
        </View>
      </Fragment>
    );
  };

  renderSearchResult = () => {
    const { search, searchLoading } = this.props;
    const { app, recommend, topic, share } = search.searchAll;
    if (searchLoading) return <Loading />;
    if (!app && !recommend && !topic && !share) return <CommonText>暂无相关内容</CommonText>;
    const { black } = themeColor.font;
    const sectionList = [
      {
        title: 'APP应用',
        type: 2,
        data: app,
        bodyRender: () =>
          app.map((item, index) => {
            const islastOne = index === app.length - 1;
            return <AppItem key={item.id} itemData={item} islastOne={islastOne} />;
          }),
      },
      {
        title: '文章',
        type: 3,
        data: recommend,
        bodyRender: () =>
          recommend.map((item, index) => {
            const islastOne = index === recommend.length - 1;
            return <ArticleItem key={item.id} itemData={item} islastOne={islastOne} />;
          }),
      },
      {
        title: '互动话题',
        type: 4,
        data: topic,
        bodyRender: () =>
          topic.map((item, index) => {
            const islastOne = index === topic.length - 1;
            return <ArticleItem key={item.id} itemData={item} islastOne={islastOne} />;
          }),
      },
      {
        title: 'X友分享',
        type: 5,
        data: share,
        bodyRender: () =>
          share.map((item, index) => {
            const islastOne = index === share.length - 1;
            return <XfriendItem key={item.id} itemData={item} islastOne={islastOne} />;
          }),
      },
    ];
    return (
      <Fragment>
        {sectionList.map(item => {
          if (!item.data) return null;
          return (
            <View key={item.title} style={[styles.searchSectionCon, styles.sectionCon]}>
              <SectionTitle
                color={black}
                title={item.title}
                type='more'
                rightAction={() => this.handleGotoMore(item)}
              />
              <View style={styles.searchSectionList}>{item.bodyRender()}</View>
            </View>
          );
        })}
      </Fragment>
    );
  };

  render() {
    const { searchKey, isSearching } = this.state;
    return (
      <View style={styles.container}>
        <SearchBar
          searchKey={searchKey}
          changeSearchKeyAction={this.handleChangeSearchKey}
          cancelSearchAction={this.handleQuitActivity}
          cleanTextAction={this.handleCleanText}
          title='搜索'
        />
        <SpringScrollView>
          {isSearching ? this.renderSearchResult() : this.renderDidNotSearch()}
        </SpringScrollView>
      </View>
    );
  }
}

const mapStateToProps = ({ search, recommend, loading }) => ({
  search,
  recommend,
  loading: loading.effects['search/queryXshareListEffect'],
  searchLoading: loading.effects['search/querySearchEffect'],
});

export default connect(mapStateToProps)(Search);

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  sectionCon: {
    ...themeLayout.padding(scale(0), scale(20)),
  },
  historyCon: {
    marginTop: scale(14),
  },
  historyList: {
    ...themeLayout.flex('row', 'flex-start'),
    flexWrap: 'wrap',
    marginTop: scale(2),
  },
  historyItem: {
    backgroundColor: themeColor.bgColor,
    ...themeLayout.border(themeSize.minBorder, '#dfdfdf'),
    ...themeLayout.flex(),
    ...themeLayout.margin(scale(12), scale(10), scale(0)),
    ...themeLayout.padding(scale(0), scale(10)),
    borderRadius: scale(12),
    height: scale(32),
    minWidth: scale(85),
  },
  hotCon: {
    marginTop: scale(18),
  },
  hotList: {
    ...themeLayout.flex('row', 'flex-start'),
    flexWrap: 'wrap',
    marginTop: scale(5),
  },
  hotItem: {
    ...themeLayout.border(themeSize.minBorder, '#dfdfdf'),
    ...themeLayout.flex(),
    ...themeLayout.margin(scale(15), scale(10), scale(0)),
    ...themeLayout.padding(scale(0), scale(10)),
    borderRadius: scale(12),
    height: scale(32),
    minWidth: scale(85),
  },
  searchSectionCon: {
    ...themeLayout.borderSide('Bottom', themeColor.borderColor, scale(2)),
    marginTop: scale(22),
  },
  searchSectionList: {
    ...themeLayout.flex('column'),
    paddingBottom: scale(10),
    paddingTop: scale(5),
  },
});
