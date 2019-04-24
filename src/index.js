import React from 'react';
// import { AppRegistry } from 'react-native'
import codePush from 'react-native-code-push';
import dva from '@/utils/dva';
import globalModel from '@/models/global';
import Router from './router';

const app = dva({
  initialState: {},
  models: [globalModel],
  onError(e) {
    console.log('onError', e);
  }
});

const codePushOptions = {
  checkFrequency: codePush.CheckFrequency.ON_APP_RESUME,
  installMode: codePush.InstallMode.ON_NEXT_RESTART
};

const Root = codePush(codePushOptions)(Router);

class Apps extends React.Component {
  render() {
    const App = app.start(<Root nativeProps={this.props} />);
    return <App />;
  }
}

export default Apps;
