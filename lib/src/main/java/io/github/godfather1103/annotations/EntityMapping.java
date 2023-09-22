package io.github.godfather1103.annotations;

import org.mapstruct.Mapping;

import java.lang.annotation.*;

/**
 * <p>Title:        Godfather1103's Github</p>
 * <p>Copyright:    Copyright (c) 2023</p>
 * <p>Company:      https://github.com/godfather1103</p>
 *
 * @author 作者: Jack Chu E-mail: chuchuanbao@gmail.com
 * @version 1.0
 * @date 创建时间：2023/9/22 09:41
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Repeatable(EntityMappings.class)
public @interface EntityMapping {
    String targetEntityClass();

    String methodName();

    Mapping[] mappings() default {};
}
