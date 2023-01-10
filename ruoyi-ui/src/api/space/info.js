import request from '@/utils/request'

// 查询空间基本信息列表
export function listSpace(query) {
  return request({
    url: '/space/info/list',
    method: 'get',
    params: query
  })
}

// 查询空间基本信息列表
export function listSpaceNoScope(query) {
  return request({
    url: '/space/info/listNoScope',
    method: 'get',
    params: query
  })
}

// 查询空间基本信息详细
export function getSpace(spaceId) {
  return request({
    url: '/space/info/' + spaceId,
    method: 'get'
  })
}

// 新增空间基本信息
export function addSpace(data) {
  return request({
    url: '/space/info',
    method: 'post',
    data: data
  })
}

// 修改空间基本信息
export function updateSpace(data) {
  return request({
    url: '/space/info',
    method: 'put',
    data: data
  })
}

// 删除空间基本信息
export function delSpace(spaceId) {
  return request({
    url: '/space/info/' + spaceId,
    method: 'delete'
  })
}
