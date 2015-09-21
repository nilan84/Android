package ucsc.mit13550368.myapplication;

/**
 * Created by nilan on 8/30/15.
 */
public class FoodItem {
    private String barCode;
    private String discription;
    private double price;
    private String foodItemName;
    private int foodNo;

    public int getFoodNo() {
        return foodNo;
    }

    public void setFoodNo(int foodNo) {
        this.foodNo = foodNo;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
