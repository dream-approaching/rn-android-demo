import React from 'react';
import { View, StyleSheet } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, themeCatColor } from '@/config';
import CommonText from '@/components/AppText/Cat/CommonText';
import { OpenRnActivity } from '@/components/NativeModules';
import ImageWithDefault from '@/components/ImageWithDefault';
import { SEARCH_TYPE } from '@/config/constants';

export default class extends React.PureComponent {
  gotoArticleDetail = () => {
    const { itemData } = this.props;
    if (+itemData.type === SEARCH_TYPE.chat) {
      OpenRnActivity('detailChat', JSON.stringify({ contentId: itemData.id }));
    } else {
      OpenRnActivity('detailWebview', JSON.stringify({ webUrl: itemData.url_content }));
    }
  };

  render() {
    const { itemData, islastOne } = this.props;
    return (
      <TouchableNativeFeedback onPress={this.gotoArticleDetail} notOut tapArea={1}>
        <View style={styles.container(islastOne)}>
          <ImageWithDefault style={styles.leftImg} source={{ uri: itemData.img }} />
          <View style={styles.itemRight}>
            <CommonText numberOfLines={2} style={styles.articleTitle}>
              {itemData.title}
            </CommonText>
          </View>
        </View>
      </TouchableNativeFeedback>
    );
  }
}

const styles = StyleSheet.create({
  container: isLastOne => {
    const obj = {
      flex: 1,
      ...themeLayout.flex('row', 'space-between'),
      ...themeLayout.padding(16, 0),
    };
    return isLastOne ? obj : { ...obj, ...themeLayout.borderSide() };
  },
  leftImg: {
    width: 57,
    height: 57,
    backgroundColor: themeCatColor.bgF4,
  },
  itemRight: {
    flex: 1,
    marginLeft: 18,
    ...themeLayout.flex('column', 'space-between', 'flex-start'),
  },
  articleTitle: {
    fontSize: 15,
    lineHeight: 24,
  },
});
