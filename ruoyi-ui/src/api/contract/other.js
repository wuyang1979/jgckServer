import request from '@/utils/request'

// 查询其他合同列表
export function listOther(query) {
    return request({
        url: '/contract/other/list',
        method: 'get',
        params: query
    })
}

// 查询其他合同详细
export function getOther(contractId) {
    return request({
        url: '/contract/other/' + contractId,
        method: 'get'
    })
}

// 新增其他合同
export function addOther(data) {
    return request({
        url: '/contract/other',
        method: 'post',
        data: data
    })
}

// 修改其他合同
export function updateOther(data) {
    return request({
        url: '/contract/other',
        method: 'put',
        data: data
    })
}

// 删除其他合同
export function delOther(contractId) {
    return request({
        url: '/contract/other/' + contractId,
        method: 'delete'
    })
}
