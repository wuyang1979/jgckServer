import request from '@/utils/request'

// 查询合同信息列表
export function listContract(query) {
  return request({
    url: '/contract/info/list',
    method: 'get',
    params: query
  })
}

// 查询合同信息详细
export function getContract(contractId) {
  return request({
    url: '/contract/info/' + contractId,
    method: 'get'
  })
}

// 新增合同信息
export function addContract(data) {
  return request({
    url: '/contract/info',
    method: 'post',
    data: data
  })
}

// 修改合同信息
export function updateContract(data) {
  return request({
    url: '/contract/info',
    method: 'put',
    data: data
  })
}

// 删除合同信息
export function delContract(contractId) {
  return request({
    url: '/contract/info/' + contractId,
    method: 'delete'
  })
}
