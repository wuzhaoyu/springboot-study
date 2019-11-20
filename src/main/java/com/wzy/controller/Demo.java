package com.wzy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类功能说明:
 * 类修改者	创建日期2019/11/19
 * 修改说明
 *
 * @author wzy
 * @version V1.0
 **/
@Controller
public class Demo {

    /**
     * @Response 方法的返回值以特定的格式写入到response的body区域
     * @return
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String demo(){
        return "Hello World";
    }
}
