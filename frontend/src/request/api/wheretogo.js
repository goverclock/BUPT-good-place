import request from '../index'

export function PublishPlaceReq(d) {
    return request({
        url: 'findplace',
        method: 'post',
        data: d,
    });
}

export function GetAllRequests(d) {
    return request({
        url: 'query_request_by_user_id',
        method: 'post',
        data: d,
    });
}

export function DeleteRequestReq(d) {
    return request({
        url: 'request_delete',
        method: 'post',
        data: d,
    });
}
