package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;

import java.util.List;

public interface CarTypeDAL
{
    List<CarType> findAllByCarTypesThatCanCarry(GoodsType expectedGoods);

    CarType findByAarType(AARDesignation aarDesignation);

    void deleteAll();

    CarType insertCarType(CarType carTypeToSave);

    void delete(CarType carTypeToDelete);

    List<CarType> getAllCarTypes();

    List<AARDesignation> getAllAARDesignations();
}
