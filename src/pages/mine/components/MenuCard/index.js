import React from 'react';
import { View } from 'react-native';
import MenuCardItem from './MenuCardItem';

export default class MenuList extends React.PureComponent {
  render() {
    const { menu, style } = this.props;
    return (
      <View style={style}>
        {menu.map(item => {
          return <MenuCardItem key={item.title} item={item} />;
        })}
      </View>
    );
  }
}
