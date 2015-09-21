package ucsc.mit13550368.myapplication;

import java.io.Serializable;

/**
 * Created by nilan on 8/23/15.
 */
public class FoodL2 implements Serializable {
    private static final long serialVersionUID = -7788619177798333712L;
    private int  foodL2id;
    private FoodL1 foodL1;
    private String FoodL2Name;

    public String getFoodL2Name() {
        return FoodL2Name;
    }

    public void setFoodL2Name(String foodL2Name) {
        FoodL2Name = foodL2Name;
    }

    public FoodL1 getFoodL1() {
        return foodL1;
    }

    public void setFoodL1(FoodL1 foodL1) {
        this.foodL1 = foodL1;
    }

    public int getFoodL2id() {
        return foodL2id;
    }

    public void setFoodL2id(int foodL2id) {
        foodL2id = foodL2id;
    }


}
