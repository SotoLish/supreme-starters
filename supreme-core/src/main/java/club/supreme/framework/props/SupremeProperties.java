package club.supreme.framework.props;

import club.supreme.framework.enums.crud.IdGeneratorStrategyEnumI;
import club.supreme.framework.enums.crud.TenantIsolateLevelEnumI;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.google.common.base.Predicate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static club.supreme.framework.props.SupremeProperties.PREFIX;

/**
 * 配置文件读取类
 *
 * @author Uncarbon
 */
@ConfigurationProperties(prefix = PREFIX, ignoreInvalidFields = true)
@Data
public class SupremeProperties {
    public static final String PREFIX = "supreme";

    private final Security security = new Security();
    private final Crud crud = new Crud();
    private final Knife4j knife4j = new Knife4j();

    @NestedConfigurationProperty
    private final SupremeCacheProperties cache = new SupremeCacheProperties();


    @Data
    public static class Security {

        /**
         * 用户密码加密盐值
         */
        private String salt = "supreme";

        /**
         * 用户默认密码
         */
        private String defPwd = "supreme";

        /**
         * 放行路由地址(不进行登录校验)
         */
        private final List<String> excludeRoutes = new ArrayList<>(64);

    }

    @Data
    public static class Crud {

        /**
         * 多租户插件
         */
        private final Tenant tenant = new Tenant();

        /**
         * 乐观锁插件
         */
        private final OptimisticLock optimisticLock = new OptimisticLock();

        /**
         * ID生成器
         */
        private final IdGenerator idGenerator = new IdGenerator();

        /**
         * 用的哪种数据库 如: mysql  postgresql
         * 参考com.baomidou.mybatisplus.annotation.DbType
         * 默认为mysql
         */
        private String dbType = DbType.MYSQL.getDb();


        @Data
        public static class Tenant {

            /**
             * 是否启用多租户
             * 默认为false
             */
            private Boolean enabled = false;

            /**
             * 多租户隔离级别
             * 默认为行级
             */
            private TenantIsolateLevelEnumI isolateLevel = TenantIsolateLevelEnumI.LINE;

            /**
             * 哪些表不启用租户隔离
             */
            private List<String> ignoredTables = new ArrayList<>(64);

        }

        @Data
        public static class OptimisticLock {

            /**
             * 是否启用乐观锁
             * 默认为false
             */
            private Boolean enabled = false;

        }

        @Data
        public static class IdGenerator {

            /**
             * 雪花ID-起始时刻
             * 默认为2021-01-01
             */
            String epochDate = "2021-01-01";
            /**
             * ID生成器策略
             * 默认为SNOWFLAKE
             */
            private IdGeneratorStrategyEnumI strategy = IdGeneratorStrategyEnumI.SNOWFLAKE;
            /**
             * 雪花ID-数据中心ID
             * 默认为0
             */
            private Long datacenterId = 0L;

        }
    }

    @Data
    public static class Knife4j {

        /**
         * 标题
         */
        private String title = "";

        /**
         * 简介
         */
        private String description = "";

        /**
         * 版本号
         */
        private String version = "";

        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "club.supreme.module";
    }
}
