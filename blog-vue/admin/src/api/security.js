import request from "@/utils/request";

export const getAllMenus = (data) => {
  return request({
    url: "/admin/getAllMenus",
    method: "POST",
    data,
  });
};

export const getAllRoles = () => {
  return request({
    url: "/role/admin/getAllRoles",
    method: "GET",
  });
};

export const insertRole = (data) => {
  return request({
    url: "/role/admin/insertRole",
    method: "POST",
    data,
  });
};

export const updateRole = (data) => {
  return request({
    url: "/role/admin/updateRole",
    method: "PUT",
    data,
  });
};

export const deleteRole = (id) => {
  return request({
    url: `/role/admin/deleteRole/${id}`,
    method: "DELETE",
  });
};

export const getAllPermission = () => {
  return request({
    url: "/admin/getAllPermission",
    method: "GET",
  });
};

/**
 * 根据用户ID获取用户对应的角色ID
 *
 * @param {*} id
 * @returns
 */
export const getRoleByUserId = (id) => {
  return request({
    url: `/user-role/getRoleByUserId/${id}`,
    method: "GET",
  });
};

/**
 * 更新用户角色
 *
 * @param {*} params
 * @returns
 */
export const updateUserRole = (params) => {
  return request({
    url: "/user-role/updateUserRole",
    method: "GET",
    params: params,
  });
};

/**
 * 获取所有资源权限
 *
 * @returns
 */
export const getResourceList = () => {
  return request({
    url: "/resource/getResourceList",
    method: "GET",
  });
};

/**
 * 根据角色ID获取对应的资源权限
 *
 * @param {*} id
 * @returns
 */
export const getResourceById = (id) => {
  return request({
    url: `/resource/getResourceById/${id}`,
    method: "GET",
  });
};

/**
 * 更新用户的资源权限
 *
 * @param {*} data
 * @returns
 */
export const updateResourceRole = (data) => {
  return request({
    url: "/resource-role/updateResourceRole",
    method: "POST",
    data: data,
  });
};
