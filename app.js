import React from "react";
import { View, Text,  Button } from "react-native";
import { createStackNavigator, createAppContainer } from "react-navigation";
import DeviceInfo from "react-native-device-info";

class HomeScreen extends React.Component {
  state = {
    brand: '123'
  }

  componentDidMount() {
    this.setState({
      brand: DeviceInfo.getBrand()
    })
  }

  render() {
    console.log('HomeScreen render')
    const { brand } = this.state;
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>Home Screen</Text>
        <Text>123456_{brand}</Text>
        <Button
          title="Go to Details"
          onPress={() => this.props.navigation.navigate('Details')}
        />
      </View>
    );
  }
}

class DetailsScreen extends React.Component {
  render() {
    console.log('DetailsScreen render')
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>Details Screen</Text>
      </View>
    );
  }
}

const AppNavigator = createStackNavigator(
  {
    Home: HomeScreen,
    Details: DetailsScreen
  },
  {
    initialRouteName: "Home"
  }
);

const AppContainer = createAppContainer(AppNavigator);

export default class App extends React.Component {
  render() {
    return <AppContainer />;
  }
}
