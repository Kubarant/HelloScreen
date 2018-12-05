# What is it?
It's all you need. It's collecting news, weather, and discount. Unfortunately if you don't know polish launguage the section with discounted products will be useless for you cause it's only collecting products from polish supermarket "Biedronka".
But don't worry you can still use other feautures, you just need to replace few URLs in application properties.
# Technology stack
- Spring webflux as web framework

Maybe I didn't use all asynchronic capabilities that framework provide, but it's given me fresh look on various things
- MongoDB and Spring Data for persistance
- JSoup for parsing HTML and XML
- Vavr for immutable collections
- OkHttp for downloading images
- Vue and Axios for front and consuming REST
# Building
To build and use this application you need to have Java 8, Maven and MongoDB.
If you already have this, it's simple just use `mvn package` in application directory.
# Relaiability
 Applicatiion uses web scraping to collect discounts and news, so if websites from which data is collected change their format(like specific css classes), some parts of application may not be working properly. In that case, you can modify specific parsers in data collectors package or check if there aren't new app version on Github.
# Additional info
  It's been created to be self hosted service. 
