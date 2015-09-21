package ucsc.mit13550368.myapplication;



import java.io.Serializable;

/**
 * Created by nilan on 8/21/15.
 */
public class Food implements Serializable {

    private static final long serialVersionUID = -7788619177798333712L;

    private FoodL2 foodL2;
    private String foodName;

    private double foodPrice;

    private String foodDiscription;

    private String foodscanCode;
    private int foodNo;

    private byte[] itemImage;


    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }


    public int getFoodNo() {
        return foodNo;
    }

    public void setFoodNo(int foodNo) {
        this.foodNo = foodNo;
    }

    public String getFoodscanCode() {
        return foodscanCode;
    }

    public void setFoodscanCode(String foodscanCode) {
        this.foodscanCode = foodscanCode;
    }

    public String getFoodDiscription() {
        return foodDiscription;
    }

    public void setFoodDiscription(String foodDiscription) {
        this.foodDiscription = foodDiscription;
    }

    public FoodL2 getFoodL2() {
        return foodL2;
    }

    public void setFoodL2(FoodL2 foodL2) {
        this.foodL2 = foodL2;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }



}
