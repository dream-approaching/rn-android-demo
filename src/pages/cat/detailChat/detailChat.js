// 互动话题内页
import React from 'react';
import {
  View,
  StyleSheet,
  StatusBar,
  BackHandler,
  Image,
  TouchableWithoutFeedback,
  Text,
} from 'react-native';
import CommentItem from '@/components/Comment/CommentItem';
import CommentInput from '@/components/Comment/Cat/CommentInput';
import { scale, themeLayout, themeCatColor, themeCatSize } from '@/config';
import { connect } from '@/utils/dva';
import { COMMENT_TYPE } from '@/config/constants';
import commentHoc from '@/components/pageComponent/commentHoc';
import FirstLoading from '@/components/Loading/FirstLoading';
import LeftHeader from '@/components/Header/LeftHeader';
import SimpleFooter from '@/components/ScrollFooter/SimpleFooter';
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

  componentDidMount() {
    const { screenProps } = this.props;
    StatusBar.setBarStyle('dark-content', true);
    this.contendId = 42 || JSON.parse(screenProps.nativeProps.params).contentId;
    console.log('%cthis.contendId :', 'color: #0e93e0;background: #aaefe5;', this.contendId);
    this.nativePosition = 1 || JSON.parse(screenProps.nativeProps.params).position;
    this.queryArticleDispatch();
  }

  componentWillUnmount() {
    const { dispatch } = this.props;
    dispatch({ type: 'comment/saveCommentList', payload: [], isFirstPage: true });
    dispatch({ type: 'catHome/saveArticleDetail', payload: {} });
  }

  queryArticleDispatch = () => {
    const data = { id: this.contendId };
    this.props.dispatch({
      type: 'catHome/queryArticleDetailEffect',
      payload: data,
    });
  };

  handleSubmitComment = () => {
    const { comment, catHome } = this.props;
    const data = {
      type: COMMENT_TYPE.chat,
      content_id: this.contendId,
      position: this.nativePosition,
      commentNum: comment.commentListTotal,
      collection: catHome.articleDetail.is_favorites,
    };
    this.props.handleSubmitComment(data);
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
    return (
      <TouchableNativeFeedback notOut>
        <View style={styles.headerRightBtn}>
          <View style={styles.headerRightCon}>
            <SmallText style={styles.headerRightText}>关注</SmallText>
          </View>
        </View>
      </TouchableNativeFeedback>
    );
  };

  render() {
    const {
      textValue,
      placeholder,
      allLoaded,
      comment,
      handleQueryNextPage,
      handleChangeText,
      onblur,
      loading,
      catHome,
    } = this.props;
    const { articleDetail } = catHome;
    console.log('%carticleDetail:', 'color: #0e93e0;background: #aaefe5;', articleDetail);
    return (
      <View style={styles.container}>
        <SmallText>123</SmallText>
        <FirstLoading loading={loading || !articleDetail.id}>
          <LeftHeader
            leftComponent={this.renderHeaderLeft()}
            rightComponent={this.renderHeaderRight()}
          />
          <MyScrollView
            ref={ref => (this.refScrollView = ref)}
            loadingFooter={SimpleFooter}
            onLoading={() => handleQueryNextPage(comment.commentList)}
            allLoaded={allLoaded}
            bounces
          >
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
              <SecondaryText style={styles.desc}>{articleDetail.content}</SecondaryText>
            </View>
            <View style={styles.timeBar}>
              <SmallText style={styles.barText}>{articleDetail.timestr}</SmallText>
              <View style={styles.addressCon}>
                <Image style={styles.addressIcon} source={{ uri: myImages.approve }} />
                <SmallText style={styles.barText}>{articleDetail.location}</SmallText>
              </View>
            </View>
            <View style={styles.actionBar}>
              <View style={styles.actionBarItem}>
                <Image style={styles.actionIcon} source={{ uri: myImages.approve }} />
                <SmallText style={styles.barText}>打赏</SmallText>
              </View>
              <View style={styles.actionBarItem}>
                <Image style={styles.actionIcon} source={{ uri: myImages.approve }} />
                <SmallText style={styles.barText}>{+articleDetail.fabulous || ''}</SmallText>
              </View>
              <View style={styles.actionBarItem}>
                <Image style={styles.actionIcon} source={{ uri: myImages.approve }} />
                <SmallText style={styles.barText}>{+articleDetail.comment_num || ''}</SmallText>
              </View>
              <View style={styles.actionBarItem}>
                <Image style={styles.actionIcon} source={{ uri: myImages.approve }} />
                <SmallText style={styles.barText}>{+articleDetail.forward_num || ''}</SmallText>
              </View>
            </View>
            {!!articleDetail.detailtwo.length && (
              <View style={styles.replyCon}>
                {articleDetail.detailtwo.map(item => {
                  console.log('%citem:', 'color: #0e93e0;background: #aaefe5;', item);
                  if (!item.path) return;
                  const pathArr = item.path.split('-');
                  const isThirdLevel = pathArr.length >= 3;
                  return (
                    <TouchableWithoutFeedback key={item.id}>
                      <View style={{ marginTop: 3 }}>
                        <Text style={[styles.textLineHeight(18), { flexWrap: 'wrap' }]}>
                          <TouchableWithoutFeedback
                            onPress={() => this.gotoPersonPage(item.mobilephone)}
                          >
                            <CommonText style={[styles.replyTitle]}>{item.commit_user}</CommonText>
                          </TouchableWithoutFeedback>
                          {isThirdLevel && (
                            <CommonText style={styles.replayText}>
                              回复
                              <TouchableWithoutFeedback
                                onPress={() => this.gotoPersonPage(item.pidinfo.mobilephone)}
                              >
                                <CommonText style={[styles.replyTitle]}>
                                  {item.pidinfo.nick_name}
                                </CommonText>
                              </TouchableWithoutFeedback>
                            </CommonText>
                          )}
                          <TouchableWithoutFeedback>
                            <CommonText>：{item.content}</CommonText>
                          </TouchableWithoutFeedback>
                        </Text>
                      </View>
                    </TouchableWithoutFeedback>
                  );
                })}
                {+articleDetail.count > 2 && (
                  <TouchableNativeFeedback tapArea={1}>
                    <View style={{ marginTop: 3 }}>
                      <SecondaryText style={[styles.replyTitle, styles.textLineHeight(18)]}>
                        {`共${articleDetail.count}条回复＞`}
                      </SecondaryText>
                    </View>
                  </TouchableNativeFeedback>
                )}
              </View>
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
    marginLeft: scale(16),
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
  headerRightCon: {
    width: 47,
    height: 20,
    borderRadius: 10,
    ...themeLayout.border(1, themeCatColor.primaryColor),
    ...themeLayout.flex('row'),
  },
  headerRightText: {
    color: themeCatColor.primaryColor,
    fontSize: 11,
  },
  coverImg: {
    width: themeCatSize.screenWidth,
    height: scale(211),
  },
  titleCon: {
    ...themeLayout.padding(scale(12), scale(14), scale(12), scale(16)),
  },
  desc: {
    fontSize: scale(15),
    marginTop: scale(6),
    lineHeight: scale(22),
    marginBottom: scale(20),
    textAlign: 'justify',
  },
  timeBar: {
    ...themeLayout.flex('row', 'flex-start'),
    ...themeLayout.padding(0, 16),
  },
  actionBar: {
    ...themeLayout.flex('row', 'space-between'),
    ...themeLayout.padding(0, 26),
    marginTop: 18,
  },
  actionBarItem: {
    ...themeLayout.flex('row'),
    minWidth: 30,
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
  swiperWrapper: {
    width: themeCatSize.screenWidth,
    height: scale(211),
  },
  replyCon: {
    ...themeLayout.padding(5, 8),
    ...themeLayout.margin(8, 15),
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
