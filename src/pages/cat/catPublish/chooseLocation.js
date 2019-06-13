import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
// import { addLocationListener, start, stop } from 'react-native-amap-geolocation';
import MyTextInput from '@/components/TextInput';
import CommonText from '@/components/AppText/Cat/CommonText';
import { scale, themeLayout } from '@/config';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import MyScrollView from '@/components/ScrollView';
import LoadingUpContent from '@/components/Loading/LoadingUpContent';

class ChooseLocation extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    value: '',
  };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
  }

  componentWillUnmount() {
    this.props.dispatch({ type: 'catPublish/saveAddressList', payload: [] });
  }

  searchAddress = async keywords => {
    const { dispatch } = this.props;
    dispatch({
      type: 'catPublish/queryAddressListEffect',
      payload: { keywords },
    });
  };

  handleChangeText = value => {
    this.setState({
      value,
    });
    this.searchAddress(value);
  };

  handleClearText = () => {
    const { dispatch } = this.props;
    this.setState({
      value: '',
    });
    dispatch({ type: 'catPublish/saveAddressList', payload: [] });
  };

  handleCancelSearch = () => {
    const { navigation } = this.props;
    navigation.pop();
  };

  handleChooseAddress = item => {
    const { dispatch } = this.props;
    dispatch({ type: 'catPublish/saveSelectAddress', payload: item.name });
    this.handleCancelSearch();
  };

  render() {
    const { value } = this.state;
    const { catPublish, loading } = this.props;
    const { defaultAddressList, addressList } = catPublish;
    const showArr = !addressList.length ? defaultAddressList : addressList;
    return (
      <View style={styles.container}>
        <View style={styles.searchBar}>
          <MyTextInput
            conStyle={styles.inputCon}
            style={styles.inputStyle}
            onChangeText={this.handleChangeText}
            leftIcon={myImages.inputSearch}
            value={value}
            placeholder='搜索'
            returnKeyType='search'
            rightIcon={value.length > 0 ? myImages.x : null}
            rightIconAction={this.handleClearText}
          />
          <TouchableNativeFeedback tapArea={1} onPress={this.handleCancelSearch} notOut>
            <View style={styles.rightBtn}>
              <CommonText style={styles.btnText}>取消</CommonText>
            </View>
          </TouchableNativeFeedback>
        </View>
        <LoadingUpContent loading={loading}>
          <View style={styles.addressListCon}>
            <View style={styles.listTitleCon}>
              <CommonText style={styles.listTitle}>推荐地点</CommonText>
            </View>
            <MyScrollView>
              {showArr.map(item => {
                return (
                  <TouchableNativeFeedback
                    onPress={() => this.handleChooseAddress(item)}
                    tapArea={1}
                    notOut
                    key={item.id}
                  >
                    <View style={styles.listItemCon}>
                      <CommonText style={styles.listItem}>{item.name}</CommonText>
                      <CommonText style={styles.listItemDesc}>
                        {item.cityname + item.address}
                      </CommonText>
                    </View>
                  </TouchableNativeFeedback>
                );
              })}
            </MyScrollView>
          </View>
        </LoadingUpContent>
      </View>
    );
  }
}

const mapStateToProps = ({ catPublish, loading }) => ({
  catPublish,
  loading: loading.effects['catPublish/queryAddressListEffect'],
});

export default connect(mapStateToProps)(ChooseLocation);

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  searchBar: {
    ...themeLayout.flex('row', 'space-between'),
    marginTop: 20,
    ...themeLayout.padding(scale(9), 0, scale(9), scale(15)),
    ...themeLayout.borderSide('Bottom'),
  },
  inputCon: {
    borderRadius: 17,
    width: scale(299),
    backgroundColor: '#F3F3F3',
  },
  rightBtn: {
    width: scale(61),
    height: scale(33),
    ...themeLayout.flex('row'),
  },
  btnText: {
    color: '#777',
    fontSize: 16,
  },
  inputStyle: {
    ...themeLayout.padding(0),
  },
  addressListCon: {
    ...themeLayout.padding(0, scale(15)),
    flex: 1,
  },
  listTitleCon: {
    height: 48,
    ...themeLayout.flex('row', 'flex-start'),
    ...themeLayout.borderSide('Bottom'),
  },
  listTitle: {
    color: '#999',
    fontSize: 14,
  },
  listItemCon: {
    height: 80,
    ...themeLayout.flex('column', 'center', 'flex-start'),
    ...themeLayout.borderSide('Bottom'),
  },
  listItem: {
    color: '#222',
    fontSize: 14,
  },
  listItemDesc: {
    color: '#999',
    fontSize: 12,
    marginTop: 3,
  },
});
