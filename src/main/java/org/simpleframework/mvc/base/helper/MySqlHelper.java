package org.simpleframework.mvc.base.helper;

import org.simpleframework.mvc.base.SQLConditionType;
import org.simpleframework.util.ArrayUtil;
import org.simpleframework.util.FieldUtil;
import org.simpleframework.util.StringUtil;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * MySQL 语句生成助手类
 * Created by Why on 2017/3/21.
 */
public class MySqlHelper {
    private Class<?> entityClass;
    private String idFieldName;
    private String tableName;
    private String alias;
    private String prefix;
    private String[] allFieldNames;
    private StringBuilder sql = new StringBuilder();
    private StringBuilder countSql = new StringBuilder();
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final String LP = "(";
    private static final String RP = ")";
    private static final String REGEX = ",";
    private static final String WHERE = "where";
    private static final String AND = "and";
    private static final String PLACEHOLDER = "?";
    private static final String ASC = "asc";
    private static final String DESC = "desc";
    private static final String ORDER_BY = "order by";
    private static final String GROUP_BY = "group by";
    private static final String LIMIT = "limit";
    private static final String INSERT_INTO = "insert into";
    private static final String VALUES = "values";
    private static final String UPDATE = "update";
    private static final String SET = "set";
    private static final String SELECT = "select";
    private static final String DELETE_FROM = "delete from";
    private static final String FROM = "from";
    private static final String UNION = "union";
    private static final String UNION_ALL = "union all";
    private static final String ON = "on";
    private static final String POINT = ".";
    private static final String COUNT = "count";
    private static final String OR = "or";
    private static final String ONE_EQ_ONE = "1=1";

    public MySqlHelper(Class<? extends Serializable> entityClass) {
        this.entityClass = entityClass;
        this.idFieldName = StringUtil.camelToUnderline(EntityHelper.getIdFieldName(entityClass));
        this.tableName = StringUtil.camelToUnderline(EntityHelper.getTableName(entityClass));
        this.alias = EMPTY;
        this.prefix = EMPTY;
        setAllFieldNames();
    }

    public MySqlHelper(Class<? extends Serializable> entityClass, String alias) {
        this.entityClass = entityClass;
        this.idFieldName = StringUtil.camelToUnderline(EntityHelper.getIdFieldName(entityClass));
        this.tableName = StringUtil.camelToUnderline(EntityHelper.getTableName(entityClass));
        this.alias = alias;
        this.prefix = alias + POINT;
        setAllFieldNames();
    }

    private void setAllFieldNames() {
        Field[] fields = FieldUtil.getFields(entityClass);
        if (ArrayUtil.isNotEmpty(fields)) {
            this.allFieldNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                this.allFieldNames[i] = prefix + StringUtil.camelToUnderline(fields[i].getName());
            }
        }
    }

    /**
     * 创建 SQL 语句
     */
    public String createSQL() {
        deleteLastRegex();
        return sql.toString();
    }

    /**
     * 加入占位符
     */
    private void placeholder(int size) {
        for (int i = 0; i < size; i++) {
            sql.append(PLACEHOLDER);
            if (i < size - 1) {
                sql.append(REGEX);
            }
        }
        sql.append(SPACE);
    }

    /**
     * 加入占位符
     */
    private void placeholder(String[] fieldNames) {
        for (int i = 0; i < fieldNames.length; i++) {
            sql.append(StringUtil.camelToUnderline(fieldNames[i])).append(SPACE).append(SQLConditionType.EQ).append(SPACE).append(PLACEHOLDER);
            if (i < fieldNames.length - 1) {
                sql.append(REGEX);
            }
        }
        sql.append(SPACE);
    }

    /**
     * 加入 insert
     */
    public MySqlHelper insert(String... fieldNames) {
        boolean isAllFieldNames = false;
        if (ArrayUtil.isEmpty(fieldNames)) {
            isAllFieldNames = true;
        }
        sql.append(INSERT_INTO).append(SPACE).append(tableName).append(SPACE);
        if (!isAllFieldNames) {
            sql.append(LP);
            for (int i = 0; i < fieldNames.length; i++) {
                sql.append(StringUtil.camelToUnderline(fieldNames[i]));
                if (i < fieldNames.length - 1) {
                    sql.append(REGEX);
                }
            }
            sql.append(RP).append(SPACE);
        }
        sql.append(VALUES).append(SPACE).append(LP);
        placeholder(isAllFieldNames ? allFieldNames.length : fieldNames.length);
        sql.append(RP);
        return this;
    }


    /**
     * 加入 update
     */
    public MySqlHelper update(String... fieldNames) {
        sql.append(UPDATE).append(SPACE).append(tableName).append(SPACE).append(SET).append(SPACE);
        if (ArrayUtil.isEmpty(fieldNames)) {
            placeholder(allFieldNames);
        } else {
            placeholder(fieldNames);
        }
        return this;
    }

    /**
     * 加入 delete
     */
    public MySqlHelper delete() {
        sql.append(DELETE_FROM).append(SPACE).append(tableName).append(SPACE);
        return this;
    }

    /**
     * 加入 select
     */
    public MySqlHelper select(String... fieldNames) {
        sql.append(SELECT).append(SPACE);
        if (ArrayUtil.isEmpty(fieldNames)) {
            sql.append(StringUtil.join(allFieldNames, REGEX));
        } else {
            for (int i = 0; i < fieldNames.length; i++) {
                if (fieldNames[i].contains(COUNT)) {
                    sql.append(fieldNames[i]);
                } else {
                    sql.append(prefix).append(StringUtil.camelToUnderline(fieldNames[i]));
                }
                if (i < fieldNames.length - 1) {
                    sql.append(REGEX);
                }
            }
        }
        sql.append(SPACE).append(FROM).append(SPACE).append(tableName).append(SPACE).append(alias).append(SPACE);
        return this;
    }

    /**
     * 加入 count
     */
    public String count(String fieldName) {
        if (countSql.length() > 0) {
            countSql.append(REGEX).append(COUNT).append(LP).append(prefix).append(StringUtil.camelToUnderline(fieldName)).append(RP);
        } else {
            countSql.append(COUNT).append(LP).append(prefix).append(StringUtil.camelToUnderline(fieldName)).append(RP);
        }
        return countSql.toString();
    }

    /**
     * 加入 where
     */
    public MySqlHelper where(Condition condition) {
        if (condition != null) {
            sql.append(WHERE).append(SPACE).append(condition.getSQL()).append(SPACE);
        }
        return this;
    }

    /**
     * 创建 condition
     */
    public Condition condition(String fieldName, String conditionType) {
        return new Condition(fieldName, conditionType);
    }

    /**
     * 创建 condition
     */
    public Condition condition(String fieldName1, String conditionType, MySqlHelper mySqlHelper, String fieldName2) {
        return new Condition(fieldName1, conditionType, mySqlHelper, fieldName2);
    }

    /**
     * 创建 condition
     */
    public Condition condition(String fieldName, String conditionType, int n) {
        return new Condition(fieldName, conditionType, n);
    }

    /**
     * 创建子查询
     */
    public Condition subQuery(String fieldName, String conditionType, String subQuerySQL) {
        return new Condition(fieldName, conditionType, subQuerySQL);
    }

    /**
     * 加入 and
     */
    public Condition and(Condition condition) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LP).append(condition.getSQL()).append(RP);
        condition.setStringBuilder(stringBuilder);
        return condition;
    }

    /**
     * 加入 limit
     */
    public MySqlHelper limit() {
        sql.append(LIMIT).append(SPACE).append(PLACEHOLDER).append(REGEX).append(PLACEHOLDER);
        return this;
    }

    public class Condition {
        private StringBuilder stringBuilder = new StringBuilder();

        public Condition(String fieldName, String conditionType) {
            stringBuilder.append(prefix).append(StringUtil.camelToUnderline(fieldName)).append(SPACE).append(conditionType).append(SPACE).append(PLACEHOLDER);
        }

        public Condition(String fieldName, String conditionType, String subQuerySQL) {
            stringBuilder.append(prefix).append(StringUtil.camelToUnderline(fieldName)).append(SPACE).append(conditionType).append(SPACE).append(LP).append(subQuerySQL).append(RP);
        }

        public Condition(String fieldName1, String conditionType, MySqlHelper mySqlHelper, String fieldName2) {
            stringBuilder.append(prefix).append(StringUtil.camelToUnderline(fieldName1)).append(SPACE).append(conditionType)
                    .append(SPACE).append(mySqlHelper.getPrefix()).append(StringUtil.camelToUnderline(fieldName2));
        }

        public Condition(String fieldName, String conditionType, int n) {
            stringBuilder.append(prefix).append(StringUtil.camelToUnderline(fieldName)).append(SPACE).append(conditionType).append(SPACE);
            placeholder(n);
        }

        private void placeholder(int n) {
            if (n == 1) {
                stringBuilder.append(PLACEHOLDER);
            } else if (n > 1) {
                stringBuilder.append(LP);
                for (int i = 0; i < n; i++) {
                    stringBuilder.append(PLACEHOLDER);
                    if (i < n - 1) {
                        stringBuilder.append(REGEX);
                    }
                }
                stringBuilder.append(RP);
            }
        }

        public Condition and(Condition condition) {
            stringBuilder.append(SPACE).append(AND).append(SPACE).append(condition.getSQL());
            return this;
        }

        public Condition or(Condition condition) {
            stringBuilder.append(SPACE).append(OR).append(SPACE).append(condition.getSQL());
            return this;
        }

        public void setStringBuilder(StringBuilder stringBuilder) {
            this.stringBuilder = stringBuilder;
        }

        public String getSQL() {
            return stringBuilder.toString();
        }
    }

    /**
     * 加入 union
     */
    public MySqlHelper union(String unionSql) {
        sql.append(UNION).append(SPACE).append(LP).append(unionSql).append(RP);
        return this;
    }

    /**
     * 加入 unionAll
     */
    public MySqlHelper unionAll(String unionSql) {
        sql.append(UNION_ALL).append(SPACE).append(LP).append(unionSql).append(RP);
        return this;
    }

    /**
     * 加入 join
     */
    public MySqlHelper joinWithAllFields(MySqlHelper sqlHelper, String joinType) {
        String joinFiledNames = StringUtil.join(sqlHelper.getAllFieldNames(), REGEX);
        sql.insert(sql.lastIndexOf(FROM) - 1, REGEX + joinFiledNames);
        sql.append(joinType).append(SPACE).append(sqlHelper.getTableName()).append(SPACE).append(sqlHelper.getAlias())
                .append(SPACE);
        return this;
    }

    /**
     * 加入 join
     */
    public MySqlHelper join(MySqlHelper sqlHelper, String joinType, String... fieldNames) {
        if (ArrayUtil.isNotEmpty(fieldNames)) {
            String joinFiledNames = sqlHelper.getFieldNamesWithAlias(fieldNames);
            sql.insert(sql.lastIndexOf(FROM) - 1, REGEX + joinFiledNames);
        }
        sql.append(joinType).append(SPACE).append(sqlHelper.getTableName()).append(SPACE).append(sqlHelper.getAlias())
                .append(SPACE);
        return this;
    }

    /**
     * 加入 order by
     */
    public MySqlHelper orderBy(OrderBy... orderBies) {
        sql.append(ORDER_BY).append(SPACE);
        for (int i = 0; i < orderBies.length; i++) {
            sql.append(orderBies[i].getSQL());
            if (i < orderBies.length - 1) {
                sql.append(REGEX);
            }
        }
        sql.append(SPACE);
        return this;
    }

    public OrderBy asc(String fieldName) {
        OrderBy orderBy = new OrderBy(fieldName);
        orderBy.asc();
        return orderBy;
    }

    public OrderBy desc(String fieldName) {
        OrderBy orderBy = new OrderBy(fieldName);
        orderBy.desc();
        return orderBy;
    }

    public class OrderBy {
        public String fieldName;
        private StringBuilder stringBuilder = new StringBuilder();

        public OrderBy(String fieldName) {
            this.fieldName = StringUtil.camelToUnderline(fieldName);
        }

        public OrderBy asc() {
            stringBuilder.append(prefix).append(fieldName).append(SPACE).append(ASC);
            return this;
        }

        public OrderBy desc() {
            stringBuilder.append(prefix).append(fieldName).append(SPACE).append(DESC);
            return this;
        }

        public String getSQL() {
            return stringBuilder.toString();
        }
    }

    /**
     * 加入 group by
     */
    public MySqlHelper groupBy(String... fieldNames) {
        sql.append(GROUP_BY).append(SPACE);
        for (int i = 0; i < fieldNames.length; i++) {
            sql.append(prefix).append(StringUtil.camelToUnderline(fieldNames[i]));
            if (i < fieldNames.length - 1) {
                sql.append(REGEX);
            }
        }
        sql.append(SPACE);
        return this;
    }


    /**
     * 加入 on
     */
    public MySqlHelper on(Condition condition) {
        sql.append(ON).append(SPACE).append(condition.getSQL()).append(SPACE);
        return this;
    }

    /**
     * 加入所有成员名
     */
    private void allFieldNames() {
        sql.append(StringUtil.join(allFieldNames, REGEX)).append(SPACE);
    }

    /**
     * 加入成员名
     */
    private void fieldNames(String... fieldNames) {
        sql.append(StringUtil.join(fieldNames, REGEX)).append(SPACE);
    }


    /**
     * 删除最后的分隔符
     */
    private void deleteLastRegex() {
        int pos = sql.length() - 1;
        if (sql.lastIndexOf(REGEX) == pos) {
            sql.deleteCharAt(pos);
            sql.append(SPACE);
        }
    }


    /**
     * 加入列名（带别名）
     */
    private String getFieldNamesWithAlias(String... fieldNames) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fieldNames.length; i++) {
            stringBuilder.append(prefix).append(StringUtil.camelToUnderline(fieldNames[i]));
            if (i < fieldNames.length - 1) {
                stringBuilder.append(REGEX);
            }
        }
        return stringBuilder.toString();
    }

    public String getIdFieldName() {
        return idFieldName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getAlias() {
        return alias;
    }

    public String[] getAllFieldNames() {
        return allFieldNames;
    }

    public String getPrefix() {
        return prefix;
    }
}
