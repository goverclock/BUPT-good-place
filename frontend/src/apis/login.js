import request from '../request/index'

export const loginReq = (d) => {
    return request({
        url: 'load',
        method: 'post',
        data: d,
    });
};
