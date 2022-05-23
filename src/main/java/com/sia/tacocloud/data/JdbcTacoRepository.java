package com.sia.tacocloud.data;

import com.sia.tacocloud.pojo.Ingredient;
import com.sia.tacocloud.pojo.Taco;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository{

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        long id = saveTacoInfo(taco);
        taco.setId(id);
        taco.getIngredients().forEach(i->{
            saveIngredientToTaco(i,id);
        });
        return taco;
    }

    private long saveTacoInfo(Taco taco){
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into taco (NAME,CREATEDAT) values(?,?) ",
                Types.VARCHAR,Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);//不加这句会导致keyHolder.getKey()获取不到id,这个属性默认为false
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(taco.getName(),
                        new Timestamp(taco.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc,keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient,long tacoId){
        jdbc.update(
                "insert into taco_ingredients (taco,ingredient) values(?,?)",
                tacoId,ingredient.getId());
    }
}
