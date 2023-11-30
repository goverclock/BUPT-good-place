import request from '../index'

export function GetAllRequestsByCity(d) {
    return request({
        url: 'query_request_by_city',
        method: 'post',
        data: d,
    });
}
