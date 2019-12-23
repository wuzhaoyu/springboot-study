package com.wzy.common.shiro;

import com.wzy.common.utill.ShiroUtils;
import com.wzy.entities.SysMenuEntity;
import com.wzy.entities.SysRoleEntity;
import com.wzy.entities.SysUserEntity;
import com.wzy.service.SysMenuService;
import com.wzy.service.SysRoleService;
import com.wzy.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

/**
 * 类功能说明: shiro权限匹配和账号密码匹配
 * 类修改者	创建日期2019/12/20
 * 修改说明
 *
 * @author wzy
 * @version V1.0
 **/
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;


    /**
     * 授权权限
     * 用户进行权限检验时候Shiro会去缓存中找，如果查不到数据，会执行这个方法的去查权限
     * 并放入缓存中
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        SysUserEntity sysUserEntity = (SysUserEntity) principalCollection.getPrimaryPrincipal();
        //获取用户ID
        Long userId = sysUserEntity.getUserId();
        //授权和处理
        HashSet<String>  rolesSet = new HashSet<>();
        HashSet<String>  permsSet = new HashSet<>();
        //查询角色和权限（根据业务自行查询）
        List<SysRoleEntity> sysRoleEntities = sysRoleService.selectSysRoleByUserId(userId);
        //通过人查询权限，权限查询菜单
        for (SysRoleEntity sysRoleEntity : sysRoleEntities){
            rolesSet.add(sysRoleEntity.getRoleName());
            List<SysMenuEntity> sysMenuEntities = sysMenuService.selectSysMenuByRoleId(sysRoleEntity.getRoleId());
            for (SysMenuEntity sysMenuEntity : sysMenuEntities){
                permsSet.add(sysMenuEntity.getPerms());
            }
        }
        // 将查询的权限和角色分别传入simpleAuthorizationInfo,为后做权限进行校验
        simpleAuthorizationInfo.setRoles(rolesSet);
        simpleAuthorizationInfo.setStringPermissions(permsSet);
        return simpleAuthorizationInfo;
    }


   /**
    *
    * @Description: 身份认证
    * @Author:   wzy
    * @Date:      2019/12/20 16:42
    * @Param:
    * @return:
    **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = authenticationToken.getPrincipal().toString();
        //通过username从数据库查找 User对象，如果找到进行验证
        //实际项目中，可以根据实际情况做缓存，如果不做，Shiro自己也有时间间隔机制，2分钟内不会重复执行该方法
        SysUserEntity user = sysUserService.selectUserByName(username);
        //判断账号是否存在
        if(user == null){
            throw new AuthenticationException();
        }
        //判断账号是否被冻结
        if(user.getState()==null || "PROHIBIT".equals(user.getState())){
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,                     //用户名
                user.getPassword(),                     //密码
                ByteSource.Util.bytes(user.getSalt()), //设置盐值
                getName()
        );
        //验证成功开始踢人(清除缓存和Session)
        ShiroUtils.deleteCache(username,true);
        return authenticationInfo;
    }
}
