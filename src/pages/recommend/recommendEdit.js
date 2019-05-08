import React from 'react';
import { View, StyleSheet, TouchableOpacity, TextInput, Image, Keyboard } from 'react-native';
import Header from '@/components/Header';
import CommonText from '@/components/AppText/CommonText';
import { themeColor, themeLayout, scale, themeSize } from '@/config';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';
import { clearRepeatArr } from '@/utils/utils';
import LabelBtn from './components/labelBtn';

class RecommendEdit extends React.Component {
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
    this.props.dispatch({
      type: 'recommend/saveSelectedLabelList',
      payload: [],
    });
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

  handleSubmitShare = () => {
    const { textValue } = this.state;
    const { dispatch } = this.props;
    const disabled = !textValue.length;
    if (disabled) return null;
    console.log('handleSubmitShare', 'handleSubmitShare');
    dispatch({
      type: 'recommend/submitXShareEffect',
      payload: '需要提交的数据',
    });
  };

  handleDeleteLabel = item => {
    const { recommend, dispatch } = this.props;
    dispatch({
      type: 'recommend/saveSelectedLabelList',
      payload: clearRepeatArr(recommend.selectedLabel, [item]),
    });
  };

  gotoAddLabel = () => {
    const { navigation } = this.props;
    navigation.navigate('AddLabel');
  };

  renderHeaderRight = () => {
    const { textValue } = this.state;
    const disabled = !textValue.length;
    return (
      <TouchableOpacity
        activeOpacity={disabled ? 0.5 : 0.2}
        onPress={this.handleSubmitShare}
        style={[styles.headerTextCon]}
      >
        <CommonText style={styles.headerText(disabled)}>发表</CommonText>
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
    const { recommend } = this.props;
    return (
      <View style={styles.footerCon}>
        <View style={styles.footerTip}>
          <Image style={styles.smallLabel} source={{ uri: myImages.btnLabel }} />
          <CommonText style={styles.tipText}>添加标签（最多可添加3个）</CommonText>
        </View>
        <View style={styles.lableCon}>
          {recommend.selectedLabel.map(item => {
            return (
              <LabelBtn pressAction={() => this.handleDeleteLabel(item)} key={item.id}>
                {item.label}
              </LabelBtn>
            );
          })}
          {recommend.selectedLabel.length < 3 && <LabelBtn pressAction={this.gotoAddLabel} empty />}
        </View>
      </View>
    );
  };

  render() {
    const { textValue, isShowKeyBoard } = this.state;
    const { navigation } = this.props;
    return (
      <View style={styles.container}>
        <Header navigation={navigation} title="提交" rightComponent={this.renderHeaderRight()} />
        <View style={styles.pageBody}>
          <TextInput
            selectable
            ref={ref => (this.refInput = ref)}
            style={styles.inputStyle(isShowKeyBoard)}
            onChangeText={this.handleChangeText}
            value={textValue}
            multiline
            placeholder="大家都在等着你的分享呢&#10;认真写下应用值得推荐的理由&#10;会更容易被推荐哦"
            placeholderTextColor="#c5c5c5"
          />
          {this.renderFooter()}
        </View>
      </View>
    );
  }
}

const mapStateToProps = ({ recommend }) => ({ recommend });

export default connect(mapStateToProps)(RecommendEdit);

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  pageBody: {
    flex: 1,
    ...themeLayout.padding(scale(0), scale(12)),
  },
  headerTextCon: {
    ...themeLayout.flex('row', 'flex-end'),
  },
  headerText: disabled => {
    return {
      color: themeColor.primaryColor,
      opacity: disabled ? 0.5 : 1,
    };
  },
  inputStyle: isShowKeyBoard => {
    return {
      color: themeColor.font.black,
      fontSize: themeSize.font.common,
      height: isShowKeyBoard
        ? themeSize.screenHeight - scale(400)
        : themeSize.screenHeight - scale(200),
      lineHeight: scale(22),
      textAlignVertical: 'top',
    };
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
