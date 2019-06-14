import React from 'react';
import { View, TextInput, StyleSheet, Image } from 'react-native';
import { scale, themeLayout, themeColor } from '@/config';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import myImages from '@/utils/myImages';
import CommonText from '@/components/AppText/CommonText';

export default class CommentPage extends React.PureComponent {
  static navigationOptions = {
    header: null,
  };

  render() {
    const {
      cancelSearchAction,
      searchKey,
      changeSearchKeyAction,
      cleanTextAction = () => {},
    } = this.props;
    return (
      <View style={styles.container}>
        <View style={styles.inputCon}>
          <Image style={styles.leftIcon} source={{ uri: myImages.search }} />
          <TextInput
            ref={ref => (this.refInput = ref)}
            style={styles.inputStyle}
            onChangeText={changeSearchKeyAction}
            value={searchKey}
            placeholder='热门话题搜索'
            placeholderTextColor={themeColor.placeholderColor}
            returnKeyType='search'
          />
          {searchKey.length > 0 && (
            <TouchableNativeFeedback onPress={cleanTextAction}>
              <Image style={styles.rightIcon} source={{ uri: myImages.x }} />
            </TouchableNativeFeedback>
          )}
        </View>
        <TouchableNativeFeedback tapArea={1} onPress={cancelSearchAction}>
          <View style={styles.cancelBtn}>
            <CommonText style={styles.cancelText}>取消</CommonText>
          </View>
        </TouchableNativeFeedback>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    height: scale(68),
    ...themeLayout.borderSide('Bottom'),
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.padding(0, 0, 0, scale(20)),
    paddingTop: scale(20),
  },
  inputCon: {
    flex: 1,
    height: scale(33),
    ...themeLayout.border(),
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.padding(scale(5), scale(3), scale(5), scale(5)),
    borderRadius: scale(17),
    backgroundColor: '#f3f3f3',
  },
  inputStyle: {
    flex: 1,
    color: themeColor.font.secondary,
    padding: 0,
    borderRadius: scale(5),
  },
  leftIcon: {
    width: scale(23),
    height: scale(23),
    marginRight: scale(5),
  },
  rightIcon: {
    width: scale(10),
    height: scale(10),
    marginRight: scale(10),
  },
  cancelBtn: {
    ...themeLayout.padding(0, scale(20), 0, scale(14)),
  },
  cancelText: {
    color: '#777',
    fontSize: 16,
  },
});
