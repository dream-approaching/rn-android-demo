import React from 'react';
import { View } from 'react-native';
import MenuItem from './MenuItem';

export default class MenuList extends React.PureComponent {
  render() {
    const { menu, style } = this.props;
    return (
      <View style={style}>
        {menu.map((item, index) => {
          return <MenuItem key={item.title} item={item} islast={index === menu.length - 1} />;
        })}
      </View>
    );
  }
}
