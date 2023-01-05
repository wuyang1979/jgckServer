import request from '@/utils/request'

// 查询历史租客基本信息列表
export function listTenantsHistory(query) {
  return request({
    url: '/tenants/history/list',
    method: 'get',
    params: query
  })
}

// 查询租客基本信息列表
export function listTenants(query) {
  return request({
    url: '/tenants/info/list',
    method: 'get',
    params: query
  })
}

// 查询租客基本信息列表(无数据过滤)
export function listTenantsNoScope(query) {
  return request({
    url: '/tenants/info/listNoScope',
    method: 'get',
    params: query
  })
}

// 查询租客基本信息详细
export function getTenants(tenantsId) {
  return request({
    url: '/tenants/info/' + tenantsId,
    method: 'get'
  })
}

// 新增租客基本信息
export function addTenants(data) {
  return request({
    url: '/tenants/info',
    method: 'post',
    data: data
  })
}

// 修改租客基本信息
export function updateTenants(data) {
  return request({
    url: '/tenants/info',
    method: 'put',
    data: data
  })
}

// 删除租客基本信息
export function delTenants(tenantsId) {
  return request({
    url: '/tenants/info/' + tenantsId,
    method: 'delete'
  })
}
