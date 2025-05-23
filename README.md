# JokesSampleApi
This app displays good jokes, fetched from a sample API — https://api.sampleapis.com/jokes/goodJokes.

# Features
Joke overview screen.
- Display cards of jokes categories (e.g. programming, general, etc.) in a list. 
- Display card of a random joke.
- Tapping on a category card opens Joke category screen.
- Re-try button in case of error.

Joke category screen.
- Display each all jokes in that category in a list.
- Each item show image, joke text and punchline.

# Architecture
- The data repository has a local data source and a remote data source, with a check if data is stale.
- MVVM architecture is used to separate UI and business logic.
- Unidirectional data flow is used to manage user events, UI state and side effects in a predictable way.

# Tech Stack
Kotlin: Programming language.
Jetpack Compose: UI framework.
Hilt: Dependency injection.
Retrofit: Networking library.
Timber: Logging.