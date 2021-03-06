package com.baomidou.mybatisplus.generator.engine;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.ui.util.SOUT;
import freemarker.template.Configuration;
import freemarker.template.Template;
import javafx.scene.control.TextArea;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.ui.sample.Controller.JAR_PATH;
import static com.ui.sample.Controller.MAP;

/**
 * @author : zhilin
 * @date : 2020/06/05
 */
public class ZipTemplateEngine extends AbstractTemplateEngine {

    private Configuration configuration;
    private ZipOutputStream zot;

    public ZipTemplateEngine() {

    }

    public ZipTemplateEngine(ZipOutputStream zot) {
        this.zot = zot;
    }


    @Override
    public ZipTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding(ConstVal.UTF8);
        try {
            configuration.setDirectoryForTemplateLoading(new File(JAR_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }


    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        ZipEntry zipEntry = new ZipEntry(outputFile);
        zot.putNextEntry(zipEntry);
        Template template = configuration.getTemplate(templatePath);
        try  {
            ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            template.process(objectMap, new OutputStreamWriter(dataStream, ConstVal.UTF8));
            zot.write(dataStream.toByteArray());
        }catch (Exception e){
            SOUT.println(e.getMessage());
        }
        zot.closeEntry();
        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
        SOUT.println("模板:" + templatePath + ";  文件:" + outputFile);
    }

    @Override
    public String templateFilePath(String filePath) {

        return filePath + ".ftl";
    }

    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
                TemplateConfig template = getConfigBuilder().getTemplate();
                // 自定义内容
                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initTableMap(tableInfo);
                    objectMap.put("cfg", injectionConfig.getMap());
                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        for (FileOutConfig foc : focList) {
                            writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                        }
                    }
                }
                // Mp.java
                String entityName = tableInfo.getEntityName();
                if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
                    String entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
                    writer(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
                }
                // MpMapper.java
                if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
                    String mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH).replaceAll("mapper", "dao") + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                    writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
                }
                // MpMapper.xml
                if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
                    String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                    writer(objectMap, templateFilePath(template.getXml()), xmlFile);
                }
                // IMpService.java
                if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
                    String serviceFile = String.format((pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                    writer(objectMap, templateFilePath(template.getService()), serviceFile);
                }
                // MpServiceImpl.java
                if (null != tableInfo.getServiceImplName() && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
                    String implFile = String.format((pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                    writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                }
                // MpController.java
                if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
                    String controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                    writer(objectMap, templateFilePath(template.getController()), controllerFile);
                }
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
            SOUT.println(e.getMessage());
        }
        return this;
    }

    @Override
    protected boolean isCreate(FileType fileType, String filePath) {
        ConfigBuilder cb = getConfigBuilder();
        // 自定义判断
        InjectionConfig ic = cb.getInjectionConfig();
        if (null != ic && null != ic.getFileCreate()) {
            return ic.getFileCreate().isCreate(cb, fileType, filePath);
        }
        // 全局判断【默认】
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            // do nothing
        }
        return !exist;
    }
}
