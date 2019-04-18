import React from 'react';
import { createStackNavigator, createAppContainer } from 'react-navigation';
import Chat from '../pages/chat/chat';
import ErrorBoundary from '../components/ErrorBoundary';

const ChatNavigator = createStackNavigator(
  {
    Chat
  },
  {
    initialRouteName: 'Chat'
  }
);

const ChatContainer = createAppContainer(ChatNavigator);

export default class extends React.Component {
  render() {
    return (
      <ErrorBoundary>
        <ChatContainer />
      </ErrorBoundary>
    );
  }
}
