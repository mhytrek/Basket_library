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
    private Map<String, List<String>> productsDeliveryTypes = new HashMap<>();
    private HashMap<String, Integer> deliveryCounter;
    private Map<String, List<String>> splittedDeliveries;

    public BasketSplitter(String absolutePathToConfigFile){
        // Changing json file to Map<String, List<String>>
        ObjectMapper mapper = new ObjectMapper();
        try {
            productsDeliveryTypes = mapper.readValue(new File(absolutePathToConfigFile), new TypeReference<Map<String, List<String>>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // getters made for testing only
    public Map<String, List<String>> getProductsDeliveryTypes() {
        return productsDeliveryTypes;
    }

    public HashMap<String, Integer> getDeliveryCounter() {
        return deliveryCounter;
    }

    public Map<String, List<String>> getSplittedDeliveries() {
        return splittedDeliveries;
    }

    // Counts the max amount of products from a basket that can be delivered by each type
    public void countDeliveries(List<String> products) throws ProductException {
        deliveryCounter = new HashMap<>();
        for(String product: products) {
            if (!productsDeliveryTypes.containsKey(product)){
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


    // give the max amount of products that can be delivered by each delivery type
    public Integer getMaxDeliveries(String product){
        return deliveryCounter.get(product);
    }


    // get the best delivery for a product
    public String getBestDelivery(String product){
        List<String> deliveryTypes = productsDeliveryTypes.get(product);
        deliveryTypes = productsDeliveryTypes.get(product);
        return Collections.max(deliveryTypes, (delivery1, delivery2) -> {
            int max1 = getMaxDeliveries(delivery1);
            int max2 = getMaxDeliveries(delivery2);
            if (max1 == max2) {
                return delivery1.compareTo(delivery2);
            }
            return Integer.compare(max1, max2);
        });
    }


    // add a product to a given delivery type  list
    public void addToList(String delivery, String product){
        if(splittedDeliveries.containsKey(delivery)){
            splittedDeliveries.get(delivery).add(product);
        } else{
            splittedDeliveries.put(delivery, new ArrayList<String>(List.of(product)));
        }
    }


    // Split the products in basket into delivery groups
    public Map<String, List <String>> split(List<String> items){
        countDeliveries(items);
        splittedDeliveries = new HashMap<>();
        for(String product: items){
            String bestDelivery = getBestDelivery(product);
            addToList(bestDelivery, product);
        }
        return splittedDeliveries;
    }
}
