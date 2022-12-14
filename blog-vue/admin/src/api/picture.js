import request from "@/utils/request";

/**
 * 获取文件列表
 *
 * @param {*} params
 * @returns
 */
export const getFilesList = (params) => {
  return request({
    url: "/api/files/getFilesList",
    method: "GET",
    params: params,
  });
};

/**
 * 上传文件
 *
 * @param {*} data
 * @returns
 */
export const upload = (data) => {
  return request({
    url: "/api/upload/",
    method: "POST",
    data: data,
  });
};

/**
 * 批量删除文件信息
 *
 * @param {*} ids
 * @returns
 */
export const deleteFileBatchIds = (ids) => {
  return request({
    url: `/api/files/deleteFileBatchIds/${ids}`,
    method: "DELETE",
  });
};

/**
 * 更新文件信息
 *
 * @param {*} data
 * @returns
 */
export const updateFileInfo = (data) => {
  return request({
    url: "/api/files/updateFileInfo",
    method: "POST",
    data: data,
  });
};
