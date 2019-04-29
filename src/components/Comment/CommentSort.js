import React, { Fragment } from 'react';
import { View, StyleSheet } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { scale, themeLayout } from '@/config';
import SmallText from '@/components/AppText/SmallText';

export default class CommentPage extends React.Component {
  render() {
    const tab = [
      {
        text: '最新',
        type: 'new',
      },
      {
        text: '最热',
        type: 'hot',
      },
    ];
    const { changeSortAction, activeTab, activeStyle } = this.props;
    return (
      <View style={styles.rightTab}>
        {tab.map((item, index) => {
          const activeStyled =
            activeTab === item.type
              ? { color: '#303030', fontSize: scale(15), ...activeStyle }
              : {};
          return (
            <Fragment key={item.text}>
              <TouchableOpacity style={styles.tabText} onPress={() => changeSortAction(item)}>
                <SmallText style={activeStyled}>{item.text}</SmallText>
              </TouchableOpacity>
              {(index !== tab.length - 1 && <SmallText style={styles.tabText}>|</SmallText>) ||
                null}
            </Fragment>
          );
        })}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  rightTab: {
    ...themeLayout.flex('row', 'flex-end', 'flex-end'),
  },
  tabText: {
    ...themeLayout.padding(scale(1)),
  },
});
