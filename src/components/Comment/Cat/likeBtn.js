import React from 'react';
import { StyleSheet, Image, View } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, themeCatColor } from '@/config';
import SmallText from '@/components/AppText/Cat/SmallText';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';
import { actionBeforeCheckLogin } from '@/utils/utils';
import { LIKE_TYPE, immediateTimer } from '@/config/constants';

const hadLikedArrCache = [];
class LikeBtn extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      // hadLikedArr: '',
      islike: !props.itemData.is_fabulous,
      likenum: +props.itemData.fabulous,
    };
    this.isShare = +props.type === LIKE_TYPE.share;
  }

  handleToggleLike = () => {
    const { dispatch, itemData, type } = this.props;
    const { islike, likenum } = this.state;
    const isLike = !itemData.is_fabulous;
    const likeNum = +itemData.fabulous;
    hadLikedArrCache.push(itemData.id);
    // this.setState({ hadLikedArr: hadLikedArrCache });

    if (this.isShare) {
      const setXshareData = {
        ...itemData,
        fabulous: isLike ? likeNum - 1 : likeNum + 1,
        is_fabulous: isLike,
      };
      dispatch({
        type: 'global/saveXshareData',
        payload: setXshareData,
      });
    } else {
      this.setState({
        islike: !islike,
        likenum: islike ? likenum - 1 : likenum + 1,
      });
    }
    if (this[`timer_${itemData.id}`]) clearTimeout(this[`timer_${itemData.id}`]);
    this[`timer_${itemData.id}`] = setTimeout(() => {
      const data = {
        type,
        opt: ((this.isShare ? isLike : islike) && 'del') || 'add',
        id: itemData.id,
      };
      dispatch({
        type: 'comment/toggleLikeEffect',
        payload: data,
      });
    }, immediateTimer);
  };

  render() {
    const { size = 16, textStyle, itemData } = this.props;
    const { islike, likenum } = this.state;
    const showNumber = this.isShare ? itemData.fabulous : likenum;
    const showIslike = this.isShare ? !itemData.is_fabulous : islike;
    return (
      <TouchableNativeFeedback
        tapArea={this.isShare ? 10 : 30}
        onPress={() => actionBeforeCheckLogin(this.handleToggleLike)}
      >
        <View style={styles.likeCon}>
          <SmallText style={[styles.textStyle, textStyle]}>{showNumber || '10'}</SmallText>
          <Image
            style={styles.likeIcon(size)}
            source={{ uri: showIslike ? myImages.thumbO : myImages.commentLike }}
          />
        </View>
      </TouchableNativeFeedback>
    );
  }
}

const mapStateToProps = ({ comment, global }) => ({ comment, global });

export default connect(mapStateToProps)(LikeBtn);

const styles = StyleSheet.create({
  likeCon: {
    ...themeLayout.flex('row', 'space-between'),
  },
  textStyle: {
    color: themeCatColor.font.black,
  },
  likeIcon: size => {
    return {
      width: size,
      height: size,
      marginLeft: 5,
    };
  },
});
