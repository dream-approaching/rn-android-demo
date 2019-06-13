import React from 'react';
import { View, ImageBackground, StyleSheet } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, themeCatColor } from '@/config';
import SecondaryText from '@/components/AppText/Cat/SecondaryText';
import SmallText from '@/components/AppText/Cat/SmallText';
import CommonText from '@/components/AppText/Cat/CommonText';
import myImages from '@/utils/myImages';
import { OpenActivity } from '@/components/NativeModules';
import ImageWithDefault from '@/components/ImageWithDefault';

export default class extends React.PureComponent {
  gotoAppDetail = itemData => {
    OpenActivity.openAppDetails(itemData.id);
  };

  render() {
    const { itemData, islastOne } = this.props;
    return (
      <TouchableNativeFeedback notOut tapArea={1} onPress={() => this.gotoAppDetail(itemData)}>
        <View style={styles.container(islastOne)}>
          <ImageWithDefault style={styles.appIcon} source={{ uri: itemData.app_logo }} />
          <View style={styles.itemRight}>
            <CommonText style={styles.appTitle}>{itemData.app_name_cn}</CommonText>
            <SecondaryText style={styles.desc} numberOfLines={1}>
              {itemData.app_short_desc}
            </SecondaryText>
            <View>
              {itemData.is_high_quality === '1' && (
                <ImageBackground
                  resizeMode='contain'
                  source={{ uri: myImages.bgLabel }}
                  style={styles.bgLabel}
                >
                  <SmallText style={styles.labelText}>精品</SmallText>
                </ImageBackground>
              )}
            </View>
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
      ...themeLayout.flex('row', 'space-between', 'flex-start'),
      ...themeLayout.padding(16, 0),
    };
    return isLastOne ? obj : { ...obj, ...themeLayout.borderSide() };
  },
  appIcon: {
    width: 57,
    height: 57,
    borderRadius: 10,
    backgroundColor: themeCatColor.bgF4,
  },
  itemRight: {
    flex: 1,
    marginLeft: 18,
    height: 57,
    ...themeLayout.flex('column', 'space-between', 'flex-start'),
  },
  desc: {
    flexWrap: 'wrap',
  },
  bgLabel: {
    width: 29,
    height: 14,
    ...themeLayout.flex(),
  },
  appTitle: {
    fontSize: 16,
  },
  labelText: {
    fontSize: 8,
    color: '#fff',
  },
});
