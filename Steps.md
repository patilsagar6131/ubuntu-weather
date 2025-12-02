Steps followed : Create a maven project : "mvn archetype:generate -DgroupId=com.sagar.weather   -DartifactId=ubuntu-weather -DarchetypeArtifactId=maven-archetype-quickstart DarchetypeVersion=1.5 -DinteractiveMode=false"
Initialize github in this folder : git init  , git add . , git commit -m"Initial commit"
Then create a github repo name = "ubuntu-weather"
Then run these commands: git remote add origin https://github.com/patilsagar6131/ubuntu-weather.git , git branch -M main, git push -u origin main
Implemented WeatherService and created fetchByCity() to make http calls and fetch raw weather, then parseWeather(String json) and then converting it to WeatherResponse object from raw json, fetchWeather a method to make http calls and convert raw response to json object.
Created WeatherResponse class and getters and setters.
Created constants for url. 
Then created WeatherViewModel class to add km/h ℃ hPa for windspeed temeprature and pressure. 
Then created WeatherAppUI javafx class to run and display all the fetched weather entities.This is just skeleton for now, but will develop it more 