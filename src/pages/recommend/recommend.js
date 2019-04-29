import React from 'react';
import { View, StyleSheet } from 'react-native';
import SpringScrollView from '@/components/SpringScrollView';
import Header from '@/components/Header';
import CommonText from '@/components/AppText/CommonText';
import TextInput from '@/components/TextInput';
import { scale, themeLayout, themeColor } from '@/config';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    textValue: '',
  };

  handleChangeText = value => {
    this.setState({
      textValue: value,
    });
  };

  render() {
    const { textValue } = this.state;
    return (
      <View style={styles.container}>
        <Header title="我要推荐" />
        <View style={styles.inputCon}>
          <TextInput
            style={styles.inputStyle}
            onChangeText={this.handleChangeText}
            value={textValue}
            placeholder="输入你想推荐的应用"
          />
        </View>
        <SpringScrollView>
          <CommonText>我要推荐</CommonText>
        </SpringScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    ...themeLayout.flex('column', 'flex-start'),
    backgroundColor: themeColor.bgColor,
  },
  inputCon: {
    marginTop: scale(16),
    backgroundColor: '#fff',
    borderRadius: scale(5),
    width: scale(328),
  },
  inputStyle: {},
});
