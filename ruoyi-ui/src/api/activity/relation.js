import request from '@/utils/request'
import da from "element-ui/src/locale/lang/da";

// 查询活动与用户关系列表
export function listRelation(query) {
  return request({
    url: '/relation/relation/list',
    method: 'get',
    params: query
  })
}

// 查询活动与用户关系详细
export function getRelation(activityId) {
  return request({
    url: '/activity/relation/' + activityId,
    method: 'get'
  })
}

// 新增活动与用户关系
export function addRelation(data) {
  return request({
    url: '/activity/relation',
    method: 'post',
    data: data
  })
}

// 修改活动与用户关系
export function updateRelation(data) {
  return request({
    url: '/activity/relation',
    method: 'put',
    data: data
  })
}

// 删除活动与用户关系
export function delRelation(activityId) {
  return request({
    url: '/activity/relation/' + activityId,
    method: 'delete'
  })
}


// 取消活动报名
export function cancelRelation(activityId) {
  return request({
    url: '/activity/relation?activityId=' + activityId,
    method: 'delete'
  })
}


// 查询活动与用户关系详细
export function listPerson(activityId) {
  return request({
    url: '/activity/relation/person?activityId=' + activityId,
    method: 'get'
  })
}

// 删除活动与用户关系
export function delRelationAll(activityId) {
  return request({
    url: '/activity/relation/all?activityId=' + activityId,
    method: 'delete'
  })
}


export function editFileByBusinessId(businessId, fileIds) {
  var data = {
    businessId: businessId,
    fileIds: fileIds,
  };
  return request({
    url: '/file/editByBusinessId',
    method: 'put',
    data: data
  })
}


export function listFile(businessId) {
  var query = {
    businessId: businessId,
    pageSize: 9999,
  };
  return request({
    url: '/file/list',
    method: 'get',
    params: query
  })
}
