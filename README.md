# Ocado_basket_library
Project for Ocado internship recrutation.

## Library
Creating a BasketSplitter that can split products into delivery groups
Criterias:
- first it needs to load configuration files, which conatins possible delivery types and possible deliveries for each product
- it need to use as little delivery types as we can
- the biggest delivery group should be the biggest it can get

## Usage
You neeed to have config.json file where you have all products from your your shop and delivery types for each of this products. Products from a client basket needs to be in a List and need to be give as an argument in basketSplitter.split(List products)

```
  BasketSplitter basketSplitter = new BasketSplitter(String absoluteConfigFilePath);
  Map<String, List<String>> delivery = basketSplitter.split(List<String> productsInBasket);
```

## Code
[source kod](https://github.com/mhytrek/Ocado_basket_library/tree/main/Basket_library) \
[.jar file with library](https://github.com/mhytrek/Ocado_basket_library/releases/tag/v1.0.0)

## Used
Java 11, Gradle


