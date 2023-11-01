import request from '../request/index'

export const RegisterReq = (d) => {
    return request({
        url: 'register',
        method: 'post',
        data: d,
    });
};
