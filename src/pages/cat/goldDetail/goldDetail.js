import React from 'react';
import { View, StyleSheet, StatusBar } from 'react-native';
import { connect } from '@/utils/dva';
import commentHoc from '@/components/pageComponent/commentHoc';
import FirstLoading from '@/components/Loading/FirstLoading';
import Header from '@/components/Header/index';
import SmallText from '@/components/AppText/Cat/SmallText';

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
          <Header title="金币明细" />
          <SmallText style={styles.actionText2}>goldDetail</SmallText>
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
});
