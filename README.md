# Ocado_basket_library
Project for Ocado internship recrutation.

## Library
Creating a BasketSplitter that can split products into delivery groups
Criteria:
- first it needs to load configuration files, which conatin possible delivery types and possible deliveries for each product
- we need to use as little delivery types as we can
- the biggest delivery group should be the biggest it can get

## Usage
You need to have a config.json file where you have all products from your shop and delivery types for each of this products.

```
{
"Carrots (1kg)": ["Express Delivery", "Click&Collect"], "Cold Beer (330ml)": ["Express Delivery"],
"Steak (300g)": ["Express Delivery", "Click&Collect"], "AA Battery (4 Pcs.)": ["Express Delivery", "Courier"], "Espresso Machine": ["Courier", "Click&Collect"], "Garden Chair": ["Courier"]
}
```


Products from a client basket need to be in a List and need to be given as an argument in basketSplitter.split(List products)

```
BasketSplitter basketSplitter = new BasketSplitter(String absoluteConfigFilePath);
Map<String, List<String>> delivery = basketSplitter.split(List<String> productsInBasket);
```

## Code
[source kod](https://github.com/mhytrek/Ocado_basket_library/tree/main/Basket_library) \
[.jar file with library](https://github.com/mhytrek/Ocado_basket_library/releases/tag/v1.0.0)

## Used
Java 11, Gradle


