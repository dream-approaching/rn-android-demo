import { AppRegistry, YellowBox } from 'react-native';
import App from './src/index';

YellowBox.ignoreWarnings([
  'Remote debugger is in a background tab which may cause apps to perform slowly',
]);

AppRegistry.registerComponent('MyReactNativeAppthree', () => App);
