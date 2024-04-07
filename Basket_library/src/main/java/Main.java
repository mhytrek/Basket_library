import com.ocado.basket.BasketSplitter;

import java.util.List;
import java.util.Map;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BasketSplitter basketSplitter = new BasketSplitter("src/main/configuration/config.json");
        Map<String, List<String>> delivery1 = basketSplitter.split(new ArrayList<>(List.of("Fond - Chocolate", "Chocolate - Unsweetened", "Nut - Almond, Blanched, Whole", "Haggis", "Mushroom - Porcini Frozen", "Cake - Miini Cheesecake Cherry", "Sauce - Mint", "Longan", "Bag Clear 10 Lb", "Nantucket - Pomegranate Pear", "Puree - Strawberry", "Numi - Assorted Teas", "Apples - Spartan", "Garlic - Peeled", "Cabbage - Nappa", "Bagel - Whole White Sesame", "Tea - Apple Green Tea")));
        Map<String, List<String>> delivery2 = basketSplitter.split(new ArrayList<>(List.of( "Tart - Raisin And Pecan", "Table Cloth 54x72 White", "Flower - Daisies", "Fond - Chocolate", "Cookies - Englishbay Wht")));
        System.out.println(delivery1.toString());
        System.out.println(delivery2.toString());
    }
}