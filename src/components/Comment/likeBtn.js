import React from 'react';
import { StyleSheet, Image, View } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { scale, themeLayout } from '@/config';
import SmallText from '@/components/AppText/SmallText';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';
import { actionBeforeCheckLogin } from '@/utils/utils';
import { LIKE_TYPE } from '@/config/constants';

class LikeBtn extends React.Component {
  handleToggleLike = () => {
    const { dispatch, itemData, type } = this.props;
    const isLike = !itemData.is_fabulous;
    const likeNum = +itemData.fabulous;
    const data = {
      type,
      opt: isLike ? 'del' : 'add',
      id: itemData.id,
      real_name: itemData.commit_user,
      head_image: itemData.head_image,
    };
    dispatch({
      type: 'comment/toggleLikeEffect',
      payload: data,
      successFn: () => {
        if (type === LIKE_TYPE.share) {
          const setXshareData = {
            ...itemData,
            fabulous: isLike ? likeNum - 1 : likeNum + 1,
            is_fabulous: isLike,
          };
          dispatch({
            type: 'global/saveXshareData',
            payload: setXshareData,
          });
        }
      },
    });
  };

  render() {
    const { size = 14, textStyle, itemData } = this.props;
    return (
      <TouchableNativeFeedback onPress={() => actionBeforeCheckLogin(this.handleToggleLike)}>
        <View style={styles.likeCon}>
          <Image
            style={styles.likeIcon(size)}
            source={{ uri: !itemData.is_fabulous ? myImages.thumbO : myImages.thumb }}
          />
          <SmallText style={textStyle}>{+itemData.fabulous || ''}</SmallText>
        </View>
      </TouchableNativeFeedback>
    );
  }
}

const mapStateToProps = ({ comment }) => ({ comment });

export default connect(mapStateToProps)(LikeBtn);

const styles = StyleSheet.create({
  likeCon: {
    ...themeLayout.flex('row', 'space-between'),
  },
  likeIcon: size => {
    return {
      width: scale(size),
      height: scale(size),
      marginRight: scale(3),
    };
  },
});
