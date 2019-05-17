import React from 'react';
import { View, ImageBackground, StyleSheet } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, scale, themeColor } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import SmallText from '@/components/AppText/SmallText';
import CommonText from '@/components/AppText/CommonText';
import myImages from '@/utils/myImages';
import { OpenActivity } from '@/components/NativeModules';
import ImageWithDefault from '@/components/ImageWithDefault';

export default class extends React.PureComponent {
  gotoAppDetail = itemData => {
    OpenActivity.openAppDetails(itemData.mydata ? itemData.mydata.id : itemData.app_info);
  };

  render() {
    const { itemData, islastOne } = this.props;
    return (
      <TouchableNativeFeedback onPress={() => this.gotoAppDetail(itemData)}>
        <View style={styles.container(islastOne)}>
          <ImageWithDefault style={styles.appIcon} source={{ uri: itemData.app_logo }} />
          <View style={styles.itemRight}>
            <CommonText style={styles.appTitle}>{itemData.app_name_cn}</CommonText>
            <SecondaryText style={styles.desc} numberOfLines={1}>
              {itemData.app_short_desc}
            </SecondaryText>
            <View>
              {itemData.is_high_quality && (
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
      ...themeLayout.margin(0, 0, 0, scale(8)),
      ...themeLayout.padding(scale(16), 0),
    };
    return isLastOne ? obj : { ...obj, ...themeLayout.borderSide() };
  },
  appIcon: {
    width: scale(57),
    height: scale(57),
    borderRadius: scale(10),
    backgroundColor: themeColor.bgF4,
  },
  itemRight: {
    flex: 1,
    marginLeft: scale(18),
    height: scale(57),
    ...themeLayout.flex('column', 'space-between', 'flex-start'),
  },
  desc: {
    flexWrap: 'wrap',
  },
  bgLabel: {
    width: scale(29),
    height: scale(14),
    ...themeLayout.flex(),
  },
  appTitle: {
    fontSize: scale(16),
  },
  labelText: {
    fontSize: scale(8),
    color: '#fff',
  },
});
