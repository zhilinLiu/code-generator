package ${package.basePath}.dto;

import lombok.Data;

/**
*  返回结果
* @author ${author}
* @since ${date}
*/

@Data
public class Result<T> {
    /**
    * 返回码
    **/
    private int code;
    /**
    * 返回信息
    **/
    private String message;
    /**
    * 返回数据
    **/
    private T data;
}