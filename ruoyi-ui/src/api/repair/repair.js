import request from '@/utils/request'

// 查询报修反馈基本信息列表
export function listRepair(query) {
    return request({
        url: '/repair/list',
        method: 'get',
        params: query
    })
}

// 查询报修反馈基本信息详细
export function getRepair(repairId) {
    return request({
        url: '/repair/' + repairId,
        method: 'get'
    })
}

// 新增报修反馈基本信息
export function addRepair(data) {
    return request({
        url: '/repair',
        method: 'post',
        data: data
    })
}

// 修改报修反馈基本信息
export function updateRepair(data) {
    return request({
        url: '/repair',
        method: 'put',
        data: data
    })
}

// 删除报修反馈基本信息
export function delRepair(repairId) {
    return request({
        url: '/repair/' + repairId,
        method: 'delete'
    })
}
