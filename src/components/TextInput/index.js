import React from 'react';
import { StyleSheet, TextInput, View, Image } from 'react-native';
import { themeLayout, themeCatColor } from '@/config';
import TouchableNativeFeedback from '../Touchable/TouchableNativeFeedback';

export default class MyTextInput extends React.Component {
  focus = () => this.refInput.focus();

  blur = () => this.refInput.blur();

  render() {
    const {
      style,
      leftIcon,
      rightIcon,
      conStyle,
      rightIconAction = () => {},
      ...rest
    } = this.props;
    return (
      <View style={[styles.inputCon, conStyle]}>
        {leftIcon && <Image style={styles.leftIcon} source={{ uri: leftIcon }} />}
        <TextInput
          ref={ref => (this.refInput = ref)}
          style={[styles.inputStyle, style]}
          placeholderTextColor={themeCatColor.placeholderColor}
          {...rest}
        />
        {rightIcon && (
          <TouchableNativeFeedback onPress={rightIconAction} notOut tapArea={2}>
            <Image style={styles.rightIcon} source={{ uri: rightIcon }} />
          </TouchableNativeFeedback>
        )}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  inputCon: {
    ...themeLayout.flex('row', 'flex-start'),
    backgroundColor: '#fff',
    paddingRight: 15,
  },
  leftIcon: {
    width: 24,
    height: 24,
    marginLeft: 8,
    marginRight: 4,
  },
  rightIcon: {
    width: 14,
    height: 14,
  },
  inputStyle: {
    color: themeCatColor.font.black,
    flex: 1,
  },
});
