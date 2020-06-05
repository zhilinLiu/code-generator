package cn.com.kando.base.generater.service;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.ZipTemplateEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成触发器
 *
 * @author : zhilin
 * @date : 2020/06/05
 */
@Component
public class GenerateTrigger {
    @Autowired
    private DataSourceConfig dsc;

    /**
     * 代码生成
     *
     * @param referencePath 包路径  例： com.github.test
     * @param moduleName    模块名
     * @param author        作者
     * @param tableName     表名，如果有多个表，请用英文逗号分割
     * @return 输出字节数组
     */
    public ByteArrayOutputStream generateCode(String referencePath, String moduleName, String author, String tableName) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = "";
        String dirName = "src/main/java";
        gc.setOutputDir(projectPath + dirName);
        gc.setAuthor(author);
        gc.setOpen(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);


        // 数据源配置

        mpg.setDataSource(dsc);


        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(referencePath);
        mpg.setPackageInfo(pc);
        String outReferencePath = referencePath.replaceAll("\\.", "/");


        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing

            }

            // 给模板增加变量
            @Override
            public Map<String, Object> prepareObjectMap(Map<String, Object> objectMap) {
                HashMap<String, String> packageMap = (HashMap<String, String>) objectMap.get("package");
                packageMap.put("Result", pc.getParent() + ".dto.Result");
                packageMap.put("basePath", pc.getParent());
                packageMap.put("pageQuery", pc.getParent() + ".ao.PageQuery");

                return super.prepareObjectMap(objectMap);
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        String resultTemplate = "/templates/Result.java.ftl";
        String pageQueryTemplaye = "/templates/PageQuery.java.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                String path = projectPath + "src/main/resources/mapper/" + pc.getModuleName() + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                return path;
            }
        });
        focList.add(new FileOutConfig(resultTemplate) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // result输出
                String path = projectPath + dirName + "/" + outReferencePath + "/" + pc.getModuleName() + "/dto/Result.java";
                return path;
            }
        });
        focList.add(new FileOutConfig(pageQueryTemplaye) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + dirName + "/" + outReferencePath + "/" + pc.getModuleName() + "/ao/PageQuery.java";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//         templateConfig.setEntity("templates/entity2.java");
//         templateConfig.setService("templates/MyService.java");
//         templateConfig.setController("templates/MyController.java");
//        templateConfig.setServiceImpl("templates/MyServiceimpl.java");

        templateConfig.setXml(null);

        mpg.setTemplate(templateConfig);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        //设置模版引擎
        mpg.setTemplateEngine(new ZipTemplateEngine());
        mpg.execute();

        return mpg.getOutputStream();
    }
}
