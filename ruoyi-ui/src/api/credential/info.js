import request from '@/utils/request'

// 查询证件基本信息列表
export function listCredential(query) {
  return request({
    url: '/credential/info/list',
    method: 'get',
    params: query
  })
}

// 查询证件基本信息详细
export function getCredential(credentialId) {
  return request({
    url: '/credential/info/' + credentialId,
    method: 'get'
  })
}

// 新增证件基本信息
export function addCredential(data) {
  return request({
    url: '/credential/info',
    method: 'post',
    data: data
  })
}

// 修改证件基本信息
export function updateCredential(data) {
  return request({
    url: '/credential/info',
    method: 'put',
    data: data
  })
}

// 删除证件基本信息
export function delCredential(credentialId) {
  return request({
    url: '/credential/info/' + credentialId,
    method: 'delete'
  })
}
