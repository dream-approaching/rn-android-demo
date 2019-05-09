import { ToastAndroid } from 'react-native';

let toast;
class Toast {
  show = (msg, during) => ToastAndroid.show(msg, during);
}

export default {
  show(msg, during = 'short') {
    toast = new Toast();
    const time = during === 'short' ? ToastAndroid.SHORT : ToastAndroid.LONG;
    toast.show(msg, time);
  },
};
