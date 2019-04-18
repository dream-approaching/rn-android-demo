import React from 'react';
import { View } from 'react-native';
import MenuItem from './MenuItem';

export default class MenuList extends React.PureComponent {
  render() {
    const { menu } = this.props;
    return (
      <View>
        {menu.map(item => {
          return <MenuItem key={item.title} item={item} />;
        })}
      </View>
    );
  }
}
