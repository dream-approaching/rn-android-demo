import React from 'react';
import { StyleSheet, Image } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { scale, themeLayout } from '@/config';
import SmallText from '@/components/AppText/SmallText';
import myImages from '@/utils/myImages';
import { connect } from '@/utils/dva';

class LikeBtn extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      likeNum: props.likeNum,
      status: props.status,
    };
  }

  handleGiveLike = params => {
    const { dispatch } = this.props;
    const { likeNum, status } = this.state;
    const data = {
      ...params,
    };
    dispatch({
      type: 'commnet/giveLikelikeEffect',
      payload: data,
      successFn: () => {
        this.setState({
          likeNum: likeNum + 1,
          status: !status,
        });
      },
    });
  };

  render() {
    const { size = 14, textStyle } = this.props;
    const { likeNum, status } = this.state;
    return (
      <TouchableOpacity onPress={this.handleGiveLike} style={styles.likeCon}>
        <Image
          style={styles.likeIcon(size)}
          source={{ uri: status ? myImages.thumbO : myImages.thumb }}
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
