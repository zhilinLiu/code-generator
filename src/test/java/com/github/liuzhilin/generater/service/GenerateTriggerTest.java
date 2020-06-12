package com.github.liuzhilin.generater.service;

import com.github.liuzhilin.generater.ao.CodeAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        codeAO.setModuleReference("cn.com.kando.base.module");
        //要生成的模块名
        codeAO.setModuleName("dailyprogress");
        //设置作者
        codeAO.setAuthor("zhilin");
        //设置表名，多个表使用多逗号分割
        codeAO.setTableNames("rb_daily_progress");
        //设置本地输出路径
        String outPath = "d:/dailyprogress.zip";

        ByteArrayOutputStream byteArrayOutputStream = generateTrigger.generateCode(codeAO.getModuleReference(), codeAO.getModuleName(),
                codeAO.getAuthor(),
                codeAO.getTableNames());
        FileOutputStream fileOutputStream = new FileOutputStream(outPath);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.close();

    }
    @Test
    public void test1() throws IOException {
        ZipEntry zipEntry = new ZipEntry("/home");
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("d:/test.zip"));
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.closeEntry();
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.closeEntry();
    }
}