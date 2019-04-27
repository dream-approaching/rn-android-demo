import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { LargeList } from 'react-native-largelist-v3';

export default class HeightEqualExample extends React.Component {
  sectionCount = 10;

  rowCount = 10;

  renderSection = section => {
    return (
      <View style={styles.section}>
        <Text>Section {section}</Text>
      </View>
    );
  };

  renderIndexPath = ({ section, row }) => {
    return (
      <View style={{ backgroundColor: 'lightblue' }}>
        <Text style={{ color: '#333' }}>
          Section {section} Row {row}
        </Text>
        <View style={styles.line} />
      </View>
    );
  };

  render() {
    const data = [];
    for (let section = 0; section < this.sectionCount; ++section) {
      const sContent = { items: [] };
      for (let row = 0; row < this.rowCount; ++row) {
        sContent.items.push(row);
      }
      data.push(sContent);
    }
    console.log('data', data);
    return (
      <LargeList
        style={styles.container}
        data={data}
        heightForSection={() => 50}
        renderSection={this.renderSection}
        heightForIndexPath={() => 50}
        renderIndexPath={this.renderIndexPath}
      />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  section: {
    flex: 1,
    backgroundColor: 'gray',
    justifyContent: 'center',
    alignItems: 'center',
  },
  row: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  line: {
    position: 'absolute',
    left: 0,
    right: 0,
    bottom: 0,
    height: 1,
    backgroundColor: '#EEE',
  },
});
