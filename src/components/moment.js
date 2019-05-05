import moment from 'moment';

moment.locale('zh-cn', {
  relativeTime: {
    future: 'in %s',
    past: '%s 前',
    s: '刚刚',
    ss: '刚刚',
    m: '1分钟前',
    mm: '%d 分钟前',
    h: '1小时',
    hh: '%d 小时前',
    d: '1天前',
    dd: '%d 天前',
    M: '1月前',
    MM: '%d 月前',
    y: '1年前',
    yy: '%d 年前',
  },
});

export default moment;
