package cn.com.kando.base.generater.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : zhilin
 * @date : 2020/06/04
 */
public class InjectionAutoConfig {
    public static InjectionConfig autoConfig(PackageConfig pc, String projectPath, String outReferencePath){
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing

            }
            // 给模板增加变量
            @Override
            public Map<String, Object> prepareObjectMap(Map<String, Object> objectMap) {
                HashMap<String, String> packageMap = (HashMap<String, String>) objectMap.get("package");
                packageMap.put("Result",pc.getParent()+".dto.Result");
                packageMap.put("basePath",pc.getParent());
                packageMap.put("pageQuery",pc.getParent()+".ao.PageQuery");
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
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig(resultTemplate) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // result输出
                return projectPath + "/src/main/java/" + outReferencePath +"/" +pc.getModuleName() +"/dto/Result.java" ;
            }
        });
        focList.add(new FileOutConfig(pageQueryTemplaye) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java/" + outReferencePath +"/" +pc.getModuleName() +"/ao/PageQuery.java";
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }
}
