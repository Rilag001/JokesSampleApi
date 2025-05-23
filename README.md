# JokesSampleApi
This app displays good jokes, fetched from a sample API — https://api.sampleapis.com/jokes/goodJokes.

# Features
Jokes overview screen.
- Display cards of jokes categories (e.g. programming, general, etc.) in a list. 
- Display card of a random joke.
- Tapping on a category card opens Joke category screen.
- Re-try button in case of error.

Jokes category screen.
- Display card of each jokes in that category in a list.
- Each item show image and joke with setup and punchline.

# Architecture
- The data repository has a local data source (Room) and a remote data source (REST API).
- First checks if we have jokes in local, and data is not stale, before fetching data from remote.
- MVVM architecture is used to separate UI and business logic.
- Unidirectional data flow is used to manage user actions, UI state and side effects in a predictable way.

# Tech Stack
Written in Kotlin and Jetpack Compose.
Hilt for dependency injection.
Okhttp and Retrofit for networking with REST API.
Room db for persistent local storage. 
