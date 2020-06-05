package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import ${package.pageQuery};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
*
* ${table.comment!} 服务类
*
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
    interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
    /**
    * 代码生成器生成，根据实际情况改动
    * 新增方法
    */
    public boolean insert(${entity} entity);

    /**
    *   代码生成器生成，根据实际情况改动
    *   根据ID删除的方法
    */
    public boolean delete(Integer id);

    /**
    *   代码生成器生成，根据实际情况改动
    *  修改
    */
    public boolean update(${entity} entity);

    /**
    *   代码生成器生成，根据实际情况改动
    *   找查
    */
    public Page select(PageQuery pageQuery);
}
</#if>