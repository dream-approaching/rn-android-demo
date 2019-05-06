import React, { Fragment } from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import { TouchableOpacity } from 'react-native-gesture-handler';
import SearchBar from './components/searchBar';
import SectionTitle from './components/sectionTitle';
import { themeLayout, scale, themeColor, themeSize } from '@/config';
import CommonText from '@/components/AppText/CommonText';

const history = ['工具', '效率', '旅游'];
const hot = [
  '工具',
  '效率',
  '旅游',
  '工具2',
  '效率2',
  '旅游2',
  '工具3',
  '效率3',
  '旅游3',
  '工具4',
  '效率效率',
];
export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    searchKey: '',
  };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
  }

  handleChangeSearchKey = value => {
    console.log(value);
    this.setState({ searchKey: value });
  };

  handleCancelSearch = () => {
    console.log('取消搜索，返回上一页');
  };

  renderDidNotSearch = () => {
    return (
      <Fragment>
        <View style={[styles.historyCon, styles.sectionCon]}>
          <SectionTitle title='历史搜索' type='del' />
          <View style={styles.historyList}>
            {history.map(item => {
              return (
                <TouchableOpacity style={styles.historyItem} key={item}>
                  <CommonText>{item}</CommonText>
                </TouchableOpacity>
              );
            })}
          </View>
        </View>
        <View style={[styles.hotCon, styles.sectionCon]}>
          <SectionTitle title='热门分类' />
          <View style={styles.hotList}>
            {hot.map(item => {
              return (
                <TouchableOpacity style={styles.hotItem} key={item}>
                  <CommonText>{item}</CommonText>
                </TouchableOpacity>
              );
            })}
          </View>
        </View>
      </Fragment>
    );
  };

  render() {
    const { searchKey } = this.state;
    return (
      <View style={styles.container}>
        <SearchBar
          searchKey={searchKey}
          changeSearchKeyAction={this.handleChangeSearchKey}
          cancelSearchAction={this.handleCancelSearch}
          title='搜索'
        />
        <SpringScrollView>{this.renderDidNotSearch()}</SpringScrollView>
      </View>
    );
  }
}

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
});
