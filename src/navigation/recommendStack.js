import React from 'react';
import { createStackNavigator, createAppContainer } from 'react-navigation';
import Recommend from '../pages/recommend/recommend';

const RecommendNavigator = createStackNavigator(
  {
    Recommend
  },
  {
    initialRouteName: 'Recommend'
  }
);

const RecommendContainer = createAppContainer(RecommendNavigator);

export default class extends React.Component {
  render() {
    return <RecommendContainer />;
  }
}
