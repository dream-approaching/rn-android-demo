import React from 'react';
import { StyleSheet, Image } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { scale, themeLayout } from '@/config';
import SmallText from '@/components/AppText/SmallText';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';

class LikeBtn extends React.Component {
  state = {
    likeNum: 0,
    isLike: false,
  };

  componentDidMount() {
    const { itemData } = this.props;
    this.setState({
      likeNum: itemData.fabulous,
      isLike: !itemData.is_fabulous,
    });
  }

  handleToggleLike = () => {
    const { dispatch, itemData, type } = this.props;
    const { likeNum, isLike } = this.state;
    const data = {
      mobilephone: itemData.mobilephone,
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
        this.setState({
          likeNum: isLike ? likeNum - 1 : +likeNum + 1,
          isLike: !isLike,
        });
      },
    });
  };

  render() {
    const { size = 14, textStyle } = this.props;
    const { likeNum, isLike } = this.state;
    return (
      <TouchableOpacity onPress={this.handleToggleLike} style={styles.likeCon}>
        <Image
          style={styles.likeIcon(size)}
          source={{ uri: isLike ? myImages.thumbO : myImages.thumb }}
        />
        <SmallText style={textStyle}>{likeNum}</SmallText>
      </TouchableOpacity>
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
