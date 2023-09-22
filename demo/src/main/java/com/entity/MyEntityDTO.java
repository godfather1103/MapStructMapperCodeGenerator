package com.entity;

import io.github.godfather1103.annotations.EntityMapping;
import io.github.godfather1103.annotations.MapperCodeGenerator;
import lombok.Data;
import org.mapstruct.Mapping;

/**
 * <p>Title:        Godfather1103's Github</p>
 * <p>Copyright:    Copyright (c) 2023</p>
 * <p>Company:      https://github.com/godfather1103</p>
 *
 * @author 作者: Jack Chu E-mail: chuchuanbao@gmail.com
 * @version 1.0
 * @date 创建时间：2023/9/22 10:08
 * @since 1.0
 */
@Data
@MapperCodeGenerator
@EntityMapping(
        targetEntityClass = "com.entity.MyEntityVO",
        methodName = "dtoToVo",
        mappings = {
                @Mapping(source = "b", target = "c")
        }
)
public class MyEntityDTO {
    private String a;
    private String b;
}
