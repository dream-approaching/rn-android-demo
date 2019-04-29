import React from 'react';
import { View, StyleSheet, StatusBar, ActivityIndicator } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import TextInput from '@/components/TextInput';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';
import { scale, themeLayout, themeColor } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import SearchItem from './components/searchItem';

class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    textValue: '',
  };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
  }

  handleChangeText = value => {
    this.setState({
      textValue: value,
    });
    this.queryAppListDispatch(value, 20);
    if (!value.length) this.clearAppList();
  };

  queryAppListDispatch = (searchKey, pagesize = 20, id = 0) => {
    const { dispatch } = this.props;
    const data = {
      app_ver_code: '1',
      ch: '1',
      mobilephone: '13613033073',
      access_token: 'eb07f80389496cc665ffb93bc059263e',
      pagesize,
      app_ver: '1',
      channel_id: '1',
      search_type: 2,
      search: searchKey,
      id,
    };
    return dispatch({
      type: 'recommend/queryAppEffect',
      payload: data,
    });
  };

  clearAppList = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'recommend/saveAppList',
      payload: [],
    });
  };

  render() {
    const { textValue } = this.state;
    const { recommend, loading } = this.props;
    const { appList } = recommend;
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
          clearButtonMode='while-editing'
          placeholder='输入你想推荐的应用'
        />
        {!noSearch && (
          <View style={styles.searchCon}>
            <View style={styles.searchTitleCon}>
              <SecondaryText style={styles.searchTitle}>搜索结果</SecondaryText>
            </View>
            {loading && <ActivityIndicator />}
            {(noList && <SecondaryText>没有结果</SecondaryText>) || (
              <SpringScrollView bounces>
                {appList.map(item => {
                  return <SearchItem searchKey={textValue} key={item.id} itemData={item} />;
                })}
              </SpringScrollView>
            )}
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

export default connect(mapStateToProps)(CommentPage);

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
