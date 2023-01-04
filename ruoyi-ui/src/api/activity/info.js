import request from '@/utils/request'

// 查询活动基本信息列表
export function listActivity(query) {
  return request({
    url: '/activity/info/list',
    method: 'get',
    params: query
  })
}

// 查询活动基本信息详细
export function getActivity(activityId) {
  return request({
    url: '/activity/info/' + activityId,
    method: 'get'
  })
}

// 新增活动基本信息
export function addActivity(data) {
  return request({
    url: '/activity/info',
    method: 'post',
    data: data
  })
}

// 修改活动基本信息
export function updateActivity(data) {
  return request({
    url: '/activity/info',
    method: 'put',
    data: data
  })
}

// 删除活动基本信息
export function delActivity(activityId) {
  return request({
    url: '/activity/info/' + activityId,
    method: 'delete'
  })
}


export function selectSign() {
  return request({
    url: '/activity/info/selectSignList',
    method: 'get'
  })
}
