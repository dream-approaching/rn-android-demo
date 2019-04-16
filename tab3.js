import React from 'react';
import { AppRegistry, StyleSheet, Text, View } from 'react-native';

class HelloWorld extends React.Component {
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.hello}>Hello, Worlds tab3</Text>
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
