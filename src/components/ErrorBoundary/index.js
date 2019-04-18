import React from 'react';

export default class ErrorBoundary extends React.Component {
  state = { error: null };

  // static getDerivedStateFromError(error) {
  //   console.log('error.componentStack:', error.componentStack);
  //   return { error };
  // }

  componentDidCatch(error, errorInfo) {
    console.log('%cerror:', 'color: #0e93e0;background: #aaefe5;', error.message);
    console.log('%cerrorInfo:', 'color: #0e93e0;background: #aaefe5;', errorInfo.componentStack);
    this.setState({
      error: error,
      errorInfo: errorInfo
    });
  }

  render() {
    const { error } = this.state;
    console.log('errorBoundary render');
    if (error) {
      return (
        <div>
          <h3>程序异常</h3>
          <details style={{ whiteSpace: 'pre-wrap' }}>
            {this.state.error && this.state.error.toString()}
          </details>
        </div>
      );
    }
    return this.props.children;
  }
}
