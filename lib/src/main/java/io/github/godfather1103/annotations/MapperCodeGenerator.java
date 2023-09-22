package io.github.godfather1103.annotations;

import org.mapstruct.Mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Title:        Godfather1103's Github</p>
 * <p>Copyright:    Copyright (c) 2023</p>
 * <p>Company:      https://github.com/godfather1103</p>
 * MyAnnotation
 *
 * @author 作者: Jack Chu E-mail: chuchuanbao@gmail.com
 * @version 1.0
 * @date 创建时间：2023/9/21 18:47
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface MapperCodeGenerator {

    /**
     * 对应的Mapper类的包名，为空时将跟被注解的类同package<BR>
     *
     * @return 结果
     * @author 作者: Jack Chu E-mail: chuchuanbao@gmail.com
     * @date 创建时间：2023/9/22 16:53
     */
    String mapperPackageName() default "";

    /**
     * 对应的Mapper类的类名，为空时为“被注解的类名+EntityConvert”<BR>
     *
     * @return 结果
     * @author 作者: Jack Chu E-mail: chuchuanbao@gmail.com
     * @date 创建时间：2023/9/22 16:53
     */
    String mapperClassName() default "";

    /**
     * {@link Mapper#componentModel()}<BR>
     *
     * @return 结果
     * @author 作者: Jack Chu E-mail: chuchuanbao@gmail.com
     * @date 创建时间：2023/9/22 16:56
     */
    String componentModel() default "default";

    /**
     * 是否继承序列化接口<BR>
     *
     * @return 结果
     * @author 作者: Jack Chu E-mail: chuchuanbao@gmail.com
     * @date 创建时间：2023/9/22 18:04
     */
    boolean isExtendsSerializable() default false;
}
