import React from 'react';
import { View, Image, StyleSheet } from 'react-native';
import { scale, themeLayout, themeSize } from '@/config';
import { TouchableOpacity } from 'react-native-gesture-handler';
import LargerText from '@/components/AppText/LargerText';
import SecondaryText from '@/components/AppText/SecondaryText';
import myImages from '@/utils/myImages';

export default class CommentPage extends React.PureComponent {
  static navigationOptions = {
    header: null,
  };

  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity>
          <Image style={styles.coverImg} source={{ uri: myImages.attention }} />
        </TouchableOpacity>
        <View style={styles.titleCon}>
          <LargerText style={styles.title}>有那些小众却不熟主流的音乐App</LargerText>
          <SecondaryText style={styles.desc}>来，畅所欲言吧</SecondaryText>
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
