import React from 'react';
import { StyleSheet, Image, View } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { scale, themeLayout } from '@/config';
import SmallText from '@/components/AppText/SmallText';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';
import { actionBeforeCheckLogin } from '@/utils/utils';

class LikeBtn extends React.Component {
  handleToggleLike = () => {
    const { dispatch, itemData, type, toggleCallback = () => {} } = this.props;
    const data = {
      type,
      opt: !itemData.is_fabulous ? 'del' : 'add',
      id: itemData.id,
      real_name: itemData.commit_user,
      head_image: itemData.head_image,
    };
    dispatch({
      type: 'comment/toggleLikeEffect',
      payload: data,
      successFn: () => {
        toggleCallback();
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
