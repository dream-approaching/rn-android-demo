import React from 'react';
import { View, StyleSheet, StatusBar, ActivityIndicator } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import TextInput from '@/components/TextInput';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';
import { debounce, lastArr } from '@/utils/utils';
import { scale, themeLayout, themeColor } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import { ChineseNormalFooter } from 'react-native-spring-scrollview/Customize';
import { TouchableOpacity } from 'react-native-gesture-handler';
import SearchItem from './components/searchItem';

class Recommend extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    textValue: '',
    allLoaded: false,
  };

  pageSize = 10;

  handleSearchDebounce = debounce(value => this.queryAppListDispatch(value), 500);

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
  }

  handleChangeText = value => {
    this.setState({
      textValue: value,
    });
    if (!value.length) this.clearAppList();
    // debounce(() => this.queryAppListDispatch(value), 500)();  // 这样不行，每次onchange都会放到500ms后执行
    this.handleSearchDebounce(value); // 这样就可以，只有最后一次触发的onchange才会执行
  };

  queryAppListDispatch = (searchKey, id = 0, pagesize = this.pageSize) => {
    const { dispatch } = this.props;
    const data = {
      pagesize,
      search_type: 2,
      search: searchKey,
      id,
    };
    return dispatch({
      type: 'recommend/queryAppEffect',
      payload: data,
      finallyFn: () => {
        this.refScrollView && this.refScrollView.endLoading();
        // this.refScrollView.endRefresh();
      },
    });
  };

  queryNextPage = () => {
    const { textValue } = this.state;
    const { recommend } = this.props;
    const lastItem = lastArr(recommend.appList);
    console.log('%clastItem:', 'color: #0e93e0;background: #aaefe5;', textValue, lastItem);
    this.queryAppListDispatch(textValue, lastItem.id);
  };

  clearAppList = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'recommend/saveAppList',
      payload: [],
    });
  };

  render() {
    const { textValue, allLoaded } = this.state;
    const { recommend, loading } = this.props;
    const { appList } = recommend;
    // console.log('%cappList:', 'color: #0e93e0;background: #aaefe5;', appList);
    const noSearch = !textValue.length;
    const noList = textValue.length && !appList.length && !loading;
    return (
      <View style={styles.container}>
        <Header title='我要推荐' />
        <TextInput
          leftIcon={myImages.inputSearch}
          conStyle={styles.inputCon}
          onChangeText={this.handleChangeText}
          value={textValue}
          placeholder='输入你想推荐的应用'
        />
        <TouchableOpacity onPress={() => this.props.navigation.navigate('RecommendEdit')}>
          <SecondaryText style={styles.searchTitle}>点击进入提交页面</SecondaryText>
        </TouchableOpacity>
        {!noSearch && (
          <View style={styles.searchCon}>
            <View style={styles.searchTitleCon}>
              <SecondaryText style={styles.searchTitle}>搜索结果</SecondaryText>
            </View>
            {loading && <ActivityIndicator />}
            <SpringScrollView
              ref={ref => (this.refScrollView = ref)}
              bounces
              onLoading={this.queryNextPage}
              allLoaded={allLoaded}
              loadingFooter={ChineseNormalFooter}
            >
              {(noList && <SecondaryText>没有结果</SecondaryText>) ||
                appList.map(item => {
                  return <SearchItem searchKey={textValue} key={item.id} itemData={item} />;
                })}
            </SpringScrollView>
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
