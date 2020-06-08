package com.github.generater.controller;

import com.github.generater.ao.CodeAO;
import com.github.generater.service.GenerateTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author : zhilin
 * @date : 2020/06/04
 */
@RestController
@RequestMapping("generateCode")
public class CodeController {
    @Autowired
    private GenerateTrigger trigger;

    @GetMapping
    public void generateCode(CodeAO codeAO, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream outputStream = trigger.generateCode(codeAO.getModuleReference(),
                codeAO.getModuleName(),
                codeAO.getAuthor(),
                codeAO.getTableNames());

        response.setContentType("multipart/form-data");
        //为文件重新设置名字，采用数据库内存储的文件名称
        response.addHeader("Content-Disposition"
                , "attachment; filename=\"" + new String((codeAO.getModuleName()+".zip").getBytes("UTF-8")
                        , "ISO8859-1") + "\"");

        ServletOutputStream out = response.getOutputStream();

        out.write(outputStream.toByteArray());

        out.flush();
    }
}
