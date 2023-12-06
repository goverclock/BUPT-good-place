import request from '../index'

export function PublishPlaceReq(d) {
    return request({
        url: 'findplace',
        method: 'post',
        data: d,
    });
}

export function GetAllRequestsByUser(d) {
    return request({
        url: 'query_request_by_user_id',
        method: 'post',
        data: d,
    });
}

export function GetResponseByRequestId(d) {
    return request({
        url: 'query_response_by_requestid',
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

export function UpdateRequestReq(d) {
    return request({
        url: 'request_update',
        method: 'post',
        data: d,
    });
}

export function AcceptResponseReq(d) {
    return request({
        url: 'accept_response',
        method: 'post',
        data: d,
    });
}
