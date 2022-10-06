package net.codejava;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class SalesDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Sale> list() {
        String sql = "SELECT * FROM SALES";

        List<Sale> listSale = namedParameterJdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Sale.class));

        return listSale;
    }

    public void save(Sale sale) {

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = "insert into sales (item, quantity,amount) values (:item, :quantity, :amount)";
        SqlParameterSource paramSource = new MapSqlParameterSource().addValue("item", sale.getItem()).addValue("quantity", sale.getQuantity()).addValue("amount", sale.getAmount());
        namedParameterJdbcTemplate.update(sql, paramSource, generatedKeyHolder);
        int id = generatedKeyHolder.getKey().intValue();
        System.out.println(id);
        sale.setId(id);
    }

    public Sale get(int id) {
        String sql = "SELECT * FROM SALES WHERE id = :id";
        Map parameters = new HashMap();
        parameters.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, BeanPropertyRowMapper.newInstance(Sale.class));
    }

    public void update(Sale sale) {
        String sql = "UPDATE SALES SET item=:item, quantity=:quantity, amount=:amount WHERE id=:id";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(sale);
        int i = namedParameterJdbcTemplate.update(sql, param);
        System.out.println(i);
    }

    public void delete(int id) {
        String sql = "DELETE FROM SALES WHERE id = :id";

        SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
        int i = namedParameterJdbcTemplate.update(sql, paramSource);
        System.out.println(i);
    }
}
