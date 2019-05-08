import React from 'react';
import { View, StyleSheet, TouchableOpacity, TextInput, Image, Keyboard } from 'react-native';
import Header from '@/components/Header';
import CommonText from '@/components/AppText/CommonText';
import { themeColor, themeLayout, scale } from '@/config';
import SpringScrollView from '@/components/SpringScrollView';
import myImages from '@/utils/myImages';
import LabelBtn from './components/labelBtn';

export default class extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    textValue: '',
    isShowKeyBoard: false,
  };

  componentDidMount() {
    this.keyboardDidShowListener = Keyboard.addListener('keyboardDidShow', this.keyboardDidShow);
    this.keyboardDidHideListener = Keyboard.addListener('keyboardDidHide', this.keyboardDidHide);
  }

  componentWillUnmount() {
    this.keyboardDidShowListener.remove();
    this.keyboardDidHideListener.remove();
  }

  keyboardDidShow = () => {
    this.setState({
      isShowKeyBoard: true,
    });
  };

  keyboardDidHide = () => {
    this.setState({
      isShowKeyBoard: false,
    });
  };

  handleChangeText = value => {
    this.setState({
      textValue: value,
    });
  };

  gotoAddLabel = () => {
    const { navigation } = this.props;
    navigation.navigate('AddLabel');
  };

  renderHeaderRight = () => {
    return (
      <TouchableOpacity style={styles.headerTextCon}>
        <CommonText style={styles.headerText}>发表</CommonText>
      </TouchableOpacity>
    );
  };

  renderFooter = () => {
    const { isShowKeyBoard } = this.state;
    if (isShowKeyBoard) {
      return (
        <View style={styles.footerUp}>
          <TouchableOpacity onPress={this.gotoAddLabel}>
            <Image style={styles.bigLabel} source={{ uri: myImages.btnLabel }} />
          </TouchableOpacity>
          <TouchableOpacity>
            <Image style={styles.bigLabel} source={{ uri: myImages.btnFacial }} />
          </TouchableOpacity>
        </View>
      );
    }
    return (
      <View style={styles.footerCon}>
        <View style={styles.footerTip}>
          <Image style={styles.smallLabel} source={{ uri: myImages.btnLabel }} />
          <CommonText style={styles.tipText}>添加标签（最多可添加3个）</CommonText>
        </View>
        <View style={styles.lableCon}>
          <LabelBtn>工具工具</LabelBtn>
          <LabelBtn pressAction={this.gotoAddLabel} empty />
        </View>
      </View>
    );
  };

  render() {
    const { textValue } = this.state;
    const { navigation } = this.props;
    return (
      <View style={styles.container}>
        <Header navigation={navigation} title="提交" rightComponent={this.renderHeaderRight()} />
        <View style={styles.pageBody}>
          <SpringScrollView>
            <TextInput
              ref={ref => (this.refInput = ref)}
              style={styles.inputStyle}
              onChangeText={this.handleChangeText}
              value={textValue}
              multiline
              placeholder="ceshi"
              placeholderTextColor={themeColor.placeholderColor}
            />
          </SpringScrollView>
          {this.renderFooter()}
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  pageBody: {
    flex: 1,
    ...themeLayout.padding(scale(16), scale(16), scale(20)),
  },
  headerTextCon: {
    ...themeLayout.flex('row', 'flex-end'),
  },
  headerText: {
    color: themeColor.primaryColor,
  },
  inputStyle: {
    flex: 1,
    ...themeLayout.border(),
  },
  footerCon: {
    bottom: 0,
    left: scale(16),
    right: scale(16),
    height: scale(118),
    position: 'absolute',
    zIndex: 1000,
    paddingTop: scale(10),
    ...themeLayout.borderSide('Top', themeColor.borderColor, scale(2)),
  },
  footerUp: {
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.borderSide('Top'),
    ...themeLayout.padding(0, scale(22)),
    bottom: 0,
    left: scale(0),
    right: scale(0),
    height: scale(48),
    position: 'absolute',
  },
  footerTip: {
    ...themeLayout.flex('row', 'flex-start'),
  },
  smallLabel: {
    width: scale(14),
    height: scale(14),
    marginRight: scale(5),
  },
  bigLabel: {
    width: scale(29),
    height: scale(29),
  },
  tipText: {
    color: themeColor.font.secondary,
  },
  lableCon: {
    marginTop: scale(12),
    ...themeLayout.flex('row', 'flex-start'),
  },
});
