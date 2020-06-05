package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import ${package.pageQuery};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import java.util.List;

/**
*
* ${table.comment!} 服务实现类
*
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
    open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

    }
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Override
    public boolean insert(${entity} entity) {
        return this.save(entity);
    }

    @Override
    public boolean delete(Integer id){
        return this.removeById(id);
    }

    @Override
    public boolean update(${entity} entity) {
        UpdateWrapper wrapper = new UpdateWrapper<${entity}>();
//        wrapper.eq("id_field",entity.getUserId());
        return this.update(entity,wrapper);
    }

    @Override
    public Page select(PageQuery pageQuery){
        //分页
        Page<KdSysUser> page = new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize());
        QueryWrapper<KdSysUser> wrapper = new QueryWrapper<KdSysUser>();

        //如果有模糊查询字段
        if(!"".equals(pageQuery.getKeyword())){
            wrapper.like("likeField",pageQuery.getKeyword());
        }

        //判断是否排序,并且是倒序还是顺序
        if ((pageQuery.getSort() != null && pageQuery.getField() != null) && (!"".equals(pageQuery.getSort()) && !"".equals(pageQuery.getField()))) {
            if ("desc".equalsIgnoreCase(pageQuery.getSort())) {
                wrapper.orderByDesc(pageQuery.getField());
            }
            if ("asc".equalsIgnoreCase(pageQuery.getSort())) {
                wrapper.orderByAsc(pageQuery.getField());
            }
        }

        return this.page(page,wrapper);
    }

}
</#if>
