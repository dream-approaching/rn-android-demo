import React from 'react';
import { View, Image, StyleSheet, Text, TouchableOpacity } from 'react-native';
// import { TouchableOpacity } from 'react-native-gesture-handler';
import { themeLayout, scale, themeColor, themeSize } from '@/config';
import SecondaryText from '@/components/AppText/SecondaryText';
import SmallText from '@/components/AppText/SmallText';
import CommonText from '@/components/AppText/CommonText';
import LikeBtn from '@/components/Comment/likeBtn';
import myImages from '@/utils/myImages';
import { OpenRnActivity, OpenActivity } from '@/components/NativeModules';
import { LIKE_TYPE } from '@/config/constants';
import { connect } from '@/utils/dva';
import ImageWithDefault from '@/components/ImageWithDefault';
import MyModal from '@/components/Modal';

class XshareItem extends React.Component {
  state = {
    mainBodyHeight: 0,
    isAttention: false,
    modalVisible: false,
  };

  componentDidMount() {
    const { itemData } = this.props;
    const isAttention = !itemData.is_add_friends;
    this.setState({ isAttention });
  }

  mainBodyLayout = ({ nativeEvent }) => {
    const { height } = nativeEvent.layout;
    this.setState({
      mainBodyHeight: height,
    });
  };

  toggleAttention = () => {
    const { itemData, dispatch } = this.props;
    const { isAttention } = this.state;
    // itemData.is_add_friends ? '+关注' : '已关注'
    console.log('%citemData:', 'color: #0e93e0;background: #aaefe5;', itemData.attention);
    const data = {
      follow_mobilephone: itemData.mobilephone,
      opt: isAttention ? 'del' : 'add',
    };
    dispatch({
      type: 'xshare/toggleAttentionEffect',
      payload: data,
      successFn: () => {
        this.setState({
          isAttention: !isAttention,
        });
      },
    });
  };

  handleShowModal = () => {
    this.setState({
      modalVisible: true,
    });
  };

  handleHideModal = () => {
    this.setState({
      modalVisible: false,
    });
  };

  handleRightBottomAction = () => {
    const { origin } = this.props;
    if (origin === 'myPage') {
      this.handleShowModal();
    }
    // 举报
    OpenActivity.openReportDialog();
  };

  handleConfirmDelete = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'xshare/deleteXshareEffect',
      payload: 'data',
      successFn: () => {},
    });
  };

  gotoXfriendDetail = () => {
    const { noPress, itemData } = this.props;
    return noPress
      ? () => {}
      : OpenRnActivity('xFriendDetail', JSON.stringify({ id: itemData.id }));
  };

  gotoAppDetail = () => {
    const { itemData } = this.props;
    OpenActivity.openAppDetails(
      itemData.mydata
        ? itemData.mydata.id
        : itemData.appdata ? itemData.appdata.id : itemData.app_info
    );
  };

  gotoPersonPage = () => {
    const { origin, itemData } = this.props;
    if (origin === 'myPage') return null;
    OpenRnActivity('myShare', JSON.stringify({ phone: itemData.mobilephone }));
  };

  render() {
    const { itemData, noPress, origin, isDetail } = this.props;
    const { mainBodyHeight, isAttention, modalVisible } = this.state;
    const isOwnPersonPage = origin === 'myPage';
    const lineNumber = !isDetail ? { numberOfLines: 6 } : {};
    return (
      <View style={styles.container}>
        <TouchableOpacity activeOpacity={isOwnPersonPage ? 1 : 0.2} onPress={this.gotoPersonPage}>
          <View style={styles.avatarCon}>
            <ImageWithDefault style={styles.avatar} source={{ uri: itemData.head_image }} />
            {+itemData.is_big_v === 2 && (
              <Image style={styles.bigV} source={{ uri: myImages.approve }} />
            )}
          </View>
        </TouchableOpacity>
        <View style={styles.itemRight}>
          <View style={styles.flexRowBetween}>
            <SecondaryText>{itemData.commit_user}</SecondaryText>
            {!isOwnPersonPage && (
              <TouchableOpacity onPress={this.toggleAttention}>
                <SmallText style={styles.attenText(!isAttention)}>
                  {!isAttention ? '+关注' : '已关注'}
                </SmallText>
              </TouchableOpacity>
            )}
          </View>
          <SmallText>{itemData.timestr || itemData.created_time}</SmallText>
          <TouchableOpacity
            activeOpacity={noPress ? 1 : 0.2}
            onPress={this.gotoXfriendDetail}
            style={styles.mainBody}
          >
            <Text
              ref={ref => (this.refText = ref)}
              onLayout={this.mainBodyLayout}
              style={styles.mainContext}
              {...lineNumber}
            >
              {itemData.label &&
                itemData.label.split(',').map(item => {
                  return (
                    <CommonText style={styles.labelText} key={item}>
                      #{item}{' '}
                    </CommonText>
                  );
                })}
              <CommonText>&nbsp;&nbsp;{itemData.content}</CommonText>
            </Text>
          </TouchableOpacity>
          {!isDetail &&
            mainBodyHeight >= 110 && (
              <TouchableOpacity onPress={this.gotoXfriendDetail}>
                <Text style={styles.seeAllText}>查看详情</Text>
              </TouchableOpacity>
            )}
          <View style={styles.flexRowBetween}>
            <TouchableOpacity onPress={this.gotoAppDetail} style={styles.appCon}>
              <ImageWithDefault
                style={styles.appIcon}
                source={{
                  uri: itemData.mydata
                    ? itemData.mydata.img
                    : itemData.appdata ? itemData.appdata.app_logo : itemData.app_logo,
                }}
              />
              <SmallText style={styles.appName}>
                {itemData.mydata
                  ? itemData.mydata.title
                  : itemData.appdata ? itemData.appdata.app_short_desc : itemData.app_name_cn}
              </SmallText>
            </TouchableOpacity>
            <View />
          </View>
          <View style={styles.bottomBar}>
            <LikeBtn
              type={LIKE_TYPE.share}
              itemData={itemData}
              size={16}
              textStyle={styles.bottomBarText}
            />
            <TouchableOpacity
              activeOpacity={noPress ? 1 : 0.2}
              style={styles.flexRowBetween}
              onPress={this.gotoXfriendDetail}
            >
              <Image style={styles.bottomBarIcon} source={{ uri: myImages.comment }} />
              <SmallText style={styles.bottomBarText}>{+itemData.comment_num || ''}</SmallText>
            </TouchableOpacity>
            <TouchableOpacity style={styles.flexRowBetween}>
              <Image style={styles.bottomBarIcon} source={{ uri: myImages.share }} />
              <SmallText style={styles.bottomBarText}>{+itemData.forward_num || ''}</SmallText>
            </TouchableOpacity>
            <TouchableOpacity onPress={this.handleRightBottomAction} style={styles.btnEtc}>
              {(origin === 'myPage' && <SmallText style={styles.delText}>删除</SmallText>) || (
                <Image style={styles.bottomBarIcon} source={{ uri: myImages.btnEtc }} />
              )}
            </TouchableOpacity>
          </View>
        </View>
        <MyModal
          backdropOpacity={0.8}
          animationIn="zoomInDown"
          animationOut="zoomOutUp"
          animationInTiming={600}
          animationOutTiming={600}
          backdropTransitionInTiming={600}
          backdropTransitionOutTiming={600}
          hideModalAction={this.handleHideModal}
          isVisible={modalVisible}
        >
          <View style={styles.modalCon}>
            <View style={styles.modelTip}>
              <CommonText style={styles.modalBtnText('#303030')}>确定删除吗</CommonText>
            </View>
            <View style={styles.modalBtnCon}>
              <TouchableOpacity style={styles.modalBtn(false)} onPress={this.handleHideModal}>
                <CommonText style={styles.modalBtnText('#2f94ea')}>取消</CommonText>
              </TouchableOpacity>
              <TouchableOpacity style={styles.modalBtn(true)} onPress={this.handleConfirmDelete}>
                <CommonText style={styles.modalBtnText('#fb716b')}>确定</CommonText>
              </TouchableOpacity>
            </View>
          </View>
        </MyModal>
      </View>
    );
  }
}

const mapStateToProps = ({ xshare }) => ({ xshare });

export default connect(mapStateToProps)(XshareItem);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    ...themeLayout.flex('row', 'space-between', 'flex-start'),
    ...themeLayout.padding(0, scale(16)),
    marginTop: scale(17),
  },
  avatarCon: {
    width: scale(49),
    height: scale(49),
  },
  avatar: {
    width: scale(49),
    height: scale(49),
    borderRadius: scale(25),
    backgroundColor: themeColor.bgF4,
  },
  bigV: {
    position: 'absolute',
    bottom: 0,
    right: 0,
    zIndex: 1000,
    width: scale(14),
    height: scale(14),
  },
  itemRight: {
    flex: 1,
    marginLeft: scale(12),
    paddingBottom: scale(14),
    ...themeLayout.borderSide(),
  },
  flexRowBetween: {
    ...themeLayout.flex('row', 'space-between'),
  },
  attenText: attention => {
    return {
      fontSize: scale(11),
      color: !attention ? themeColor.font.secondary : themeColor.primaryColor,
    };
  },
  mainContext: {
    lineHeight: scale(20),
  },
  labelText: {
    color: themeColor.font.secondary,
  },
  appCon: {
    height: scale(22),
    ...themeLayout.flex('row'),
    ...themeLayout.padding(0, scale(10)),
    backgroundColor: themeColor.bgF4,
    borderRadius: scale(10),
    marginTop: scale(8),
  },
  appIcon: {
    width: scale(17),
    height: scale(17),
    borderRadius: scale(9),
  },
  appName: {
    color: themeColor.primaryColor,
    marginLeft: scale(6),
    // fontWeight: '500',
  },
  bottomBar: {
    ...themeLayout.flex('row', 'space-between'),
    marginTop: scale(12),
  },
  bottomBarText: {
    fontSize: scale(11),
    color: themeColor.font.small,
    marginLeft: scale(5),
  },
  bottomBarIcon: {
    width: scale(16),
    height: scale(16),
  },
  btnEtc: {
    marginLeft: scale(30),
  },
  etcIcon: {
    width: scale(18),
    height: scale(18),
  },
  seeAllText: {
    color: '#2c5b93',
    fontSize: themeSize.font.secondary,
    marginTop: scale(5),
  },
  delText: {
    color: themeColor.primaryColor,
    fontSize: scale(11),
    ...themeLayout.borderSide('Bottom', themeColor.primaryColor),
  },
  modalCon: {
    width: scale(242),
    height: scale(107),
    ...themeLayout.flex('column'),
    alignSelf: 'center',
    backgroundColor: '#fff',
    borderRadius: scale(10),
  },
  modelTip: {
    height: scale(54),
    ...themeLayout.flex(),
  },
  modalBtnCon: {
    width: '100%',
    ...themeLayout.flex(),
    ...themeLayout.borderSide('Top'),
  },
  modalBtn: showBorder => {
    const style = {
      flex: 1,
      ...themeLayout.flex(),
      height: scale(54),
    };
    return showBorder ? { ...style, ...themeLayout.borderSide('Left') } : style;
  },
  modalBtnText: color => {
    return {
      fontSize: scale(19),
      color,
    };
  },
});
