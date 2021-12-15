Hi 👋🏼👋🏼👋🏼
Thanks for checking out my project.

# CrptoCurrency Challenge
- When the user open the app, we need to display a list of crypto currency assets (see CoincapService and AssetsApiData),
  displaying the asset name and symbol.

- When the user tap on an asset, we navigate to a new Fragment where we need to display detailed view of one Market (see CoincapService and MarketsApiData)
  that has the selected crypto currency as the "baseId", and has the highest volume transacted in the last 24 hours ("volumeUsd24Hr").
  The information to display in this view is:
  + Exchange ID
  + Rank
  + Price
  + Updated date with the format "Day/Month/Year"
  + If no data is available show N/A

- Write test to test use cases and repositories and anything that's important

## Table of content

- [Prerequisite](#prerequisite)
- [Architecture](#architecture)
- [Testing](#testing)
- [Features](#features)

## Prerequisite
To build this project, you require:
- Android Studio Artic Fox or higher

<h2 align="left">Screenshots</h2>
<h4 align="center">
<img src="https://res.cloudinary.com/androiddevadv/image/upload/v1639561368/Screenshot_2021-12-15_at_10.42.41_dvijzl.png" width="30%" vspace="10" hspace="10">
<img src="https://res.cloudinary.com/androiddevadv/image/upload/v1639561444/Screenshot_2021-12-15_at_10.43.59_akco1v.png" width="30%" vspace="10" hspace="10">
<img src="https://res.cloudinary.com/androiddevadv/image/upload/v1639561478/Screenshot_2021-12-15_at_10.44.34_zkjwws.png" width="30%" vspace="10" hspace="10"><br>

## Architecture

The application follows clean architecture because of the benefits it brings to software which includes scalability, maintainability and testability.
It enforces separation of concerns and dependency inversion, where higher and lower level layers all depend on abstractions. In the project, the layers are separated into different packages namely:

- Domain
- Data
- Remote
- Cache

For dependency injection and asynchronous programming, the project uses Dagger Hilt and Coroutines with Flow. Dagger Hilt is a fine abstraction over the vanilla dagger boilerplate, and is easy to setup. Coroutines and Flow brings kotlin's expressibility and conciseness to asynchronous programming, along with a fine suite of operators that make it a robust solution.

#### Presentation
The presentation layer is implemented with MVVM architecture. The project has a package called `presentation` which defines the bridge between the ui and the data layers. The layer is also platform agnostic, making it easy to change implementation details (ViewModel, etc).

<img src="https://res.cloudinary.com/androiddevadv/image/upload/v1639561549/download_1_hnyphe.png" width="20%" vspace="8" hspace="8"><br>


#### Domain
The domain layer contains the app business logic. It defines contracts for data operations and domain models to be used in the app.
Usecases which represent a single unit of business logic are also defined in the domain layer, and are consumed by the presentation layer.

#### Data
The Data layer implements the contract for providing data defined in the domain layer, and it in turn provides a contract that will be used to fetch data from the remote and cache data sources.
We have two data sources - `Remote` and `Cache`. Remote relies on Retrofit library to fetch data from the  REST api, while the cache layer uses Room library to persist history.

## Testing
Testing is done with MockK testing framework, and with Google Truth for making assertions. The test uses fake objects for all tests instead of mocks, making it easier to verify interactions between objects and their dependencies, and simulate the behavior of the real objects.
The use cases in domain are also tested to be sure that they are called with the required parameters or throw a NoParams exception.

The presentation layer is extensively unit-tested to ensure that the correct response is produced.

The case study can do with more UI tests that verify that the view state is rendered as expected. However, the extensive unit and integration test coverage ensures that the app works as expected.

## Features
* Clean Architecture with MVVM
* Kotlin Coroutines with Flow
* Dagger Hilt
* Navigation Components


## Reference links
- Coincap API: https://docs.coincap.io/

