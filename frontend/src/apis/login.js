import request from '../request/index'

export const LoginReq = (d) => {
    return request({
        url: 'load',
        method: 'post',
        data: d,
    });
};
