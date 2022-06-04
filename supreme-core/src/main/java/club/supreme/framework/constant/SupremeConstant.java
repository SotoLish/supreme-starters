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
        /**
         * 租户ID
         */
        String COLUMN_TENANT_ID = "tenant_id";

        String ENTITY_FIELD_TENANT_ID = "tenantId";

        /**
         * 创建时刻
         */
        String COLUMN_CREATED_AT = "created_at";

        String ENTITY_FIELD_CREATED_AT = "createdAt";

        /**
         * 创建者
         */
        String COLUMN_CREATED_BY = "created_by";

        String ENTITY_FIELD_CREATED_BY = "createdBy";

        /**
         * 更新时刻
         */
        String COLUMN_UPDATED_AT = "updated_at";

        String ENTITY_FIELD_UPDATED_AT = "updatedAt";

        /**
         * 更新者
         */
        String COLUMN_UPDATED_BY = "updated_by";

        String ENTITY_FIELD_UPDATED_BY = "updatedBy";

        /**
         * 逻辑删除标识
         */
        String COLUMN_DEL_FLAG = "del_flag";

        /**
         * 乐观锁
         */
        String COLUMN_REVISION = "revision";

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

    /**
     * Tenant
     * @author supreme Lai
     * @date 2022/05/26
     */
    interface Tenant {
        /**
         * 该租户ID为超级租户, 可以无视租户SQL拦截器
         */
        Long DEFAULT_PRIVILEGED_TENANT_ID = 0L;
    }
}
