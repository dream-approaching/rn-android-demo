import React, { Fragment } from 'react';
import { View, Image, StyleSheet } from 'react-native';
import { scale, themeLayout, themeSize } from '@/config';
import LargerText from '@/components/AppText/LargerText';
import SmallText from '@/components/AppText/SmallText';
import { TouchableOpacity } from 'react-native-gesture-handler';

export default class CommentPage extends React.PureComponent {
  static navigationOptions = {
    header: null,
  };

  state = {
    activeTab: 'new',
  };

  handleChangeType = item => {
    this.setState({
      activeTab: item.type,
    });
  };

  render() {
    const { activeTab } = this.state;
    const tab = [
      {
        text: '最新',
        type: 'new',
      },
      {
        text: '最热',
        type: 'hot',
      },
    ];
    return (
      <View style={styles.container}>
        <View style={styles.leftIconCon}>
          <TouchableOpacity>
            <Image style={styles.leftIcon} source={{ uri: 'ic_button_del' }} />
          </TouchableOpacity>
        </View>
        <View style={styles.titleCon}>
          <LargerText style={styles.title}>65条评论</LargerText>
        </View>
        <View style={styles.rightTab}>
          {tab.map((item, index) => {
            const Text = activeTab === item.type ? LargerText : SmallText;
            return (
              <Fragment key={item.text}>
                <TouchableOpacity
                  style={styles.tabText}
                  onPress={() => this.handleChangeType(item)}
                >
                  <Text>{item.text}</Text>
                </TouchableOpacity>
                {(index !== tab.length - 1 && <SmallText>|</SmallText>) || null}
              </Fragment>
            );
          })}
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    height: scale(50),
    ...themeLayout.borderSide('Bottom'),
    ...themeLayout.flex('row'),
    ...themeLayout.padding(0, scale(16), 0, scale(6)),
    backgroundColor: '#fff',
  },
  titleCon: {
    ...themeLayout.flex('row'),
    flex: 1,
    marginLeft: -scale(20),
  },
  title: {
    fontSize: themeSize.font.superLarge,
  },
  leftIconCon: {
    flex: 1,
    ...themeLayout.padding(scale(10)),
  },
  leftIcon: {
    width: scale(14),
    height: scale(14),
  },
  rightTab: {
    flex: 1,
    ...themeLayout.flex('row', 'flex-end'),
  },
  tabText: {
    ...themeLayout.padding(scale(2)),
  },
});
