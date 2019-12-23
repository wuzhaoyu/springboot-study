package com.wzy.common.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * 类功能说明: 自定sessionId生成器
 * 类修改者	创建日期2019/12/20
 * 修改说明
 *
 * @author wzy
 * @version V1.0
 **/
public class ShiroSeesionGenerator implements SessionIdGenerator {
    /**
     * 生成sessionId
     * @param session
     * @return
     */
    @Override
    public Serializable generateId(Session session) {
        Serializable serializable = new JavaUuidSessionIdGenerator().generateId(session);
        return String.format("login_token_%s",serializable);
    }
}
