// 入驻统计
import request from "@/utils/request";

export function getSettle(spaceId) {
  return request({
    url: '/statistics/settle/' + spaceId,
    method: 'get'
  })
}


export function getContract(spaceId) {
  return request({
    url: '/statistics/contract/' + spaceId,
    method: 'get'
  })
}


export function getCredential(spaceId) {
  return request({
    url: '/statistics/credential/' + spaceId,
    method: 'get'
  })
}

export function setRemindConfig(param) {
  return request({
    url: '/statistics/remind/setConfig',
    method: 'post',
    data: param
  })
}


export function getRentStatistics(year) {
  return request({
    url: '/statistics/rent/' + year,
    method: 'get'
  })
}
