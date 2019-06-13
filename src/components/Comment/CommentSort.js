import React, { Fragment } from 'react';
import { View, StyleSheet } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout } from '@/config';
import SmallText from '@/components/AppText/Cat/SmallText';

export default class CommentSort extends React.Component {
  render() {
    const tab = [
      {
        text: '最热',
        type: 1,
      },
      {
        text: '最新',
        type: 2,
      },
    ];
    const { changeSortAction, activeTab, activeStyle } = this.props;
    return (
      <View style={styles.rightTab}>
        {tab.map((item, index) => {
          const activeStyled =
            activeTab === item.type
              ? { color: '#707070', fontSize: 15, ...activeStyle }
              : {};
          return (
            <Fragment key={item.text}>
              <TouchableNativeFeedback tapArea={5} onPress={() => changeSortAction(item)}>
                <View style={styles.tabText}>
                  <SmallText style={activeStyled}>{item.text}</SmallText>
                </View>
              </TouchableNativeFeedback>
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
    ...themeLayout.padding(1),
  },
});
