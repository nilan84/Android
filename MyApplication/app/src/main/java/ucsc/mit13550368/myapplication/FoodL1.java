package ucsc.mit13550368.myapplication;

import java.io.Serializable;

/**
 * Created by nilan on 8/23/15.
 */
public class FoodL1 implements Serializable {
    private static final long serialVersionUID = -7788619177798333712L;

    private int  foodL1id;
    private String foodL1Name;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFoodL1id() {
        return foodL1id;
    }

    public void setFoodL1id(int foodL1id) {
        foodL1id = foodL1id;
    }

    public String getFoodL1Name() {
        return foodL1Name;
    }

    public void setFoodL1Name(String foodL1Name) {
        foodL1Name = foodL1Name;
    }



}
