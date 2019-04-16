import React from 'react';
import { AppRegistry, StyleSheet, Text, View } from 'react-native';
import { scale, color, size, layout, isAndroid } from './src/config';

class HelloWorld extends React.Component {
  componentDidMount() {
    console.log('scale, color, size, layout, isAndroid:', scale, color, size, layout, isAndroid);
  }
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.hello}>Hello, Worlds tab2</Text>
      </View>
    );
  }
}
var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center'
  },
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10
  }
});

export default AppRegistry.registerComponent('MyReactNativeApp', () => HelloWorld);
