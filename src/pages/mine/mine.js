import React from 'react';
import { ImageBackground, StyleSheet, View, Text, Button } from 'react-native';
import MenuList from '@/components/MenuList';
import ScrollView from '@/components/ScrollView';
import { OpenActivity } from '@/components/NativeModules';
import LargerText from '@/components/AppText/LargerText';
import { themeColor, scale, themeLayout } from '@/config';
import { connect } from '@/utils/dva';
import myImages from '@/utils/images';
import Avatar from './components/Avatar';
import MenuCard from './components/MenuCard';

class Mine extends React.Component {
  static navigationOptions = {
    header: null
  };

  componentDidMount() {
    console.log('Mine componentDidMount');
    console.log('%cMine componentDidMount:', 'color: #0e93e0;background: #aaefe5;', this.props);
  }

  handlePlus = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'global/testCountEffect'
    });
  };

  render() {
    const userData = {
      avatar:
        'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1298508432,4221755458&fm=26&gp=0.jpg',
      name: '夏冬冬21'
    };
    const cardMenu = [
      {
        title: '文章',
        icon: myImages.article,
        navigatePath: ''
      },
      {
        title: '应用',
        icon: myImages.application,
        navigatePath: ''
      },
      {
        title: '话题',
        icon: myImages.chat,
        navigatePath: ''
      }
    ];
    const listMenu = [
      {
        title: '我要认领应用',
        icon: myImages.own,
        navigatePath: '',
        onPressAction: () => {
          console.log('OpenActivity', OpenActivity);
          OpenActivity.open('com.lieying.content.social.login.ENTER');
        }
      },
      {
        title: '我的通知',
        icon: myImages.notice,
        navigatePath: ''
      },
      {
        title: '我的关注',
        icon: myImages.attention,
        navigatePath: ''
      },
      {
        title: '我的粉丝',
        icon: myImages.fans,
        navigatePath: ''
      },
      {
        title: '设置',
        icon: myImages.setting,
        navigatePath: 'Setting'
      },
      {
        title: '反馈意见',
        icon: myImages.suggest,
        navigatePath: ''
      }
    ];
    const { global } = this.props;
    return (
      <View style={styles.container}>
        <ScrollView>
          <ImageBackground resizeMode="cover" source={{ uri: myImages.bg }} style={styles.imgbg}>
            <LargerText style={[styles.titleText]}>个人中心-hot-load-9</LargerText>
            <Avatar style={[styles.cardCon, styles.cardUser]} data={userData} />
            <MenuCard style={[styles.cardCon, styles.cardMenu]} menu={cardMenu} />
            <MenuList style={[styles.cardCon, styles.listMenu]} menu={listMenu} />
            <Text>{global.count}</Text>
            <Button title="加" onPress={this.handlePlus} />
          </ImageBackground>
        </ScrollView>
      </View>
    );
  }
}

const mapStateToProps = ({ global }) => ({ global });

export default connect(mapStateToProps)(Mine);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: themeColor.bgColor
  },
  imgbg: {
    alignItems: 'center'
  },
  titleText: {
    color: '#fff',
    ...themeLayout.margin(scale(37), 0, scale(13), 0),
    ...themeLayout.flex('row')
  },
  cardCon: {
    backgroundColor: themeColor.white,
    borderRadius: scale(13),
    ...themeLayout.padding(0, scale(14)),
    width: scale(328)
  },
  cardUser: {
    marginBottom: scale(9),
    height: scale(93)
  },
  listMenu: {
    marginBottom: scale(50)
  },
  cardMenu: {
    ...themeLayout.flex('column', 'space-between', 'flex-start'),
    ...themeLayout.padding(scale(12), scale(28), scale(12), scale(14)),
    height: scale(111),
    marginBottom: scale(9)
  }
});
