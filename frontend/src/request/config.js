import cfg from '../config'

// export default {
//   // 基础url前缀
//   baseURL: cfg.serverAddr,
//   // 请求头信息
//   headers: {
//     'Content-Type': 'application/json',
//     'token': localStorage.getItem('pm_token'),
//   },
//   // 设置超时时间
//   timeout: 10000,
//   // 携带凭证
//   withCredentials: false,
//   // 返回数据类型
//   responseType: 'json'
// }

export default function getConfig() {
  let ret = {
    // 基础url前缀
    baseURL: cfg.serverAddr,
    // 请求头信息
    headers: {
      'Content-Type': 'application/json',
      'token': localStorage.getItem('pm_token'),
    },
    // 设置超时时间
    timeout: 10000,
    // 携带凭证
    withCredentials: false,
    // 返回数据类型
    responseType: 'json'
  }
  return ret
}
