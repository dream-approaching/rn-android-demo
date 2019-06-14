// 互动话题内页
import React from 'react';
import { View, StyleSheet, StatusBar, BackHandler, Image, Text } from 'react-native';
import CommentItem from '@/components/Comment/CommentItem';
import CommentInput from '@/components/Comment/Cat/CommentInput';
import { scale, themeLayout, themeCatColor, themeCatSize } from '@/config';
import { connect } from '@/utils/dva';
import { COMMENT_TYPE, immediateTimer } from '@/config/constants';
import commentHoc from '@/components/pageComponent/commentHoc';
import FirstLoading from '@/components/Loading/FirstLoading';
import LeftHeader from '@/components/Header/LeftHeader';
import SmallText from '@/components/AppText/Cat/SmallText';
import MyScrollView from '@/components/ScrollView';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import SecondaryText from '@/components/AppText/SecondaryText';
import ImageWithDefault from '@/components/ImageWithDefault';
import Swiper from 'react-native-swiper';
import CommonText from '@/components/AppText/Cat/CommonText';
import myImages from '@/utils/myImages';

class DetailChat extends React.Component {
  static navigationOptions = {
    header: null,
  };

  state = {
    isFirstTime: true,
  };

  componentDidMount() {
    const { screenProps } = this.props;
    const { params } = screenProps.nativeProps;
    console.log('%cscreenProps:', 'color: #0e93e0;background: #aaefe5;', screenProps);
    StatusBar.setBarStyle('dark-content', true);
    this.contendId = params ? JSON.parse(params).contentId : 42;
    this.nativePosition = params ? JSON.parse(params).position : 1;
    this.queryArticleDispatch();
  }

  componentWillUnmount() {
    const { dispatch } = this.props;
    dispatch({ type: 'catHome/saveArticleDetail', payload: { img: '', detailtwo: [] } });
  }

  queryArticleDispatch = () => {
    const data = { id: this.contendId };
    this.props.dispatch({
      type: 'catHome/queryArticleDetailEffect',
      payload: data,
      successFn: () => {
        this.setState({ isFirstTime: false });
      },
    });
  };

  handleSubmitComment = () => {
    const data = {
      type: COMMENT_TYPE.share,
      content_id: this.contendId,
    };
    this.props.handleSubmitComment(data, () => {
      this.queryArticleDispatch();
    });
  };

  renderCommentItem = ({ item, index }) => (
    <CommentItem
      seeAllChildAction={this.handleSeeAllChild}
      replyAction={this.props.replyAction}
      itemData={item}
      index={index}
    />
  );

  handleSeeAllChild = (item, index) => {
    const { navigation } = this.props;
    navigation.navigate('ChildComment', {
      total: item.count,
      id: item.id,
      index,
      contendId: this.contendId,
      type: COMMENT_TYPE.chat,
    });
  };

  handleQuitActivity = () => {
    BackHandler.exitApp();
  };

  handleGotoComment = () => {
    const { navigation, catHome } = this.props;
    const { articleDetail } = catHome;
    navigation.navigate('CatComment', { contendId: articleDetail.id, type: articleDetail.type });
  };

  handleToggleLike = () => {
    const { dispatch, catHome } = this.props;
    const { articleDetail } = catHome;
    const data = {
      type: articleDetail.type,
      opt: !articleDetail.is_fabulous ? 'del' : 'add',
      id: articleDetail.id,
    };
    dispatch({
      type: 'catHome/saveArticleDetail',
      payload: {
        ...articleDetail,
        is_fabulous: !articleDetail.is_fabulous,
        fabulous: !articleDetail.is_fabulous
          ? +articleDetail.fabulous - 1
          : +articleDetail.fabulous + 1,
      },
    });
    if (this.timer) clearTimeout(this.timer);
    this.timer = setTimeout(() => {
      dispatch({
        type: 'catNormal/toggleLikeEffect',
        payload: data,
      });
    }, immediateTimer);
  };

  handleSetReward = () => {
    const { dispatch, catHome } = this.props;
    const { articleDetail } = catHome;
    if (!articleDetail.is_reward) return null;
    const data = {
      type: 1, // 1-图文 2-视频
      content_id: articleDetail.id,
    };
    dispatch({
      type: 'catHome/saveArticleDetail',
      payload: { ...articleDetail, is_reward: false },
    });
    if (this.timer) clearTimeout(this.timer);
    this.timer = setTimeout(() => {
      dispatch({
        type: 'catNormal/setRewardEffect',
        payload: data,
      });
    }, immediateTimer);
  };

  toggleAttention = () => {
    const { dispatch, catHome } = this.props;
    const { articleDetail } = catHome;
    const data = {
      follow_mobilephone: articleDetail.mobilephone,
      opt: !articleDetail.is_add_friends ? 'del' : 'add',
    };
    dispatch({
      type: 'catHome/saveArticleDetail',
      payload: { ...articleDetail, is_add_friends: !articleDetail.is_add_friends },
    });
    if (this.timer) clearTimeout(this.timer);
    this.timer = setTimeout(() => {
      dispatch({
        type: 'catNormal/toggleAttentionEffect',
        payload: data,
      });
    }, immediateTimer);
  };

  renderHeaderLeft = () => {
    const { catHome } = this.props;
    const { articleDetail } = catHome;
    return (
      <TouchableNativeFeedback tapArea={1} notOut>
        <View style={styles.headerLeftBtn}>
          <ImageWithDefault style={styles.avatar} source={{ uri: articleDetail.head_image }} />
          <CommonText style={styles.headerLeftText}>{articleDetail.commit_user}</CommonText>
        </View>
      </TouchableNativeFeedback>
    );
  };

  renderHeaderRight = () => {
    const { catHome } = this.props;
    const { articleDetail } = catHome;
    return (
      <TouchableNativeFeedback onPress={this.toggleAttention} notOut>
        <View style={styles.headerRightBtn}>
          {(articleDetail.is_add_friends && (
            <View style={styles.headerRightCon1}>
              <SmallText style={styles.headerRightText1}>关注</SmallText>
            </View>
          )) || (
            <View style={styles.headerRightCon2}>
              <SmallText style={styles.headerRightText2}>已关注</SmallText>
            </View>
          )}
        </View>
      </TouchableNativeFeedback>
    );
  };

  render() {
    const { textValue, placeholder, handleChangeText, onblur, loading, catHome } = this.props;
    const { isFirstTime } = this.state;
    const { articleDetail } = catHome;
    return (
      <View style={styles.container}>
        <FirstLoading loading={(loading || !articleDetail.id) && isFirstTime}>
          <LeftHeader
            leftComponent={this.renderHeaderLeft()}
            rightComponent={this.renderHeaderRight()}
          />
          <MyScrollView>
            <View>
              <Swiper style={styles.swiperWrapper}>
                {articleDetail.img.split(',').map(item => {
                  return (
                    <ImageWithDefault key={item} style={styles.coverImg} source={{ uri: item }} />
                  );
                })}
              </Swiper>
            </View>
            <View style={styles.titleCon}>
              <CommonText style={styles.contentText}>{articleDetail.content}</CommonText>
            </View>
            <View style={styles.timeBar}>
              <SmallText style={styles.barText}>{articleDetail.timestr}</SmallText>
              <View style={styles.addressCon}>
                <Image style={styles.addressIcon} source={{ uri: myImages.catLocation }} />
                <SmallText style={styles.barText}>{articleDetail.location}</SmallText>
              </View>
            </View>
            <View style={styles.actionBar}>
              <TouchableNativeFeedback onPress={this.handleSetReward} notOut tapArea={2}>
                <View style={styles.actionBarItem}>
                  <Image
                    style={styles.actionIcon}
                    source={{
                      uri: !articleDetail.is_reward ? myImages.catGoldActive : myImages.catGold,
                    }}
                  />
                  {(!articleDetail.is_reward && (
                    <SmallText style={styles.actionText2}>已打赏</SmallText>
                  )) || <SmallText style={styles.actionText}>打赏</SmallText>}
                </View>
              </TouchableNativeFeedback>
              <TouchableNativeFeedback onPress={this.handleToggleLike} notOut tapArea={2}>
                <View style={styles.actionBarItem}>
                  <Image
                    style={styles.actionIcon}
                    source={{
                      uri: !articleDetail.is_fabulous ? myImages.catLikeActive : myImages.catLike,
                    }}
                  />
                  <SmallText style={styles.actionText}>{+articleDetail.fabulous || ''}</SmallText>
                </View>
              </TouchableNativeFeedback>
              <TouchableNativeFeedback onPress={this.handleGotoComment} notOut tapArea={2}>
                <View style={styles.actionBarItem}>
                  <Image style={styles.actionIcon} source={{ uri: myImages.catComment }} />
                  <SmallText style={styles.actionText}>
                    {+articleDetail.comment_num || ''}
                  </SmallText>
                </View>
              </TouchableNativeFeedback>
              <TouchableNativeFeedback notOut tapArea={2}>
                <View style={styles.actionBarItem}>
                  <Image style={styles.actionIcon} source={{ uri: myImages.catShare }} />
                  <SmallText style={styles.actionText}>
                    {+articleDetail.forward_num || ''}
                  </SmallText>
                </View>
              </TouchableNativeFeedback>
            </View>
            {!!articleDetail.detailtwo.length && (
              <TouchableNativeFeedback onPress={this.handleGotoComment} notOut tapArea={2}>
                <View style={styles.replyCon}>
                  {articleDetail.detailtwo.map(item => {
                    if (!item.path) return;
                    const pathArr = item.path.split('-');
                    const isThirdLevel = pathArr.length >= 2;
                    return (
                      <View style={{ marginTop: 3 }} key={item.id}>
                        <Text style={[styles.textLineHeight(18), { flexWrap: 'wrap' }]}>
                          <CommonText style={[styles.replyTitle]}>{item.commit_user}</CommonText>
                          {isThirdLevel && (
                            <CommonText style={styles.replayText}>
                              回复
                              <CommonText style={[styles.replyTitle]}>
                                {item.pidinfo.nick_name}
                              </CommonText>
                            </CommonText>
                          )}
                          <CommonText>：{item.content}</CommonText>
                        </Text>
                      </View>
                    );
                  })}
                  {+articleDetail.count > 2 && (
                    <TouchableNativeFeedback tapArea={1}>
                      <View style={{ marginTop: 3 }}>
                        <SecondaryText style={[styles.replyTitle, styles.textLineHeight(18)]}>
                          {`查看${articleDetail.count}条评论`}
                          <Text style={{ letterSpacing: -5 }}>＞＞</Text>
                        </SecondaryText>
                      </View>
                    </TouchableNativeFeedback>
                  )}
                </View>
              </TouchableNativeFeedback>
            )}
          </MyScrollView>
          <CommentInput
            {...this.props}
            ref={ref => (this.refInputCon = ref)}
            handleChangeText={handleChangeText}
            handleSubmitComment={this.handleSubmitComment}
            textValue={textValue}
            placeholder={placeholder}
            onblur={onblur}
            nativePosition={this.nativePosition}
          />
        </FirstLoading>
      </View>
    );
  }
}

const mapStateToProps = ({ catHome, loading }) => ({
  catHome,
  loading: loading.effects['catHome/queryArticleDetailEffect'],
});

export default connect(mapStateToProps)(commentHoc(DetailChat));

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  commentTitle: {
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.padding(0, scale(16)),
    ...themeLayout.borderSide(),
    height: scale(46),
  },
  commentTotal: {
    fontSize: scale(16),
  },
  headerLeftBtn: {
    ...themeLayout.flex('row', 'flex-start'),
    marginLeft: scale(8),
  },
  avatar: {
    width: 30,
    height: 30,
    borderRadius: 15,
    marginRight: 10,
  },
  headerLeftText: {
    fontSize: 13,
  },
  headerRightBtn: {
    ...themeLayout.padding(scale(12)),
  },
  headerRightCon1: {
    width: 47,
    height: 20,
    borderRadius: 10,
    ...themeLayout.border(1, themeCatColor.primaryColor),
    ...themeLayout.flex('row'),
  },
  headerRightText1: {
    color: themeCatColor.primaryColor,
    fontSize: 11,
  },
  headerRightCon2: {
    width: 47,
    height: 20,
    borderRadius: 10,
    ...themeLayout.border(1, '#dadada'),
    ...themeLayout.flex('row'),
  },
  headerRightText2: {
    color: '#bbb',
    fontSize: 11,
  },
  swiperWrapper: {
    width: themeCatSize.screenWidth,
    height: scale(250),
  },
  coverImg: {
    width: themeCatSize.screenWidth,
    height: scale(250),
  },
  titleCon: {
    ...themeLayout.padding(0, scale(14), scale(12), scale(16)),
  },
  contentText: {
    fontSize: 14,
    marginTop: scale(6),
    lineHeight: scale(22),
  },
  timeBar: {
    ...themeLayout.flex('row', 'flex-start'),
    ...themeLayout.padding(0, 16),
  },
  actionBar: {
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.padding(0, 16),
    marginTop: 12,
  },
  actionBarItem: {
    ...themeLayout.flex('row'),
    minWidth: 30,
    ...themeLayout.padding(5, 10),
  },
  actionIcon: {
    width: scale(16),
    height: scale(16),
    marginRight: scale(4),
  },
  addressCon: {
    ...themeLayout.flex('row'),
    marginLeft: scale(26),
  },
  addressIcon: {
    width: scale(11),
    height: scale(11),
    marginRight: scale(2),
  },
  barText: {
    fontSize: 11,
    color: '#999',
  },
  actionText: {
    fontSize: 12,
    color: '#999',
  },
  actionText2: {
    fontSize: 12,
    color: themeCatColor.primaryColor,
  },
  replyCon: {
    ...themeLayout.padding(5, 8),
    ...themeLayout.margin(2, 15, 20),
    backgroundColor: themeCatColor.bgF4,
    borderRadius: 8,
  },
  replyText: {
    marginTop: 3,
  },
  replyTitle: {
    color: '#39536C',
  },
  textLineHeight: num => ({
    lineHeight: num,
  }),
});
