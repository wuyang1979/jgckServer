// 入驻统计
import request from "@/utils/request";

export function getSettle() {
  return request({
    url: '/statistics/settle',
    method: 'get'
  })
}


export function getContract() {
  return request({
    url: '/statistics/contract',
    method: 'get'
  })
}


export function getCredential() {
  return request({
    url: '/statistics/credential',
    method: 'get'
  })
}
