import request from '@/utils/request';
import config from '@/config';

export async function searchReq(params) {
  return request(
    `${config.baseUrl}/interface/v1/app/label/search_list?params=${JSON.stringify(params)}`
  );
}
