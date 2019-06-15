import React, { Fragment } from 'react';
import { View, StyleSheet, StatusBar, BackHandler } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import SectionTitle from '@/components/SectionTitle/indexCat';
import { themeLayout, scale, themeColor, themeSize } from '@/config';
import CommonText from '@/components/AppText/CommonText';
import { connect } from '@/utils/dva';
import SearchBar from './components/searchBar';
import { timeoutSearch } from '@/config/constants';
import SimpleFooter from '@/components/ScrollFooter/SimpleFooter';

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

  componentWillUnmount() {
    this.searchTimer && clearTimeout(this.searchTimer);
  }

  // 查询历史搜索
  queryHistorySearchDispatch = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'search/queryHistorySearchEffect',
      payload: {
        id: 0,
        pagesize: 6, // 只显示6条历史搜索
      },
    });
  };

  // 查询热门分类
  queryHotClassifyDispatch = () => {
    const { dispatch } = this.props;
    const data = {
      id: 0,
      pagesize: 50,
      is_hot: '1',
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
      return this.setState({ isSearching: false });
    }
    if (this.searchTimer) clearTimeout(this.searchTimer);
    this.searchTimer = setTimeout(() => {
      this.querySearchDispatch(value);
    }, timeoutSearch);
  };

  handleCleanText = () => {
    this.setState({ searchKey: '', isSearching: false });
  };

  handleQuitActivity = () => {
    BackHandler.exitApp();
  };

  handleClearHistory = () => {
    const { dispatch } = this.props;
    console.log('%cdispatch:', 'color: #0e93e0;background: #aaefe5;', dispatch);
    dispatch({
      type: 'search/deleteHistorySearchEffect',
    });
  };

  render() {
    const { searchKey, isSearching } = this.state;
    const { search, recommend } = this.props;
    const data = [{ content: '123' }, { content: '4566' }];
    const data2 = [{ id: '123', label: '123' }, { id: '4566', label: '1234' }];
    return (
      <View style={styles.container}>
        <SearchBar
          searchKey={searchKey}
          changeSearchKeyAction={this.handleChangeSearchKey}
          cancelSearchAction={this.handleQuitActivity}
          cleanTextAction={this.handleCleanText}
          title="搜索"
        />
        <SpringScrollView
          onLoading={() => {}}
          bounces={isSearching}
          loadingFooter={SimpleFooter}
          allLoaded
        >
          <Fragment>
            <View style={[styles.sectionCon, styles.hotCon]}>
              <SectionTitle fontWeight="bold" title="大家都在搜" />
              <View style={styles.hotList}>
                {/* {recommend.hotClassify.map(item => { */}
                {data2.map(item => {
                  return (
                    <TouchableNativeFeedback
                      onPress={() => this.handleSelectClassify(item.label)}
                      key={item.id}
                    >
                      <View style={styles.hotItem}>
                        <CommonText>{item.label}</CommonText>
                      </View>
                    </TouchableNativeFeedback>
                  );
                })}
              </View>
            </View>
            <View style={[styles.sectionCon, styles.historyCon]}>
              <SectionTitle
                title="搜索历史"
                fontWeight="bold"
                rightAction={this.handleClearHistory}
                type={search.historySearchList.length > 0 ? 'del' : null}
              />
              <View style={styles.historyList}>
                {/* {search.historySearchList.map(item => { */}
                {data.map(item => {
                  return (
                    <TouchableNativeFeedback
                      tapArea={1}
                      onPress={() => this.handleSelectClassify(item.content)}
                      key={item.content}
                    >
                      <View style={styles.historyItem}>
                        <CommonText>{item.content}</CommonText>
                      </View>
                    </TouchableNativeFeedback>
                  );
                })}
              </View>
            </View>
          </Fragment>
        </SpringScrollView>
      </View>
    );
  }
}

const mapStateToProps = ({ search, recommend, loading }) => ({
  search,
  recommend,
  historyLoading: loading.effects['search/queryHistorySearchEffect'],
  searchLoading: loading.effects['search/querySearchEffect'],
  hotLoading: loading.effects['recommend/queryHotClassifyEffect'],
  deleteLoading: loading.effects['search/deleteHistorySearchEffect'],
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
    ...themeLayout.borderSide('Top', '#f3f3f3', 5),
    paddingTop: scale(10),
  },
  historyList: {
    flexWrap: 'wrap',
  },
  historyItem: {
    ...themeLayout.borderSide('Bottom'),
    ...themeLayout.flex('row', 'flex-start'),
    height: 48,
  },
  hotCon: {
    marginTop: scale(18),
  },
  hotList: {
    ...themeLayout.flex('row', 'flex-start'),
    flexWrap: 'wrap',
  },
  hotItem: {
    ...themeLayout.border(themeSize.minBorder, '#dadada'),
    ...themeLayout.flex(),
    ...themeLayout.margin(scale(12), scale(11), scale(0), 0),
    ...themeLayout.padding(scale(0), scale(10)),
    borderRadius: scale(12),
    height: scale(24),
    minWidth: scale(45),
  },
  searchSectionCon: {
    ...themeLayout.borderSide('Bottom', themeColor.bgF4, scale(4)),
    marginTop: scale(22),
  },
  searchSectionList: {
    ...themeLayout.flex('column'),
    paddingBottom: scale(10),
    paddingTop: scale(5),
  },
});
