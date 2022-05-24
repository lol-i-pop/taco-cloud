package com.sia.tacocloud.data;

import com.sia.tacocloud.pojo.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient,String> {

//    Iterable<Ingredient> findAll();
//    select ingredient0_.id as id1_0_, ingredient0_.name as name2_0_, ingredient0_.type as type3_0_ from ingredient ingredient0_

//    Ingredient findById(String id);
//    Ingredient save(Ingredient ingredient);
}
