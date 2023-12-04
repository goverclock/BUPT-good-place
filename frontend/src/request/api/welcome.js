import request from '../index'

export function GetAllRequestsByCityReq(d) {
    return request({
        url: 'query_request_by_city',
        method: 'post',
        data: d,
    });
}

export function SubmitResponseReq(d) {
    return request({
        url: 'response',
        method: 'post',
        data: d,
    });
}

export function DeleteResponseReq(d) {
    return request({
        url: 'response_delete',
        method: 'post',
        data: d,
    });
}

export function UpdateResponseReq(d) {
    return request({
        url: 'response_update',
        method: 'post',
        data: d,
    });
}
