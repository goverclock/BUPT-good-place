import request from '../index'

export function PublishPlaceReq(d) {
    return request({
        url: 'findplace',
        method: 'post',
        data: d,
    });
}
