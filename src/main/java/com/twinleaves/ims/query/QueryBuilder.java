package com.twinleaves.ims.query;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Data
public class QueryBuilder {

    @Setter(AccessLevel.NONE)
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Setter(AccessLevel.NONE)
    private String query;

    @Setter(AccessLevel.NONE)
    private MapSqlParameterSource sqlParams = new MapSqlParameterSource();

    public QueryBuilder(final String query, final NamedParameterJdbcTemplate namedParamJdbcTemplate) {
        this.query = query;
        this.namedParamJdbcTemplate = namedParamJdbcTemplate;
    }

    /**
     * Used to add dynamic conditions in the query along with named params.
     *
     * @param criteria String
     * @param parameter String
     * @param value Object
     * @return QueryBuilder
     */
    public QueryBuilder addParameterWithEnabledCriteria(final String criteria, final String parameter, final Object value) {
        sqlParams.addValue(parameter, value);
        this.query = getCriteriaEnabledQuery(criteria);
        return this;
    }

    /**
     * Used to add named params to query.
     *
     * @param parameter String
     * @param value Object
     * @return QueryBuilder
     */
    public QueryBuilder addParameter(final String parameter, final Object value) {
        sqlParams.addValue(parameter, value);
        return this;
    }

    /**
     * Used to add named params to query
     *
     * @param parameter String
     * @return QueryBuilder
     */
    public QueryBuilder addFilterCriteria(final String parameter) {
        this.query = getCriteriaEnabledQuery(parameter);
        return this;
    }

    /**
     * Returns query with enabled criteria.
     * @param criteria string
     * @return String
     */
    private String getCriteriaEnabledQuery(final String criteria) {
        return query.replace("--<" + criteria + ">", "");
    }
}
