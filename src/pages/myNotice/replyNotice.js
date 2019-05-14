import React from 'react';
import { View, StyleSheet, TouchableOpacity, TextInput, Image, Keyboard, Text } from 'react-native';
import Header from '@/components/Header';
import CommonText from '@/components/AppText/CommonText';
import { themeColor, themeLayout, scale, themeSize } from '@/config';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';
import Loading from '@/components/Loading/loading';
import SecondaryText from '@/components/AppText/SecondaryText';

class ReplyNotice extends React.Component {
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

  handleSubmitReply = () => {
    const { textValue } = this.state;
    const { dispatch, navigation } = this.props;
    const itemData = navigation.getParam('itemData');
    if (!textValue.length) return null;
    const data = {
      type: itemData.type,
      content_id: itemData.content_id,
      content: textValue,
    };
    if (itemData.parent_id) {
      data.parent_id = itemData.parent_id;
    }
    Keyboard.dismiss();
    dispatch({
      type: 'comment/submitCommentEffect',
      payload: data,
      successFn: () => navigation.pop(),
    });
  };

  renderHeaderRight = () => {
    const { textValue } = this.state;
    const disabled = !textValue.length;
    return (
      <TouchableOpacity
        activeOpacity={disabled ? 0.5 : 0.2}
        onPress={this.handleSubmitReply}
        style={[styles.headerTextCon]}
      >
        <CommonText style={styles.headerText(disabled)}>发送</CommonText>
      </TouchableOpacity>
    );
  };

  renderFooter = () => {
    const { isShowKeyBoard } = this.state;
    if (isShowKeyBoard) {
      return (
        <View style={styles.footerUp}>
          <TouchableOpacity />
          <TouchableOpacity>
            <Image style={styles.bigLabel} source={{ uri: myImages.btnFacial }} />
          </TouchableOpacity>
        </View>
      );
    }
  };

  render() {
    const { textValue, isShowKeyBoard } = this.state;
    const { navigation, submitLoading } = this.props;
    console.log('%cnavigation:', 'color: #0e93e0;background: #aaefe5;', navigation);
    const itemData = navigation.getParam('itemData');
    return (
      <View style={styles.container}>
        <Header
          navigation={navigation}
          title='回复评论'
          rightComponent={this.renderHeaderRight()}
        />
        {submitLoading && <Loading />}
        <View style={styles.pageBody}>
          <TextInput
            autoFocus
            ref={ref => (this.refInput = ref)}
            style={styles.inputStyle(isShowKeyBoard)}
            onChangeText={this.handleChangeText}
            value={textValue}
            multiline
            placeholder='写评论'
            placeholderTextColor='#c5c5c5'
          />
          <View style={styles.replyPersonCon}>
            <Text numberOfLines={2} style={styles.replyPersonText}>
              <CommonText style={styles.atText}>@{itemData.nick_name}:</CommonText>
              <SecondaryText>{itemData.content}</SecondaryText>
            </Text>
          </View>
          {this.renderFooter()}
        </View>
      </View>
    );
  }
}

const mapStateToProps = ({ recommend, loading }) => ({
  recommend,
  submitLoading: loading.effects['recommend/submitXShareEffect'],
});

export default connect(mapStateToProps)(ReplyNotice);

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
        ? themeSize.screenHeight - scale(500)
        : themeSize.screenHeight - scale(500),
      lineHeight: scale(22),
      textAlignVertical: 'top',
    };
  },
  replyPersonCon: {
    ...themeLayout.flex('row', 'flex-start'),
    backgroundColor: themeColor.bgColor,
    ...themeLayout.padding(scale(9), scale(16), scale(10)),
    maxHeight: scale(70),
  },
  replyPersonText: {
    lineHeight: scale(24),
  },
  atText: {
    color: themeColor.font.at,
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
  bigLabel: {
    width: scale(29),
    height: scale(29),
  },
});
