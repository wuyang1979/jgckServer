import request from '@/utils/request'

// 查询品牌中心基本信息列表
export function listBrand(query) {
    return request({
        url: '/brand/list',
        method: 'get',
        params: query
    })
}

// 查询品牌中心基本信息详细
export function getBrand(brandId) {
    return request({
        url: '/brand/' + brandId,
        method: 'get'
    })
}

// 新增品牌中心基本信息
export function addBrand(data) {
    return request({
        url: '/brand',
        method: 'post',
        data: data
    })
}

// 修改品牌中心基本信息
export function updateBrand(data) {
    return request({
        url: '/brand',
        method: 'put',
        data: data
    })
}

// 删除品牌中心基本信息
export function delBrand(brandId) {
    return request({
        url: '/brand/' + brandId,
        method: 'delete'
    })
}
