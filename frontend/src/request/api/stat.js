import request from '../index'

export function QueryProfitReq(d) {
    return request({
        url: 'query_profit',
        method: 'post',
        data: d,
    });
}

export function MockQueryProfitReq(d) {
    console.log('MockQueryProfitReq: got ', d)
    return new Promise((resolve) => {
        setTimeout(() => {
            const rn = Math.floor(Math.random() * 100);
            resolve({ data: [rn, rn * 2]});
        }, 1000)
    })
}

export function QueryProfitMonthReq(d) {
    return request({
        url: 'query_profit_by_month',
        method: 'post',
        data: d,
    });
}
