package com.ruoyi.common.core.mybatis;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * MyBatis Fastjson2 Map 类型转换器
 * 用于将数据库中的 JSON 字符串与 Java 中的 Map<String, Object> 自动转换
 * * @author rupert
 */
public class FastjsonMapTypeHandler extends BaseTypeHandler<Map<String, Object>> {

    private static final Logger log = LoggerFactory.getLogger(FastjsonMapTypeHandler.class);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType) throws SQLException {
        // 将 Map 转换为 JSON 字符串存入数据库
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parse(rs.getString(columnName));
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parse(rs.getString(columnIndex));
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parse(cs.getString(columnIndex));
    }

    private Map<String, Object> parse(String json) {
        if (json == null || json.trim().isEmpty()) {
            return new HashMap<>(); // 返回空对象而非 null，防止业务层报空指针
        }
        try {
            // 使用 Fastjson 2 的 TypeReference 准确还原 Map
            // JSONReader.Feature.SupportSmartMatch 可选：支持下划线驼峰互转等智能匹配
            return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            log.error("FastjsonMapTypeHandler 解析 JSON 失败: {}", json, e);
            return new HashMap<>();
        }
    }
}