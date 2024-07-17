package com.yksj.monitor.controller;

import com.yksj.monitor.entity.Animal;
import com.yksj.monitor.utils.JSONResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@Validated
public class AnimalController {

//    Animal animal = new Animal();

    @PostMapping("testAnimal")
    public  Map<String, String>  validTest(@RequestBody Animal animal) {
        Map<String, String> map = new HashMap<>();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Animal>> violations = validator.validate(animal);

        for (ConstraintViolation<Animal> violation : violations) {
            String field = violation.getMessageTemplate();
            String msg = violation.getMessage();
            map.put(field, msg);
        }
        return map;

    }
}
