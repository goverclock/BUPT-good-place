// https://github.com/goverclock/BUPT-good-place/blob/main/README.md

import request from '../index'

export function LoginReq(d) {
    return request({
        url: 'load',
        method: 'post',
        data: d,
    });
}

export function RegisterReq(d) {
    return request({
        url: 'register',
        method: 'post',
        data: d,
    });
};

export function GetUserInfoReq(d) {
    return request({
        url: 'get_user_information',
        method: 'post',
        data: d,
    });
}

export function UpdateInformationReq(d) {
    return request({
        url: 'update_information',
        method: 'post',
        data: d,
    });
}

export function UpdatePasswordReq(d) {
    return request({
        url: 'update_password',
        method: 'post',
        data: d,
    });
}
