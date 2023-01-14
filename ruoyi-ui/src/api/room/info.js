import request from '@/utils/request'

// 查询房源基本信息列表
export function listRoom(query) {
    return request({
        url: '/room/info/list',
        method: 'get',
        params: query
    })
}

// 查询房源基本信息列表
export function listRoomByTenantsId(query) {
    return request({
        url: '/room/info/listByTenantsId',
        method: 'get',
        params: query
    })
}

// 查询房源基本信息列表
export function listRoomNoScope(query) {
    return request({
        url: '/room/info/listNoScope',
        method: 'get',
        params: query
    })
}

// 查询房源基本信息详细
export function getRoom(roomId) {
    return request({
        url: '/room/info/' + roomId,
        method: 'get'
    })
}

// 新增房源基本信息
export function addRoom(data) {
    return request({
        url: '/room/info',
        method: 'post',
        data: data
    })
}

// 修改房源基本信息
export function updateRoom(data) {
    return request({
        url: '/room/info',
        method: 'put',
        data: data
    })
}

// 删除房源基本信息
export function delRoom(roomId) {
    return request({
        url: '/room/info/' + roomId,
        method: 'delete'
    })
}
