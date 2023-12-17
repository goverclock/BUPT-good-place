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

export function MockQueryProfitMonthReq(d) {
    console.log('MockQueryProfitMonthReq: got ', d)
    let t = new Date(d.start_time * 1000)
    return new Promise((resolve) => {
        setTimeout(() => {
            const rn = Math.floor(Math.random() * 100);
            resolve({
                agency_fee: rn * 2,
                count: rn,
                date: "2023-" + t.getMonth()
            });
        }, 1000)
    })
}
