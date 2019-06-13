import config from '@/config';
import RNFetchBlob from 'react-native-fetch-blob';

export const uploadImg = async ({ format, size, base64 }) => {
  const searchParams = new FormData();

  searchParams.append('access_token', config.getUserInfo().access_token);
  searchParams.append('app_ver', config.app_ver);
  searchParams.append('app_ver_code', config.app_ver_code);
  searchParams.append('ch', config.ch);
  searchParams.append('channel_id', config.channel_id);
  searchParams.append('image_type', format);
  searchParams.append('image_size', size);
  searchParams.append('image_content', base64);

  const response = await fetch(`${config.baseUrl1}/interface/v1/user/auth/upload_picture`, {
    body: searchParams,
    method: 'POST',
  })
    .then(res => res.json())
    .catch(fetchErr => console.log('fetchErr', fetchErr));

  return response;
};

export const uploadVideo = async ({ path, ...rest }) => {
  const data = {
    access_token: config.getUserInfo().access_token,
    channel_id: config.channel_id,
    mobilephone: config.getUserInfo().mobilephone,
    app_ver: config.app_ver,
    app_ver_code: config.app_ver_code,
    ch: config.ch,
    ...rest,
  };
  console.log('%cbody:', 'color: #0e93e0;background: #aaefe5;', data);
  const response = await RNFetchBlob.fetch(
    'POST',
    `${config.baseUrl}/interface/v1/app/content/uploadvideo`,
    {}, // headers
    [
      {
        name: 'file',
        filename: 'video.mp4', // elements without property `filename` will be sent as plain text
        data: RNFetchBlob.wrap(path),
      },
      {
        name: 'body',
        data: JSON.stringify(data),
      },
    ]
  )
    .then(res => res.json())
    .catch(err => {
      console.log('%cerr:', 'color: #0e93e0;background: #aaefe5;', err);
    });

  return response;
};
