# Phenix Challenge
Phenix challenge is a technical solution developed for Carefour. The aim of this project is to perform sales performance analysis on carefour products and stores based on 2 KPIs:
- Top 100 sold products in all Carefour stores.
- Top 100 turnover products per store.  

This two sales KPIs are available on daily basis and for a range of 7 days starting from a given day.

As an input, 2 Data files type are available: Transactions and Product Data, their structure is detailed in the next section.
## Data Structure
-----------
Transaction file are a daily file, a single one per day that contains all stores sales. However Product data is store based file following this naming rules : 
 - Transactions : `transactions_YYYYMMDD.data`
 - Products referential : `reference_prod-ID_MAGASIN_YYYYMMDD.data`, ID_MAGASIN is the UUID store ID.
 
Daily Transactions file follow this format: `txId|datetime|magasin|produit|qte`

Whereas Daily Product file has this format: `produit|prix`.

 - txId : Transaction ID (integer)
 - datetime : date and heure with format ISO 8601
 - magasin : UUID store ID
 - produit : produit ID (integer)
 - qte : quantity (integer)
 - prix : product price in euros
 
## Conception
-----
Factory Pattern, used in the data generation, is implemented in model package. All tools called in Main class are implemented in the service package as follow :
  
### com.phenix
It contains the Main class that perform the following actions:
 - Loads project configuration properties, using `loadProperties()`.
 - Generates random sets of data using the `DataGenerator` class.
 - Generates the analysis files in the output path using `TransactionAnalyser` class.

### com.phenix.model
This package facilitates the use of the tool and mostly the code upgrade in case of development of new business requirements. It contains 4 classes for Data modeling and processing with the factory Pattern.
 - **Data** : `Interface`
 - **DataFactory** : `Class` used for generation Data objects
 - **Product** : `CLass implements Data` defining the Product data.
 - **Transaction** : `CLass implements Data` defining the Transaction data.

### com.phenix.service
 - **DataGenerator** : `Class` defines the methode `generate()` that generates random data using the model package.
 - **DataManager** : `Class` used for loading & storing Data mainly with the methods `loadProductFile()`, `loadTransactionFiles()` `saveTopProdcut()`, and `saveTopProdcutTurnOver()`. 
 - **TransactionAnalyser** : `Class` Makes some analysis starting from the loaded Transaction & Product data, implementing two methods `searchTopProductSold()` and `searchTopProductTurnOver()`.
 
## How it works
All configs can be modified in `src/main/resources/application.config` (these part needs improvement, configs shouldn't be hard coded but radher stored in this config file).

 - day.of.extract : date to be used as a baseline for the analysis
 - max.days : number of days in the generated data. 
 - max.stores : number of stored generated in the data.
 - max.values : number of transaction generated per store.
 - output.file.path : result output folder path.
 - data.generation.folder : generated data folder path.

## Unit Test
Some unit test for services are implemented
 - testLoadTransactionFiles()
 - testLoadProductFiles()
 - testSearchTopProductSold()
 - testSearchTopProductTurnOver()

 