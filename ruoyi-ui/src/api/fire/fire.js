import request from '@/utils/request'

// 查询消防安全列表
export function listFire(query) {
    return request({
        url: '/fire/list',
        method: 'get',
        params: query
    })
}

// 查询消防安全详细
export function getFire(fireId) {
    return request({
        url: '/fire/' + fireId,
        method: 'get'
    })
}

// 新增消防安全
export function addFire(data) {
    return request({
        url: '/fire',
        method: 'post',
        data: data
    })
}

// 修改消防安全
export function updateFire(data) {
    return request({
        url: '/fire',
        method: 'put',
        data: data
    })
}

// 删除消防安全
export function delFire(fireId) {
    return request({
        url: '/fire/' + fireId,
        method: 'delete'
    })
}
