/* eslint-disable */

import React from 'react';
import {
  View,
  StyleSheet,
  Animated,
  Vibration,
  Platform,
  ViewPropTypes,
  PixelRatio,
  Image,
} from 'react-native';

import {
  GestureHandlerGestureEvent,
  LongPressGestureHandler,
  PanGestureHandler,
  PanGestureHandlerStateChangeEvent,
  State,
  BaseButton,
} from 'react-native-gesture-handler';
import { removeArrIndex } from '@/utils/utils';
import CommonText from '@/components/AppText/Cat/CommonText';
import { themeLayout, scale } from '@/config';
import myImages from '@/utils/myImages';
import TouchableNativeFeedback from '@/components/Touchable/TouchableNativeFeedback';

const Button = Animated.createAnimatedComponent(BaseButton);

export class DragToSortTags extends React.Component {
  _offsetX = new Animated.Value(0);
  _offsetY = new Animated.Value(0);
  _frame;
  _tagFrames = [];
  _tagTransform;
  _event;
  _panRef = React.createRef();
  _longPressRef;
  _tagRefs = [];
  _exchangeIndex = -1;
  _animatations;
  _lastTick;
  _tags;
  _selectedTags;

  getSortedTags() {
    return this._tags;
  }

  getSelectedTags() {
    return this._selectedTags;
  }

  constructor(props) {
    super(props);
    this._selectedTags = [...props.initSelectedTags];
    this.state = { draggedIndex: -1 };
    this.componentWillReceiveProps(props);
    this._event = Animated.event(
      [
        {
          nativeEvent: {
            translationX: this._offsetX,
            translationY: this._offsetY,
          },
        },
      ],
      {
        useNativeDriver: true,
        listener: this._panListener,
      }
    );
  }

  static defaultProps = {
    tags: [],
    marginHorizontal: 5,
    marginVertical: 5,
    renderTag: () => null,
    colorForSelected: 'transparent',
    opacityForDragged: 0.6,
    onTagsSorted: () => null,
    fixBorderRadiusOnAndroid: true,
    selectable: true,
    onSelectChange: () => null,
    maxCountSelectable: 0,
    vibration: 40,
    initSelectedTags: [],
    longPressResponseTime: 500,
  };

  componentWillReceiveProps(next) {
    if (next.tags !== this.props.tags || !this._longPressRef) {
      this._longPressRef = [];
      this._tagTransform = [];
      this._tags = [...next.tags];
      next.tags.forEach((tag, index) => {
        this._longPressRef[index] = React.createRef();
        this._tagTransform[index] = {
          x: new Animated.Value(0),
          y: new Animated.Value(0),
        };
      });
    }
  }

  handleDelImg = item => {
    const index = this._tags.findIndex(tagItem => tagItem.id === item.id);
    this._tags = removeArrIndex(this._tags, index);
    return this._clearAllTransformAndUpdate();
  };

  handleAddImg = () => {
    console.log('选择照片');
  };

  render() {
    const container = StyleSheet.flatten([this.props.style, styles.container]);
    const { type } = this.props;
    return (
      <PanGestureHandler
        ref={this._panRef}
        simultaneousHandlers={this._longPressRef}
        onGestureEvent={this._event}
        onHandlerStateChange={this._onPanStateChange}
      >
        <Animated.View style={container} onLayout={this._onContainerLayout}>
          {this._tags.map(this._renderTags)}
          {this._tags.length < 9 && type !== 'video' && (
            <TouchableNativeFeedback tapArea={1} notOut onPress={this.handleAddImg}>
              <View style={styles.imgCon}>
                <Image style={styles.addImg} source={{ uri: myImages.delBack }} />
              </View>
            </TouchableNativeFeedback>
          )}
        </Animated.View>
      </PanGestureHandler>
    );
  }

  _renderTags = (tag, index) => {
    const tagElement = this.props.renderTag(tag);
    if (!tagElement) return null;
    const {
      fixBorderRadiusOnAndroid,
      colorForSelected,
      opacityForDragged,
      marginHorizontal,
      marginVertical,
      longPressResponseTime,
    } = this.props;
    const oldStyle = StyleSheet.flatten(tagElement.props.style);
    const dragged = this.state.draggedIndex === index;
    let borderRadius = oldStyle ? oldStyle.borderRadius : null;
    if (Platform.OS === 'android' && fixBorderRadiusOnAndroid && borderRadius > 0) {
      borderRadius = borderRadius * PixelRatio.get();
    }
    let backgroundColor = oldStyle ? oldStyle.backgroundColor : null;
    if (colorForSelected && this._selectedTags.indexOf(tag) >= 0) {
      backgroundColor = colorForSelected;
    }
    let opacity = oldStyle ? oldStyle.opacity : null;
    if (opacityForDragged && dragged) {
      opacity = opacityForDragged;
    }
    const style = {
      ...oldStyle,
      marginHorizontal: marginHorizontal,
      marginVertical: marginVertical,
      backgroundColor: backgroundColor,
      borderRadius: borderRadius,
      opacity: opacity,
      zIndex: dragged ? 99999999 : null,
      transform: [
        {
          translateX: dragged ? this._offsetX : this._tagTransform[index].x,
        },
        {
          translateY: dragged ? this._offsetY : this._tagTransform[index].y,
        },
      ],
    };
    return (
      <LongPressGestureHandler
        ref={this._longPressRef[index]}
        key={index}
        minDurationMs={longPressResponseTime}
        simultaneousHandlers={this._panRef}
        onHandlerStateChange={e => this._handleLongPress(e, index)}
      >
        <Button
          {...tagElement.props}
          style={style}
          ref={ref => (this._tagRefs[index] = ref)}
          onLayout={e => (this._tagFrames[index] = Object.assign({}, e.nativeEvent.layout))}
          onPress={() => this._onPress(index)}
        />
      </LongPressGestureHandler>
    );
  };

  _onPanStateChange = e => {
    // const { wantDelete } = this.props;
    switch (e.nativeEvent.state) {
      case State.END:
      case State.CANCELLED:
      case State.FAILED:
        if (this._exchangeIndex === -1) return this._clearAllTransformAndUpdate();
        const x =
          this._tagFrames[this._exchangeIndex].x - this._tagFrames[this.state.draggedIndex].x;
        const y =
          this._tagFrames[this._exchangeIndex].y - this._tagFrames[this.state.draggedIndex].y;
        // if (wantDelete) {
        //   this._tags = removeArrIndex(this._tags, this._exchangeIndex);
        //   return this._clearAllTransformAndUpdate();
        // }
        Animated.parallel([
          Animated.timing(this._offsetX, {
            toValue: x,
            duration: 100,
            useNativeDriver: true,
          }),
          Animated.timing(this._offsetY, {
            toValue: y,
            duration: 100,
            useNativeDriver: true,
          }),
        ]).start(this._clearAllTransformAndUpdate);
    }
  };

  _handleLongPress = (e, index) => {
    const { vibration } = this.props;
    switch (e.nativeEvent.state) {
      case State.ACTIVE:
        if (vibration > 0) Vibration.vibrate(vibration, false);
        this.setState({ draggedIndex: index });
        // this.props.setDeleteBox(true);
        break;
    }
  };

  _clearAllTransformAndUpdate = () => {
    this._offsetX.setValue(0);
    this._offsetY.setValue(0);
    this._tagTransform.forEach(tran => {
      tran.x.setValue(0);
      tran.y.setValue(0);
    });
    const { draggedIndex } = this.state;
    if (draggedIndex !== -1 && this._exchangeIndex !== -1 && draggedIndex !== this._exchangeIndex) {
      const tag = this._tags[draggedIndex];
      this._tags.splice(draggedIndex, 1);
      this._tags.splice(this._exchangeIndex, 0, tag);
      this.props.onTagsSorted(this._tags);
      this._autoSortSelectedTags();
    }
    this.setState({ draggedIndex: -1 });
    // this.props.setDeleteBox(false);
    // this.props.setWantDelete(false);
    this._exchangeIndex = -1;
  };

  _panListener = e => {
    const { draggedIndex } = this.state;
    // const { wantDelete } = this.props;
    if (draggedIndex === -1) return;
    const gesture = e.nativeEvent;
    this._tagFrames.every((frame, index) => {
      const frames = this._tagFrames[index];
      if (
        gesture.x > frames.x &&
        gesture.x < frames.x + frames.width &&
        gesture.y > frames.y &&
        gesture.y < frames.y + frames.height
      ) {
        this._exchangeToIndex(draggedIndex, index);
        return false;
      }
      // if (gesture.absoluteY < 500 && wantDelete) {
      //   this.props.setWantDelete(false);
      // }
      // if (gesture.absoluteY >= 500 && !wantDelete) {
      //   this.props.setWantDelete(true);
      //   return false;
      // }
      return true;
    });
  };

  _exchangeToIndex(draggedIndex, toIndex) {
    const now = new Date().getTime();
    const { marginHorizontal, marginVertical } = this.props;
    if (Platform.OS === 'android' && now - this._lastTick < 50) {
      return;
    }
    this._lastTick = now;
    if (this._exchangeIndex !== toIndex) {
      if (this._animatations) {
        this._animatations.stop();
      }
      this._exchangeIndex = toIndex;
      const realFrames = [];
      this._tagFrames.forEach((frame, index) => (realFrames[index] = { ...frame }));
      realFrames[draggedIndex].width = -marginHorizontal * 2;
      realFrames.forEach((frame, index) => {
        if (index > draggedIndex) {
          realFrames[index].x =
            realFrames[index - 1].x + realFrames[index - 1].width + 2 * marginHorizontal;
          realFrames[index].y = realFrames[index - 1].y;
          if (
            realFrames[index].x + realFrames[index].width + marginHorizontal >
            this._frame.width
          ) {
            realFrames[index].x = marginHorizontal;
            realFrames[index].y =
              realFrames[index - 1].y + realFrames[index - 1].height + marginVertical * 2;
          }
        }
      });
      realFrames.forEach((frame, index) => {
        if (
          (draggedIndex > toIndex && index >= toIndex) ||
          (draggedIndex <= toIndex && index > toIndex)
        ) {
          realFrames[index].x += this._tagFrames[draggedIndex].width + 2 * marginHorizontal;
          if (
            realFrames[index].x + realFrames[index].width + marginHorizontal >
            this._frame.width
          ) {
            realFrames[index].x = marginHorizontal;
            realFrames[index].y += realFrames[index].height + marginVertical * 2;
          }
        }
      });
      realFrames.forEach((frame, index) => {
        realFrames[index].offsetX = realFrames[index].x - this._tagFrames[index].x;
        realFrames[index].offsetY = realFrames[index].y - this._tagFrames[index].y;
      });
      const animatations = realFrames
        .map((frame, index) =>
          Animated.timing(this._tagTransform[index].x, {
            toValue: frame.offsetX,
            duration: 100,
            useNativeDriver: true,
          })
        )
        .concat(
          realFrames.map((frame, index) =>
            Animated.timing(this._tagTransform[index].y, {
              toValue: frame.offsetY,
              duration: 100,
              useNativeDriver: true,
            })
          )
        );
      this._animatations = Animated.parallel(animatations);
      this._animatations.start();
    }
  }

  _onContainerLayout = e => {
    this._frame = Object.assign({}, e.nativeEvent.layout);
  };

  _onPress(index) {
    if (!this.props.selectable) return;
    const tag = this._tags[index];
    const indexOf = this._selectedTags.indexOf(tag);
    if (
      indexOf < 0 &&
      this.props.maxCountSelectable > 0 &&
      this._selectedTags.length >= this.props.maxCountSelectable
    )
      return;

    if (indexOf < 0) {
      this._selectedTags.push(tag);
      this.forceUpdate();
      if (!this._autoSortSelectedTags()) {
        this.props.onSelectChange(this._selectedTags);
      }
    } else {
      this._selectedTags.splice(indexOf, 1);
      this.forceUpdate();
      this.props.onSelectChange(this._selectedTags);
    }
  }

  _autoSortSelectedTags() {
    const sortedTags = [];
    this._tags.forEach(tag => {
      if (this._selectedTags.indexOf(tag) >= 0) sortedTags.push(tag);
    });
    if (!this._selectedTags.every((tag, index) => sortedTags[index] === tag)) {
      this._selectedTags = sortedTags;
      this.props.onSelectChange(this._selectedTags);
      return true;
    }
    return false;
  }
}

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  imgCon: {
    borderRadius: 8,
    ...themeLayout.border(scale(1), '#E5E5E5'),
    ...themeLayout.flex(),
    ...themeLayout.margin(scale(5), scale(5)),
    width: scale(106),
    height: scale(106),
    overflow: 'hidden',
  },
  addImg: {
    width: 22,
    height: 22,
  },
});
