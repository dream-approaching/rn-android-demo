import React from 'react';
import { View, StyleSheet } from 'react-native';
import { scale, themeLayout, themeSize } from '@/config';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import LargerText from '@/components/AppText/LargerText';
import SecondaryText from '@/components/AppText/SecondaryText';
import ImageWithDefault from '@/components/ImageWithDefault';

export default class CommentPage extends React.PureComponent {
  static navigationOptions = {
    header: null,
  };

  render() {
    const { data } = this.props;
    return (
      <View style={styles.container}>
        <TouchableNativeFeedback>
          <ImageWithDefault style={styles.coverImg} source={{ uri: data.img }} />
        </TouchableNativeFeedback>
        <View style={styles.titleCon}>
          <LargerText style={styles.title}>{data.title}</LargerText>
          <SecondaryText style={styles.desc}>{data.content}</SecondaryText>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    ...themeLayout.borderSide('Bottom', '#eee', scale(4)),
  },
  coverImg: {
    width: themeSize.screenWidth,
    height: scale(211),
  },
  titleCon: {
    ...themeLayout.padding(scale(12), scale(16), scale(12)),
  },
  title: {
    fontSize: scale(21),
  },
  desc: {
    fontSize: scale(15),
    marginTop: scale(6),
  },
});
