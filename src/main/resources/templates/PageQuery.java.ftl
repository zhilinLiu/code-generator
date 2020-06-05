package ${package.basePath}.ao;

import lombok.Data;


/**
* 分页查询的请求参数封装
* @author ${author}
* @since ${date}
*/
@Data
public class PageQuery {

    /**
    * 每页的条数
    */
    private Integer pageSize = 20;

    /**
    * 页编码(第几页)
    */
    private Integer pageNo = 1;

    /**
    * 搜索关键字
    */
    private String keyword;

    /**
    * 排序方式(asc 或者 desc)
    */
    private String sort;

    /**
    * 排序的字段名称
    */
    private String field;
}