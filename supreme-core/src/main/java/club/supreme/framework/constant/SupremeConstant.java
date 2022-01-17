package club.supreme.framework.constant;


import java.util.Locale;

/**
 * 系统常量
 *
 * @author supreme
 */
public interface SupremeConstant {

    interface Message {
        String NULL = "暂无数据";
        String SUCCESS = "操作成功";
    }

    interface Version {

        /**
         * HTTP API 版本 v1
         */
        String HTTP_API_VERSION_V1 = "/api/v1";
    }

    interface Jackson {
        Locale LOCALE = Locale.CHINA;
        String TIME_ZONE = "GMT+8";
        String DATE_FORMAT = "yyyy-MM-dd";
        String TIME_FORMAT = "HH:mm:ss";
        String DATE_TIME_FORMAT = Jackson.DATE_FORMAT + " " + Jackson.TIME_FORMAT;
    }

    interface CRUD {
        Long DEFAULT_TENANT_ID = 0L;
        String DEFAULT_TENANT_NAME = "请设置租户ID";

        /**
         * 该租户ID为超级租户, 可以无视租户SQL拦截器
         */
        Long PRIVILEGED_TENANT_ID = 0L;

        /**
         * 租户ID
         */
        String COLUMN_TENANT_ID = "gmt_tenant";
        String ENTITY_FIELD_TENANT_ID = "gmtTenant";

        /**
         * 创建时刻
         */
        String COLUMN_CREATED_AT = "gmt_create";
        String ENTITY_FIELD_CREATED_AT = "gmtCreate";

        /**
         * 创建者
         */
        String COLUMN_CREATED_BY = "gmt_creator";
        String ENTITY_FIELD_CREATED_BY = "gmtCreator";

        /**
         * 更新时刻
         */
        String COLUMN_UPDATED_AT = "gmt_modified";
        String ENTITY_FIELD_UPDATED_AT = "gmtModified";

        /**
         * 更新者
         */
        String COLUMN_UPDATED_BY = "gmt_modifiedby";
        String ENTITY_FIELD_UPDATED_BY = "gmtModifiedby";

        /**
         * 状态
         */
        String COLUMN_STATUS = "gmt_status";
        String ENTITY_FIELD_STATUS = "gmtStatus";

        /**
         * SQL LIMIT 1
         */
        String SQL_LIMIT_1 = " LIMIT 1";

        /**
         * SQL 列名 id
         */
        String SQL_COLUMN_ID = " id ";

    }

    interface Regex {
        String CHINA_MAINLAND_PHONE_NO = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$";
        String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    }

    interface Permission {
        String CREATE = "create";
        String RETRIEVE = "retrieve";
        String UPDATE = "update";
        String DELETE = "delete";
    }
}
