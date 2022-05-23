package com.sia.tacocloud.data;

import com.sia.tacocloud.pojo.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findById(String id);
    Ingredient save(Ingredient ingredient);
}
