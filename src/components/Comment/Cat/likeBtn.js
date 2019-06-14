import React from 'react';
import { StyleSheet, Image, View } from 'react-native';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';
import { themeLayout, themeCatColor } from '@/config';
import SmallText from '@/components/AppText/Cat/SmallText';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';
import { actionBeforeCheckLogin } from '@/utils/utils';
import { immediateTimer } from '@/config/constants';

const hadLikedArrCache = [];
class LikeBtn extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      // hadLikedArr: '',
      islike: !props.itemData.is_fabulous,
      likenum: +props.itemData.fabulous,
    };
  }

  handleToggleLike = () => {
    const { dispatch, itemData, type } = this.props;
    const { islike, likenum } = this.state;
    hadLikedArrCache.push(itemData.id);
    // this.setState({ hadLikedArr: hadLikedArrCache });

    this.setState({
      islike: !islike,
      likenum: islike ? likenum - 1 : likenum + 1,
    });
    if (this[`timer_${itemData.id}`]) clearTimeout(this[`timer_${itemData.id}`]);
    this[`timer_${itemData.id}`] = setTimeout(() => {
      const data = {
        type,
        opt: (islike && 'del') || 'add',
        id: itemData.id,
      };
      dispatch({
        type: 'catNormal/toggleLikeEffect',
        payload: data,
      });
    }, immediateTimer);
  };

  render() {
    const { size = 16, textStyle } = this.props;
    const { islike, likenum } = this.state;
    return (
      <TouchableNativeFeedback
        tapArea={30}
        onPress={() => actionBeforeCheckLogin(this.handleToggleLike)}
      >
        <View style={styles.likeCon}>
          <SmallText style={[styles.textStyle, textStyle]}>{likenum || ''}</SmallText>
          <Image
            style={styles.likeIcon(size)}
            source={{ uri: islike ? myImages.catLikeActive : myImages.catLike }}
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
