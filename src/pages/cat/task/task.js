import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import { connect } from '@/utils/dva';
import commentHoc from '@/components/pageComponent/commentHoc';
import FirstLoading from '@/components/Loading/FirstLoading';
import Header from '@/components/Header/index';
import { themeLayout, scale } from '@/config';
import CommonText from '@/components/AppText/CommonText';

class Task extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    isFirstTime: true,
  };

  componentDidMount() {
    StatusBar.setBarStyle('dark-content', true);
    this.setState({
      isFirstTime: false,
    });
  }

  componentWillUnmount() {
    const { dispatch } = this.props;
    dispatch({ type: 'catHome/saveArticleDetail', payload: { img: '', detailtwo: [] } });
  }

  render() {
    const { isFirstTime } = this.state;
    return (
      <View style={styles.container}>
        <FirstLoading loading={isFirstTime}>
          <Header title="今日任务" />
          <View style={styles.itemCon}>
            <View style={styles.leftCon}>
              <CommonText style={styles.itemTitle}>登录App</CommonText>
              <CommonText style={styles.itemTime}>昨天</CommonText>
            </View>
            <CommonText style={styles.itemValue}>+6000</CommonText>
          </View>
        </FirstLoading>
      </View>
    );
  }
}

const mapStateToProps = ({ catHome, loading }) => ({
  catHome,
  loading: loading.effects['catHome/queryArticleDetailEffect'],
});

export default connect(mapStateToProps)(commentHoc(Task));

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  itemCon: {
    ...themeLayout.padding(0, 16),
    ...themeLayout.borderSide(),
    height: scale(78),
    ...themeLayout.flex('row', 'space-between'),
  },
  leftCon: {
    ...themeLayout.flex('column', 'center', 'flex-start'),
  },
  itemTitle: {
    fontSize: 15,
  },
  itemTime: {
    color: '#999',
    marginTop: 5,
  },
  itemValue: {
    fontSize: 17,
  },
});
