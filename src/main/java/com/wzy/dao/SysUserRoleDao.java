package com.wzy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.entities.SysUserRoleEntity;
import org.springframework.stereotype.Repository;

/**
 * @Description 用户与角色关系DAO
 * @Author Sans
 * @CreateTime 2019/6/14 15:57
**/
@Repository
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {
	
}
