package com.sia.tacocloud.data;

import com.sia.tacocloud.pojo.Taco;

public interface TacoRepository {
    Taco save(Taco taco);
}
