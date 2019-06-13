import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import { indexData } from '@/config/fakeData';
import { WaterfallList } from 'react-native-largelist-v3';
import CommonText from '@/components/AppText/Cat/CommonText';
import SimpleFooter from '@/components/ScrollFooter/SimpleFooter';
import { themeLayout, scale } from '@/config';
import { connect } from '@/utils/dva';

class CatHome extends React.PureComponent {
  static navigationOptions = {
    header: null,
  };

  state = {};

  componentDidMount() {
    // const { dispatch } = this.props;
    // dispatch({
    //   type: 'catHome/queryCatHomeListEffect',
    //   payload: {
    //     pagesize: 5,
    //   },
    // });
  }

  layout = async (item, { nativeEvent }) => {
    const { height } = nativeEvent.layout;
    console.log('%cheight:', 'color: #0e93e0;background: #aaefe5;', height);
    this.mainBodyHeight = height;
    this.setState({
      [`${item.id}_height`]: +height,
    });
  };

  render() {
    return (
      <View style={styles.container}>
        <View>
          <WaterfallList
            data={indexData}
            heightForItem={item =>
              scale(180) / item.ratio + 20 + 1 * this.state[`${item.id}_height`] || 1
            }
            numColumns={2}
            loadingFooter={SimpleFooter}
            onLoading={() => console.log(123)}
            renderItem={item => {
              return (
                <View style={styles.itemCon}>
                  <Image
                    resizeMode='cover'
                    style={{ width: scale(180), height: scale(180) / item.ratio }}
                    source={{ uri: item.file }}
                  />
                  <CommonText onLayout={nativeEvent => this.layout(item, nativeEvent)}>
                    {item.title}
                  </CommonText>
                </View>
              );
            }}
          />
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  itemCon: {
    ...themeLayout.flex('column', 'flex-start', 'flex-start'),
    marginHorizontal: scale(3.5),
    marginBottom: 20,
  },
});

const mapStateToProps = ({ catHome, loading }) => ({
  catHome,
  loading: loading.effects['catHome/queryCatHomeListEffect'],
});

export default connect(mapStateToProps)(CatHome);
