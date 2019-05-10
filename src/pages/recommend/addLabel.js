import React, { Fragment } from 'react';
import { View, StyleSheet, TouchableOpacity } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import SectionTitle from '@/components/SectionTitle';
import TextInput from '@/components/TextInput';
import { themeLayout, scale, themeSize, themeColor } from '@/config';
import myImages from '@/utils/myImages';
import { debounce, clearRepeatArr } from '@/utils/utils';
import CommonText from '@/components/AppText/CommonText';
import SecondaryText from '@/components/AppText/SecondaryText';
import { connect } from '@/utils/dva';
import Toast from '@/components/Toast';
// import { hotClassify } from '@/config/fakeData';
import LabelBtn from './components/labelBtn';

class AddLabel extends React.Component {
  static navigationOptions = {
    header: null,
  };

  pageSize = 100;

  state = {
    textValue: '',
    selectedLabel: [],
    isSearching: false,
  };

  handleSearchDebounce = debounce(value => {
    this.setState({ isSearching: true });
    return this.queryLabelDispatch(value);
  }, 500);

  componentDidMount() {
    const { recommend } = this.props;
    this.setState({ selectedLabel: recommend.selectedLabel });
    this.queryLabelDispatch();
  }

  queryLabelDispatch = search => {
    const { dispatch } = this.props;
    const data = {
      id: 0,
      pagesize: this.pageSize,
      search,
    };
    dispatch({
      type: 'recommend/queryHotClassifyEffect',
      payload: data,
    });
  };

  handleChangeText = value => {
    this.setState({
      textValue: value,
    });
    if (!value.length) this.clearAppList();
    this.handleSearchDebounce(value);
  };

  handleSubmitLabel = () => {
    const { navigation, dispatch } = this.props;
    const { selectedLabel } = this.state;
    dispatch({
      type: 'recommend/saveSelectedLabelList',
      payload: selectedLabel,
    });
    navigation.navigate('RecommendEdit', { selectedLabel });
  };

  handleAddLabel = item => {
    const { selectedLabel } = this.state;
    if (selectedLabel.length === 3) {
      Toast.show('最多选择3个');
    }
    this.setState({
      selectedLabel: [...selectedLabel, item],
    });
  };

  handleDeleteLabel = item => {
    const { selectedLabel } = this.state;
    this.setState({
      selectedLabel: clearRepeatArr(selectedLabel, [item]),
    });
  };

  handleSelectSearchLabel = item => {
    this.handleAddLabel(item);
    this.setState({
      isSearching: false,
      textValue: '',
    });
  };

  renderHeaderRight = () => {
    const { isSearching } = this.state;
    if (isSearching) return null;
    return (
      <TouchableOpacity onPress={this.handleSubmitLabel} style={[styles.headerTextCon]}>
        <CommonText style={styles.headerText}>完成</CommonText>
      </TouchableOpacity>
    );
  };

  renderSearchList = () => {
    const { recommend } = this.props;
    const searchList = clearRepeatArr(recommend.searchLable, this.state.selectedLabel);
    return (
      <View style={styles.searchListCon}>
        {searchList.map(item => {
          return (
            <TouchableOpacity
              onPress={() => this.handleSelectSearchLabel(item)}
              style={styles.searchListItem}
              key={item.id}
            >
              <CommonText>#{item.label}</CommonText>
              <SecondaryText style={styles.searchCnt}>{item.cnt}人用过</SecondaryText>
            </TouchableOpacity>
          );
        })}
      </View>
    );
  };

  renderSelectedLabel = () => {
    const { selectedLabel } = this.state;
    const { recommend } = this.props;
    return (
      <Fragment>
        <View style={styles.labelCon}>
          {selectedLabel.map(item => {
            return (
              <LabelBtn pressAction={() => this.handleDeleteLabel(item)} key={item.id}>
                {item.label}
              </LabelBtn>
            );
          })}
        </View>
        <View style={styles.hotCon}>
          <SectionTitle title='热门分类' />
          <View style={styles.hotListCon}>
            {recommend.hotClassify.map(item => {
              const disabled = new Set(selectedLabel).has(item);
              return (
                <TouchableOpacity
                  onPress={() => (disabled ? null : this.handleAddLabel(item))}
                  style={styles.hotItem(disabled)}
                  key={item.id}
                >
                  <CommonText style={styles.hotItemText(disabled)}>{item.label}</CommonText>
                </TouchableOpacity>
              );
            })}
          </View>
        </View>
      </Fragment>
    );
  };

  render() {
    const { navigation } = this.props;
    const { textValue, isSearching } = this.state;
    return (
      <View style={styles.container}>
        <Header
          navigation={navigation}
          title='添加标签'
          rightComponent={this.renderHeaderRight()}
        />
        <SpringScrollView>
          <View style={styles.bodyContainer}>
            <TextInput
              leftIcon={myImages.inputSearch}
              conStyle={styles.inputCon}
              style={styles.inputStyle}
              onChangeText={this.handleChangeText}
              value={textValue}
              placeholder='搜索标签'
            />
            {isSearching ? this.renderSearchList() : this.renderSelectedLabel()}
          </View>
        </SpringScrollView>
      </View>
    );
  }
}

const mapStateToProps = ({ recommend, loading }) => ({
  recommend,
  loading: loading.effects['recommend/queryHotClassifyEffect'],
});

export default connect(mapStateToProps)(AddLabel);

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  bodyContainer: {
    ...themeLayout.padding(0, scale(16)),
  },
  inputCon: {
    marginTop: scale(16),
    borderRadius: scale(5),
    width: scale(328),
    ...themeLayout.border(),
  },
  inputStyle: {
    ...themeLayout.padding(scale(5)),
  },
  searchListCon: {
    marginTop: scale(6),
    marginBottom: scale(30),
  },
  searchListItem: {
    ...themeLayout.borderSide(),
    ...themeLayout.padding(scale(10), scale(9)),
    ...themeLayout.flex('row', 'flex-start', 'flex-end'),
  },
  searchCnt: {
    opacity: 0.6,
    marginLeft: scale(10),
    fontSize: scale(10),
  },
  labelCon: {
    ...themeLayout.flex('row', 'flex-start'),
    marginTop: scale(16),
    marginLeft: scale(10),
  },
  hotCon: {
    marginTop: scale(20),
  },
  hotListCon: {
    marginTop: scale(3),
    ...themeLayout.flex('row', 'flex-start'),
    flexWrap: 'wrap',
  },
  hotItem: disabled => {
    return {
      ...themeLayout.border(themeSize.minBorder, '#dfdfdf'),
      ...themeLayout.flex(),
      ...themeLayout.margin(scale(18), scale(10), scale(0)),
      ...themeLayout.padding(scale(0), scale(10)),
      borderRadius: scale(12),
      height: scale(32),
      width: scale(88),
      backgroundColor: disabled ? themeColor.bgColor : '#fff',
    };
  },
  hotItemText: disabled => {
    return {
      color: disabled ? '#aaa' : themeColor.font.black,
    };
  },
  headerTextCon: {
    ...themeLayout.flex('row', 'flex-end'),
  },
  headerText: {
    color: themeColor.primaryColor,
  },
});
