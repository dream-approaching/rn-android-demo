import React from 'react';
import { View, Text, Button } from 'react-native';
import DeviceInfo from 'react-native-device-info';
import { liyi } from '../../components/NativeModules/index';

export default class Mine extends React.Component {
  static navigationOptions = {
    title: 'Mine'
  };

  state = {
    brand: '123'
  };

  componentDidMount() {
    this.setState({
      brand: DeviceInfo.getBrand()
    });
    console.log('%cliyi:', 'color: #0e93e0;background: #aaefe5;', liyi);
  }

  render() {
    console.log('Mine render');
    const { brand } = this.state;
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>Mine Screen</Text>
        <Text>12345_{brand}</Text>
        <Button title='打开原生界面' onPress={() => liyi.open(liyi.KEY_ONE)} />
        <Button
          title='设置'
          onPress={() => this.props.navigation.navigate('Setting')}
        />
      </View>
    );
  }
}
