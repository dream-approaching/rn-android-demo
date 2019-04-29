import React from 'react';
// import { AppRegistry } from 'react-native'
import codePush from 'react-native-code-push';
import dva from '@/utils/dva';
import globalModel from '@/models/global';
import recommendModel from '@/models/recommend';
// import createLogger from 'redux-logger';
import Router from './router';

const app = dva({
  initialState: {},
  models: [globalModel, recommendModel],
  onError(e) {
    console.log('onError', e);
  },
  // onAction: createLogger,
});

const codePushOptions = {
  checkFrequency: codePush.CheckFrequency.ON_APP_RESUME,
  installMode: codePush.InstallMode.ON_NEXT_RESTART,
};

const Root = codePush(codePushOptions)(Router);

/* eslint no-undef: 0 */
// if (__DEV__) {
//   global.XMLHttpRequest = global.originalXMLHttpRequest
//     ? global.originalXMLHttpRequest
//     : global.XMLHttpRequest;
//   global.FormData = global.originalFormData ? global.originalFormData : global.FormData;
//   global.Blob = global.originalBlob ? global.originalBlob : global.Blob;
//   global.FileReader = global.originalFileReader ? global.originalFileReader : global.FileReader;
// }
class Apps extends React.Component {
  render() {
    const App = app.start(<Root nativeProps={this.props} />);
    return <App />;
  }
}

export default Apps;
