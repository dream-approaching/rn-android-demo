import React from 'react';
import { View } from 'react-native';
import { WebView } from 'react-native-webview';
import { themeSize } from '@/config';
import Loading from '@/components/Loading/loading';

class DetailWebview extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    loading: false,
    err: false,
  };

  componentDidMount() {
    const { screenProps } = this.props;
    console.log('%cscreenProps:', 'color: #0e93e0;background: #aaefe5;', screenProps);
    this.webUrl = JSON.parse(screenProps.nativeProps.params).webUrl;
  }

  render() {
    const { loading, err } = this.state;
    console.log('%cloading, err:', 'color: #0e93e0;background: #aaefe5;', loading, err);
    // if (!this.webUrl) return <NoData text='暂无内容' />;
    this.webUrl &&
      console.log('%cthis.webUrl:', 'color: #0e93e0;background: #aaefe5;', this.webUrl);
    return (
      <View
        style={{
          height: themeSize.screenHeight,
        }}
      >
        <WebView
          allowFileAccess
          scalesPageToFit
          startInLoadingState
          renderLoading={() => <Loading />}
          originWhitelist={['*']}
          onError={() => this.setState({ err: 'connection failed please try again!' })}
          onLoadStart={() => this.setState({ loading: true })}
          onLoadEnd={() => this.setState({ loading: false })}
          source={{ uri: this.webUrl }}
        />
      </View>
    );
  }
}

export default DetailWebview;
