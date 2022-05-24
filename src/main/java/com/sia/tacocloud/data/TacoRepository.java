package com.sia.tacocloud.data;

import com.sia.tacocloud.pojo.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco,Long> {
//    Taco save(Taco taco);
}
