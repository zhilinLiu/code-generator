package com.github.generater.ao;

import lombok.Data;

/**
 * @author : zhilin
 * @date : 2020/06/05
 */
@Data
public class CodeAO {
    /**
     *  父类包名 例如  com.github
     */
    private String moduleReference;
    /**
     *  模块名称
     */
    private String moduleName;
    /**
     * 作者
     */
    private String author;
    /**
     * 表名，多表用,分割
     */
    private String tableNames;
}
