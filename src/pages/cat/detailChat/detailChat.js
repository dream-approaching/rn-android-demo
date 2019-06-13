// 互动话题内页
import React from 'react';
import { View, StyleSheet, StatusBar, BackHandler } from 'react-native';
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
  title: {
    marginTop: scale(6),
    marginBottom: scale(6),
    fontSize: scale(21),
    lineHeight: scale(30),
  },
  desc: {
    fontSize: scale(15),
    marginTop: scale(6),
    lineHeight: scale(22),
    marginBottom: scale(20),
    textAlign: 'justify',
  },
  swiperWrapper: {
    width: themeCatSize.screenWidth,
    height: scale(211),
  },
});
