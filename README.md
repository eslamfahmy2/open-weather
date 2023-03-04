# Overview

Weather is an Android application that assists users to get query weather data by city name

## Technologies

The project uses modern Android tools and frameworks such as Kotlin, Compose, Coroutines, Live Data,
Hilt , clean code, compose navigation and others. The app employs automatic state and error handling
and wrappers to handle backend API calls.

## Testing

Illustration integration with Espresso Testing

# Project Structure

The project uses a MVVM architecture. The app is primarily backend driven and can only function when
the user has an internet connection. There is no local database, as the needed data is retrieved
fresh from the backend. Business logic and use cases are placed in a separate Repository layer.
View Models are used to communicate with the UI layer, which is primarily written with the Compose
framework.

## File Structure

The project uses a Clean architecture. All UI related code can be found in the ".presentation"
package and is subdivided into packages according to view
".domain" package contains systems models , repositories, mappers..
".data" pancake contains implementation of data source "Retorfit" and date transfer objects

## Visuals
