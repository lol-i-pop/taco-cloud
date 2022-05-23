package com.sia.tacocloud.controller;

import com.sia.tacocloud.data.IngredientRepository;
import com.sia.tacocloud.data.TacoRepository;
import com.sia.tacocloud.pojo.Ingredient;
import com.sia.tacocloud.pojo.Order;
import com.sia.tacocloud.pojo.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import com.sia.tacocloud.pojo.Ingredient.Type;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private  final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;

//    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo,
                                TacoRepository designRepo){
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute("order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute("taco")
    public Taco taco(){
        return new Taco();
    }


    @GetMapping
    public String showDesignFrom(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i->ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for(Type type:types){
            model.addAttribute(type.toString().toLowerCase(Locale.ROOT),
                    filterByType(ingredients,type));
        }
        model.addAttribute("design",new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design,
                                Errors errors,
                                @ModelAttribute Order order){
        if(errors.hasErrors()){
            return "design";
        }
        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }
    private List<Ingredient> filterByType(List<Ingredient> ingredients,Type type){
        return ingredients
                .stream()
                .filter(x->x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
