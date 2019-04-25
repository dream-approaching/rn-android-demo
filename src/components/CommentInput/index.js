import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { TextInput, TouchableOpacity } from 'react-native-gesture-handler';
import { scale, themeColor, themeLayout } from '@/config';

export default class CommentPage extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    textValue: '你觉得呢',
  };

  handleChangeText = text => {
    console.log('%ctext:', 'color: #0e93e0;background: #aaefe5;', text);
    this.setState({
      textValue: text,
    });
  };

  render() {
    const { textValue } = this.state;
    return (
      <View style={styles.inputCon}>
        <TouchableOpacity>
          <Image style={styles.leftIcon} source={{ uri: 'ic_input_back' }} />
        </TouchableOpacity>
        <TextInput
          style={styles.inputStyle}
          onChangeText={this.handleChangeText}
          value={textValue}
        />
        <TouchableOpacity>
          <Image style={styles.rightIcon} source={{ uri: 'ic_input_collection' }} />
        </TouchableOpacity>
        <TouchableOpacity>
          <Image style={styles.rightIcon} source={{ uri: 'ic_input_share' }} />
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  inputCon: {
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.padding(scale(9), scale(13)),
    elevation: 30,
    backgroundColor: '#fff',
    // shadowOffset: scale(20),
  },
  leftIcon: {
    width: scale(19),
    height: scale(19),
  },
  rightIcon: {
    width: scale(22),
    height: scale(22),
    ...themeLayout.margin(0, scale(8)),
  },
  inputStyle: {
    color: themeColor.font.secondary,
    ...themeLayout.border(),
    ...themeLayout.padding(scale(5), scale(10)),
    marginLeft: scale(10),
    height: scale(32),
    flex: 1,
  },
});
