package com.ocado.basket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

class ProductException extends RuntimeException{
    public ProductException(String reason) {
        super("Cannot find " + reason);
    }
}
public class BasketSplitter {
    private final Map<String, List<String>> productsDeliveryTypes;
    private HashMap<String, Integer> deliveryCounter;
    private Map<String, List<String>> splittedDeliveries;

    public BasketSplitter(String absolutePathToConfigFile) {
        // Changing json file to Map<String, List<String>>
        ObjectMapper mapper = new ObjectMapper();
        try {
            productsDeliveryTypes = mapper.readValue(new File(absolutePathToConfigFile), new TypeReference<Map<String, List<String>>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // getters made for testing only
    public Map<String, List<String>> getProductsDeliveryTypes() {
        return productsDeliveryTypes;
    }


    // Counts the max amount of remaining products that can be delivered by each type
    public void countDeliveries(List<String> products) throws ProductException {
        deliveryCounter = new HashMap<>();
        for (String product : products) {
            if (!productsDeliveryTypes.containsKey(product)) {
                throw new ProductException("delivery type for " + product);
            }
            for (String possibleDelivery : productsDeliveryTypes.get(product)) {
                if (deliveryCounter.containsKey(possibleDelivery)) {
                    deliveryCounter.replace(possibleDelivery, deliveryCounter.get(possibleDelivery) + 1);
                } else {
                    deliveryCounter.put(possibleDelivery, 1);
                }
            }
        }
    }



    // get the best delivery for remaining products
    public String getBestDelivery(List<String> products) {
        countDeliveries(products);
        return Collections.max(deliveryCounter.keySet(), (delivery1, delivery2) -> {
            int max1 = deliveryCounter.get(delivery1);
            int max2 = deliveryCounter.get(delivery2);
            if (max1 == max2) {
                return delivery1.compareTo(delivery2);
            }
            return Integer.compare(max1, max2);
        });
    }


    // add a new delivery group with products
    public List<String> addToList(String delivery, List<String> products) {
        List<String> productList = new ArrayList<>();
        List<String> productNoDelivery = new ArrayList<>();
        for(String product: products){
            if(productsDeliveryTypes.get(product).contains(delivery)){
                productList.add(product);
            }
            else {
                productNoDelivery.add(product);
            }
        }
        splittedDeliveries.put(delivery, productList);
        return productNoDelivery;
    }


    // Split the products in basket into delivery groups
    public Map<String, List<String>> split(List<String> items) {
        splittedDeliveries = new HashMap<>();
        while (!items.isEmpty()) {
            String bestDelivery = getBestDelivery(items);
            items = addToList(bestDelivery, items);
        }
        return splittedDeliveries;
    }
}
