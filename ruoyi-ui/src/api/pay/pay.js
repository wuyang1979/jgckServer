import request from '@/utils/request'

// 查询缴费基本信息列表
export function listPay(query) {
    return request({
        url: '/pay/list',
        method: 'get',
        params: query
    })
}

// 查询缴费基本信息详细
export function getPay(payId) {
    return request({
        url: '/pay/' + payId,
        method: 'get'
    })
}

// 新增缴费基本信息
export function addPay(data) {
    return request({
        url: '/pay',
        method: 'post',
        data: data
    })
}

// 修改缴费基本信息
export function updatePay(data) {
    return request({
        url: '/pay',
        method: 'put',
        data: data
    })
}

// 删除缴费基本信息
export function delPay(payId) {
    return request({
        url: '/pay/' + payId,
        method: 'delete'
    })
}
