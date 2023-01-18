import request from '@/utils/request'

// 查询租金管理列表
export function listRent(query) {
    return request({
        url: '/rent/info/list',
        method: 'get',
        params: query
    })
}

// 查询租金管理详细
export function getRent(rentId) {
    return request({
        url: '/rent/info/' + rentId,
        method: 'get'
    })
}

// 新增租金管理
export function addRent(data) {
    return request({
        url: '/rent/info',
        method: 'post',
        data: data
    })
}

// 修改租金管理
export function updateRent(data) {
    return request({
        url: '/rent/info',
        method: 'put',
        data: data
    })
}

// 删除租金管理
export function delRent(rentId) {
    return request({
        url: '/rent/info/' + rentId,
        method: 'delete'
    })
}
