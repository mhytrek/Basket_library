package com.ocado.basket;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BasketSplitterTest {

    @Test
    public void testBasketSplitter(){
        //when
        BasketSplitter basketSplitter = new BasketSplitter("src/test/configuration/config.json");
        Map<String, List<String>> result = basketSplitter.getProductsDeliveryTypes();

        //then
        Map<String, List<String>> expectedResult = new HashMap<String, List<String>>();
        expectedResult.put("Carrots (1kg)", Arrays.asList( "Click&Collect", "Express Delivery"));
        expectedResult.put("Cold Beer (330ml)", Arrays.asList("Express Delivery"));
        expectedResult.put("Steak (300g)", Arrays.asList("Express Delivery", "Click&Collect"));
        expectedResult.put("AA Battery (4 Pcs.)", Arrays.asList("Express Delivery", "Courier"));
        expectedResult.put("Espresso Machine", Arrays.asList("Courier", "Click&Collect"));
        expectedResult.put("Garden Chair", Arrays.asList("Courier"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testSplit(){
        //Test1
        //given
        BasketSplitter basketSplitter = new BasketSplitter("src/test/configuration/config.json");
        List<String> items = Arrays.asList("Steak (300g)", "Carrots (1kg)", "Cold Beer (330ml)", "AA Battery (4 Pcs.)", "Espresso Machine", "Garden Chair");

        //when
        Map<String, List<String>> result = basketSplitter.split(items);

        //then
        List<String> expressDeliveryList = new ArrayList<>(List.of("Steak (300g)","Carrots (1kg)","Cold Beer (330ml)","AA Battery (4 Pcs.)"));
        List<String> courierList = new ArrayList<>(List.of("Espresso Machine", "Garden Chair"));

        assertEquals(expressDeliveryList,result.get("Express Delivery"));
        assertEquals(courierList,result.get("Courier"));
        assertFalse(result.containsKey("Click&Collect"));


        //Test2
        //given
        List<String> items2 = new ArrayList<>(List.of("Potatoes (1kg)", "Garden Chair"));


        //then
        assertThrows(ProductException.class, () -> basketSplitter.split(items2));

        //Test3
        //given
        List<String> items3 = new ArrayList<>(List.of("Steak (300g)", "Carrots (1kg)"));

        //when
        Map<String, List<String>> result3 = basketSplitter.split(items3);

        //then
        assertEquals(1, result3.keySet().size());

    }

}
