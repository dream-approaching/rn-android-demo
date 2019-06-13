import React from 'react';
import { View, Text } from 'react-native';

export default class ErrorBoundary extends React.Component {
  state = { error: null };

  // static getDerivedStateFromError(error) {
  //   console.log('error.componentStack:', error.componentStack);
  //   return { error };
  // }

  // componentDidCatch(error, errorInfo) {
  //   console.log('%cerror:', 'color: #0e93e0;background: #aaefe5;', error.message);
  //   console.log('%cerrorInfo:', 'color: #0e93e0;background: #aaefe5;', errorInfo.componentStack);
  //   this.setState({
  //     error,
  //   });
  //   // 告诉原生有异常
  // }

  render() {
    const { error } = this.state;
    if (error) {
      return (
        <View>
          <Text>程序异常</Text>
          <Text style={{ display: 'flex', flexWrap: 'wrap' }}>
            {this.state.error && this.state.error.toString()}
          </Text>
        </View>
      );
    }
    return this.props.children;
  }
}
