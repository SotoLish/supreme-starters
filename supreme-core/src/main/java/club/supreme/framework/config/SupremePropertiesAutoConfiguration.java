package club.supreme.framework.config;

import club.supreme.framework.props.SupremeProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置文件读取
 *
 * @author Supreme
 */
@EnableConfigurationProperties(value = {SupremeProperties.class})
@Configuration
public class SupremePropertiesAutoConfiguration {
}
