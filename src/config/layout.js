import { themeCatSize } from './size';
import { themeCatColor } from './color';

function marginPadding(type, arg) {
  let typeObj = {};
  switch (arg.length) {
    case 1:
      typeObj = {
        [`${type}Top`]: arg[0],
        [`${type}Right`]: arg[0],
        [`${type}Bottom`]: arg[0],
        [`${type}Left`]: arg[0],
      };
      break;
    case 2:
      typeObj = {
        [`${type}Top`]: arg[0],
        [`${type}Right`]: arg[1],
        [`${type}Bottom`]: arg[0],
        [`${type}Left`]: arg[1],
      };
      break;
    case 3:
      typeObj = {
        [`${type}Top`]: arg[0],
        [`${type}Right`]: arg[1],
        [`${type}Bottom`]: arg[2],
        [`${type}Left`]: arg[1],
      };
      break;
    case 4:
      typeObj = {
        [`${type}Top`]: arg[0],
        [`${type}Right`]: arg[1],
        [`${type}Bottom`]: arg[2],
        [`${type}Left`]: arg[3],
      };
      break;
    default:
      break;
  }
  return typeObj;
}

export const themeLayout = {
  flex: (direc = 'row', justify = 'center', align = 'center') => {
    return {
      display: 'flex',
      flexDirection: direc,
      justifyContent: justify,
      alignItems: align,
    };
  },
  margin(...args) {
    return marginPadding('margin', args);
  },
  padding(...args) {
    return marginPadding('padding', args);
  },
  borderSide(
    side = 'Bottom',
    color = themeCatColor.borderColor,
    width = themeCatSize.minBorder,
    borderStyle = 'solid'
  ) {
    return {
      [`border${side}Width`]: width,
      [`border${side}Color`]: color,
      borderStyle,
    };
  },
  border(
    borderWidth = themeCatSize.minBorder,
    borderColor = themeCatColor.borderColor,
    borderStyle = 'solid'
  ) {
    return {
      borderWidth,
      borderColor,
      borderStyle,
    };
  },
};
