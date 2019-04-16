import React from "react";
import { View, Text, Button } from "react-native";
import { createStackNavigator, createAppContainer } from "react-navigation";
import * as Animatable from "react-native-animatable";
import DeviceInfo from "react-native-device-info";

class HomeScreen extends React.Component {
  componentDidMount() {
    console.log(
      "%cgetBrand():",
      "color: #0e93e0;background: #aaefe5;",
      DeviceInfo.getBrand()
    );
  }

  render() {
    console.log("HomeScreen render");
    return (
      <View style={{ flex: 1, alignItems: "center", justifyContent: "center" }}>
        <Text>Home Screen</Text>
        <Animatable.Text
          animation="slideOutLeft"
          iterationCount={1}
          direction="alternate"
        >
          Up and down you go
        </Animatable.Text>
        <Button
          title="Go to Details"
          onPress={() => this.props.navigation.navigate("Details")}
        />
      </View>
    );
  }
}

class DetailsScreen extends React.Component {
  render() {
    console.log("DetailsScreen render");
    return (
      <View style={{ flex: 1, alignItems: "center", justifyContent: "center" }}>
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
