package com.ocado.basket;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BasketSplitterTest {

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
    }

}
