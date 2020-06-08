package com.github.generater.service;

import com.github.generater.ao.CodeAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author : zhilin
 * @date : 2020/06/05
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GenerateTriggerTest {
    @Autowired
    GenerateTrigger generateTrigger;

    @Test
    public void test() throws IOException {

        CodeAO codeAO = new CodeAO();
        //设置包名
        codeAO.setModuleReference("com.github");
        //要生成的模块名
        codeAO.setModuleName("user");
        //设置作者
        codeAO.setAuthor("zhilin");
        //设置表名，多个表使用多逗号分割
        codeAO.setTableNames("kd_sys_user");
        //设置本地输出路径
        String outPath = "d:/user.zip";

        ByteArrayOutputStream byteArrayOutputStream = generateTrigger.generateCode(codeAO.getModuleReference(), codeAO.getModuleName(),
                codeAO.getAuthor(),
                codeAO.getTableNames());
        FileOutputStream fileOutputStream = new FileOutputStream(outPath);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.close();

    }
}