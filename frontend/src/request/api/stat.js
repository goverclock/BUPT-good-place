import request from '../index'

export function QueryProfitReq(d) {
    return request({
        url: 'query_profit',
        method: 'post',
        data: d,
    });
}

export function QueryProfitMonthReq(d) {
    return request({
        url: 'query_profit_by_month',
        method: 'post',
        data: d,
    });
}
