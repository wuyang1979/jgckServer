import request from '@/utils/request'

// 查询房源带看基本信息列表
export function listLook(query) {
  return request({
    url: '/room/look/list',
    method: 'get',
    params: query
  })
}

// 查询房源带看基本信息详细
export function getLook(lookId) {
  return request({
    url: '/room/look/' + lookId,
    method: 'get'
  })
}

// 新增房源带看基本信息
export function addLook(data) {
  return request({
    url: '/room/look',
    method: 'post',
    data: data
  })
}

// 修改房源带看基本信息
export function updateLook(data) {
  return request({
    url: '/room/look',
    method: 'put',
    data: data
  })
}

// 删除房源带看基本信息
export function delLook(lookId) {
  return request({
    url: '/room/look/' + lookId,
    method: 'delete'
  })
}

// 获取机构树
export function getTree() {
  return request({
    url: 'system/user/userDeptTree',
    method: 'get'
  })
}
