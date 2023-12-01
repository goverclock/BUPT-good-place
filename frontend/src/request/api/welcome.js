import request from '../index'

export function GetAllRequestsByCityReq(d) {
    return request({
        url: 'query_request_by_city',
        method: 'post',
        data: d,
    });
}

export function SubmitWelcomeReq(d) {
    return request({
        url: 'response',
        method: 'post',
        data: d,
    });
}
