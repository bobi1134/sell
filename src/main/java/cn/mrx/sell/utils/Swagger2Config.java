package cn.mrx.sell.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Author：Mr.X
 * Date：2017/9/8 14:39
 * Description：
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {

    /**
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                   .apiInfo(apiInfo())
                   .select()
                   .apis(RequestHandlerSelectors.basePackage("cn.mrx.sell.controller"))
                   .paths(PathSelectors.any())
                   .build();
    }

    /**
     * 构建 api文档的详细信息函数
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                   .title("Sell 微信点餐系统API文档")  //页面标题
                   .contact(new Contact("Mr.X", "http://www.mrx.com", "jxjy.ing@foxmail.com"))
                   .version("1.0")
                   .description("API 描述")
                   .build();
    }
}
