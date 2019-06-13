import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import TextInput from '@/components/TextInput';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';
import { lastArr } from '@/utils/utils';
import { scale, themeLayout, themeColor } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import SearchItem from './components/searchItem';
import NoData from '@/components/NoData';
import SimpleFooter from '@/components/ScrollFooter/SimpleFooter';
import LoadingUpContent from '@/components/Loading/LoadingUpContent';
import { timeoutSearch } from '@/config/constants';

class Recommend extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    textValue: '',
    allLoaded: false,
    noDataText: '',
  };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
  }

  componentWillUnmount() {
    this.searchTimer && clearTimeout(this.searchTimer);
    this.props.dispatch({
      type: 'recommend/saveAppList',
      isFirstPage: true,
      payload: [],
    });
  }

  handleChangeText = value => {
    this.setState({
      textValue: value,
      noDataText: '',
    });
    if (!value.length) return this.clearAppList();
    if (this.searchTimer) clearTimeout(this.searchTimer);
    this.searchTimer = setTimeout(() => {
      const data = {
        id: 0,
        isFirst: true,
        search: value,
      };
      this.queryAppListDispatch(data);
    }, timeoutSearch);
  };

  queryAppListDispatch = params => {
    const { dispatch } = this.props;
    const data = {
      pagesize: 20,
      search_type: 2,
      ...params,
    };
    return dispatch({
      type: 'recommend/queryAppEffect',
      payload: data,
      successFn: response => {
        if (response.length === 0) {
          this.setState({
            allLoaded: true,
            noDataText: '暂无数据',
          });
        }
      },
      finallyFn: () => {
        this.refScrollView && this.refScrollView.endLoading();
      },
    });
  };

  queryNextPage = () => {
    const { textValue } = this.state;
    const { recommend } = this.props;
    const lastItem = lastArr(recommend.appList);
    const data = {
      id: lastItem.id,
      search: textValue,
    };
    this.queryAppListDispatch(data);
  };

  clearAppList = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'recommend/saveAppList',
      payload: [],
      isFirstPage: true,
    });
  };

  gotoRecommendEdit = item => {
    this.props.navigation.navigate('RecommendEdit', { id: item.id });
  };

  render() {
    const { textValue, allLoaded, noDataText } = this.state;
    const { recommend, loading } = this.props;
    const { appList } = recommend;
    const isSearching = !!textValue.length;
    return (
      <View style={styles.container}>
        <Header title='我要推荐' />
        <TextInput
          leftIcon={myImages.inputSearch}
          conStyle={styles.inputCon}
          onChangeText={this.handleChangeText}
          value={textValue}
          placeholder='输入你想推荐的应用'
          returnKeyType='search'
        />
        {isSearching && (
          <View style={styles.searchCon}>
            <View style={styles.searchTitleCon}>
              <SecondaryText style={styles.searchTitle}>搜索结果</SecondaryText>
            </View>
            <LoadingUpContent loading={loading}>
              <SpringScrollView
                ref={ref => (this.refScrollView = ref)}
                bounces
                onLoading={this.queryNextPage}
                allLoaded={allLoaded}
                loadingFooter={SimpleFooter}
                contentStyle={{ backgroundColor: '#fff' }}
              >
                {(!!appList.length &&
                  appList.map(item => {
                    return (
                      <SearchItem
                        gotoAppAction={() => this.gotoRecommendEdit(item)}
                        searchKey={textValue}
                        key={item.id}
                        itemData={item}
                      />
                    );
                  })) || <NoData text={noDataText} />}
              </SpringScrollView>
            </LoadingUpContent>
          </View>
        )}
      </View>
    );
  }
}

const mapStateToProps = ({ recommend, loading }) => ({
  recommend,
  loading: loading.effects['recommend/queryAppEffect'],
});

export default connect(mapStateToProps)(Recommend);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    ...themeLayout.flex('column', 'flex-start'),
    backgroundColor: themeColor.bgColor,
  },
  inputCon: {
    marginTop: scale(16),
    borderRadius: scale(5),
    width: scale(328),
  },
  searchCon: {
    flex: 1,
    backgroundColor: '#fff',
    width: scale(360),
    marginTop: scale(16),
    ...themeLayout.padding(0, scale(16)),
  },
  searchTitleCon: {
    ...themeLayout.borderSide(),
    ...themeLayout.padding(scale(9), 0, scale(7)),
  },
  searchTitle: {
    fontSize: scale(15),
  },
});
