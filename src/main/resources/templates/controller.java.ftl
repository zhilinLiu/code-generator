package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${package.Result};
import ${package.pageQuery};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 *
 * ${table.comment!} 前端控制器
 *
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    private ${table.serviceName} service;
   
   
    /**
    * 新增
    *
    * @param entity
    * @return
    */
    @PostMapping
    public Result addJob(@RequestBody ${entity} entity) {
    Result<${entity}> result = new Result<>();
        try {
            //任务执行代码
            service.insert(entity);
            result.setCode(200);
            result.setMessage("新增成功成功");
        } catch (Exception e) {
            result.setCode(500);
            result.setMessage(e.getMessage());
        }
            return result;
    }
   
    /**
    * 删除
    */
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") Integer id){
    Result<${entity}> result = new Result<>();
        try {
            //任务执行代码
            service.delete(id);
            result.setCode(200);
            result.setMessage("删除成功");
        } catch (Exception e) {
            result.setCode(500);
            result.setMessage(e.getMessage());
        }
            return result;
    }
   
    /**
    * 修改
    * @param entity
    * @return
    */
    @PutMapping
    public Result update(@RequestBody ${entity} entity){
        Result<${entity}> result = new Result<>();
        try {
            service.update(entity);
            result.setCode(200);
            result.setMessage("修改成功");
        } catch (Exception e) {
            result.setCode(500);
            result.setMessage(e.getMessage());
        }
            return result;
    }
   
    /**
    * 找查
    * @param pageQuery
    * @return
    */
    @GetMapping
    public Result get(PageQuery pageQuery){
        Result<Page> result = new Result<Page>();
        try {
            Page select = service.select(pageQuery);
            result.setCode(200);
            result.setMessage("查询成功");
            result.setData(select);
        } catch (Exception e) {
            result.setCode(500);
            result.setMessage(e.getMessage());
        }
            return result;
    }

}
</#if>
