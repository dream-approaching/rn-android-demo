import React from 'react';
import { Image } from 'react-native';
import myImages from '@/utils/myImages';

export default class ImageWithDefault extends React.PureComponent {
  state = { error: false };

  loadError = () => {
    this.setState({ error: true });
  };

  render() {
    const { error } = this.state;
    const { source, defaultSource = { uri: myImages.defaultHeader }, ...rest } = this.props;
    const image = error || !source.uri ? defaultSource : source;
    // const image = error || showDefault ? defaultSource : source.uri ? source : defaultSource;
    return (
      <Image
        onLoad={this.loadEnd}
        // loadingIndicatorSource={defaultSource}
        onError={this.loadError}
        source={image}
        {...rest}
      />
    );
  }
}
