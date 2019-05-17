import React from 'react';
import Router from './router';

class Main extends React.Component {
  render() {
    return <Router screenProps={this.props} />;
  }
}
export default Main;
