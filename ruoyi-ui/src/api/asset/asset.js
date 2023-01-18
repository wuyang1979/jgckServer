import request from '@/utils/request'

// 查询固定资产基本信息列表
export function listAsset(query) {
  return request({
    url: '/asset/list',
    method: 'get',
    params: query
  })
}

// 查询固定资产基本信息详细
export function getAsset(assetId) {
  return request({
    url: '/asset/' + assetId,
    method: 'get'
  })
}

// 新增固定资产基本信息
export function addAsset(data) {
  return request({
    url: '/asset',
    method: 'post',
    data: data
  })
}

// 修改固定资产基本信息
export function updateAsset(data) {
  return request({
    url: '/asset',
    method: 'put',
    data: data
  })
}

// 删除固定资产基本信息
export function delAsset(assetId) {
  return request({
    url: '/asset/' + assetId,
    method: 'delete'
  })
}
