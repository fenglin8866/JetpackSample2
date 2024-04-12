===============Lifecycle相关 start================
Android Architecture Components Basic Sample
=============================================

This sample showcases the following Architecture Components:

* [Room](https://developer.android.com/topic/libraries/architecture/room.html)
* [ViewModels](https://developer.android.com/reference/android/arch/lifecycle/ViewModel.html)
* [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html)

Introduction
-------------

### Features

This sample contains two screens: a list of products and a detail view, that shows product reviews.

#### Presentation layer

The presentation layer consists of the following components:
* A main activity that handles navigation.
* A fragment to display the list of products.
* A fragment to display a product review.

The app uses a Model-View-ViewModel (MVVM) architecture for the presentation layer. Each of the fragments corresponds to a MVVM View. The View and ViewModel communicate  using LiveData and the following design principles:

* ViewModel objects don't have references to activities, fragments, or Android views. That would cause leaks on configuration changes, such as a screen rotation, because the system retains a ViewModel across the entire lifecycle of the corresponding view.



![ViewModel Diagram](docs/images/VM_diagram.png?raw=true "ViewModel Diagram")


* ViewModel objects expose data using `LiveData` objects. `LiveData` allows you to observe changes to data across multiple components of your app without creating explicit and rigid dependency paths between them.

* Views, including the fragments used in this sample, subscribe to corresponding `LiveData` objects. Because `LiveData` is lifecycle-aware, it doesn’t push changes to the underlying data if the observer is not in an active state, and this helps to avoid many common bugs. This is an example of a subscription:

```java
        // Update the list of products when the underlying data changes.
        /*viewModel.getProducts().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> myProducts) {
                if (myProducts != null) {
                    mBinding.setIsLoading(false);
                    mProductAdapter.setProductList(myProducts);
                } else {
                    mBinding.setIsLoading(true);
                }
            }
        });*/
```

#### Data layer

The database is created using Room and it has two entities: a `ProductEntity` and a `CommentEntity` that generate corresponding SQLite tables at runtime.

Room populates the database asynchronously when it's created, via the `RoomDatabase#Callback`. To simulate low-performance, an artificial delay is added. To let
other components know when the data has finished populating, the `AppDatabase` exposes a
`LiveData` object.

To access the data and execute queries, you use a [Data Access Object](https://developer.android.com/topic/libraries/architecture/room.html#daos) (DAO). For example, a product is loaded with the following query:

```java
    @Query("select * from products where id = :productId")
    LiveData<ProductEntity> loadProduct(int productId);
```

Queries that return a `LiveData` object can be observed, so when  a change in one of the affected tables is detected, `LiveData` delivers a notification of that change to the registered observers.

The `DataRepository` exposes the data to the UI layer. To ensure that the UI uses the list of products only after the database has been pre-populated, a [`MediatorLiveData`](https://developer.android.com/reference/android/arch/lifecycle/MediatorLiveData.html) object is used. This
observes the changes of the list of products and only forwards it when the database is ready to be used.





===============Lifecycle相关 end================

===============Hilt相关 start================

logs===========

# Using Hilt in your Android app

This folder contains the source code for the "Using Hilt in your Android app" codelab.

The codelab is built in multiple GitHub branches:
* `main` is the codelab's starting point.
* `solution` contains the solution to this codelab.


# Introduction
Dependency injection is a technique widely used in programming and well suited
to Android development. By following the principles of dependency injection, you
lay the groundwork for a good app architecture.

Implementing dependency injection provides you with the following advantages:
* Reusability of code.
* Ease of refactoring.
* Ease of testing.


# Pre-requisites
* Experience with Kotlin syntax.
* You understand Dependency Injection.

# Getting Started
1. Install Android Studio, if you don't already have it.
2. Download the sample.
3. Import the sample into Android Studio.
4. Build and run the sample.

# Comparison between different branches
* [Full codelab comparison](https://github.com/googlecodelabs/android-hilt/compare/main...solution)

# Migrating from Dagger to Hilt in your Android app

This folder contains the source code for the "Migrating from Dagger to Hilt in your Android app" codelab.

The codelab is built in multiple GitHub branches:
* `master` is the codelab's starting point.
* `interop` is an intermediate step in which Dagger and Hilt coexist.
* `solution` contains the solution to this codelab.


login=====================


# Introduction
Hilt is built on top of the popular DI library Dagger to benefit from the compile time correctness, runtime performance, scalability, and Android Studio support that Dagger provides.

Since many Android framework classes are instantiated by the OS itself, there's an associated boilerplate when using Dagger in Android apps. Unlike Dagger, Hilt is integrated with Jetpack libraries and Android framework classes and removes most of that boilerplate to let you focus on just the important parts of defining and injecting bindings without worrying about managing all of the Dagger setup and wiring. It automatically generates and provides:

* Components for integrating Android framework classes with Dagger that you would otherwise need to create by hand.
* Scope annotations for the components that Hilt generates automatically.
* Predefined bindings and qualifiers.

As Dagger and Hilt can coexist together, apps can be migrated on an as-needed basis.


# Pre-requisites
* Experience with Kotlin syntax.
* Experience with Dagger.

# Getting Started
1. Install Android Studio, if you don't already have it.
2. Download the sample.
3. Import the sample into Android Studio.
4. Build and run the sample.

===============Hilt相关 end==================


===============Room相关 start================
# Bus Scheduler App
=======================
This folder contains the source code for the Bus Scheduler app codelab.

# Introduction
The Bus Scheduler app displays a list of bus stops and arrival times. Tapping a bus stop on the first screen will display a list of all arrival times for that particular stop.

The bus stops are stored in a Room database. Schedule items are represented by the `Schedule` class and queries on the data table are made by the `ScheduleDao` class. The app includes a view model to access the `ScheduleDao` and format data to be display in a list, using `Flow` to send data to a recycler view adapter.

# Pre-requisites
* Experience with Kotlin syntax.
* Familiarity with activities, fragments, and recycler views.
* Basic knowledge of SQL databases and performing basic queries.

# Getting Started
1. Install Android Studio, if you don't already have it.
2. Download the sample.
3. Import the sample into Android Studio.
4. Build and run the sample.


Inventory - Solution Code
==================================
Intermediate version of the solution code for Android Basics in Kotlin.
Codelab: Android Jetpack - Room.

Introduction
------------

This app is an Inventory tracking app. Demos how to add, update, sell, and delete items from the local database.
This app demonstrated
the use of Android Jetpack component [Room](https://developer.android.com/training/data-storage/room) database.

The app also leverages [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel),
[LiveData](https://developer.android.com/topic/libraries/architecture/livedata),
[Flow](https://developer.android.com/kotlin/flow),
[View Binding](https://developer.android.com/topic/libraries/view-binding),
and [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)
with the SafeArgs plugin for parameter passing between fragments.

Pre-requisites
--------------

You need to know:
- How to create and use fragments.
- How to navigate between fragments, and use safeArgs to pass data between fragments.
- How to use architecture components including ViewModel, LiveData, and LiveData transformations.
- How to use coroutines for long-running tasks.
- RecyclerView and adapters
- SQLite database and the SQLite query language


Getting Started
---------------

1. Download and run the app.


RoomWordsSample
=======================================
This repository contains the finished sample code for the
[Architecture Components codelab](https://codelabs.developers.google.com/codelabs/android-room-with-a-view/index.html?index=..%2F..%2Findex#0) in Java on the master branch and in Kotlin for the [Architecture Components Kotlin codelab](https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin) on the [kotlin](https://github.com/googlecodelabs/android-room-with-a-view/tree/kotlin) branch.

Introduction
------------

In May 2017 Google released the Architecture Components libraries.
Each library manages and simplifies aspects of data persistence and the
UI component lifecycle. Together, the libraries encourage a modular
app architecture that results in reduced complexity and less code.

This sample shows how to use the libraries to build
a complete basic app that implements the recommended architecture
and can be used as a template for further explorations.


Pre-requisites
--------------

* Android Studio 3.0 or later and you know how to use it.

* Make sure Android Studio is updated, as well as your SDK and Gradle.
  Otherwise, you may have to wait for a while until all the updates are done.

* A device or emulator that runs SDK level 20

You need to be solidly familiar with the Java programming language,
object-oriented design concepts, and Android Development Fundamentals.
In particular:

* RecyclerView and Adapters
* SQLite database and the SQLite query language
* Threading and ExecutorService
* It helps to be familiar with software architectural patterns that separate
  data from the user interface, such as MVP or MVC. This codelab implements the
  architecture defined in the
  [Guide to App Architecture](
  https://developer.android.com/topic/libraries/architecture/guide.html)

Getting Started
---------------

1. [Install Android Studio](https://developer.android.com/studio/install.html),
   if you don't already have it.
2. Download the sample.
2. Import the sample into Android Studio.
3. Build and run the sample.

==========================Room相关 end===============================

# Words App

This folder contains the source code for the Words app codelab.


# Introduction
Words app allows you to select a letter and use Intents to navigate to an Activity that
presents a number of words starting with that letter. Each word can be looked up via a web search.

Words app contains a scrollable list of 26 letters A to Z in a RecyclerView. The orientation
of the RecyclerView can be changed between a vertical list or a grid of items.

The app demonstrates the use of Intents in two ways:
* to navigate inside an app by specifying an explicit destination, and,
* allowing Android to service the Intent using the apps and resources present on the device.

# Pre-requisites
* Experience with Kotlin syntax.
* Able to create an Activity.
* Able to create a RecyclerView and supply it with data.

# Getting Started
1. Install Android Studio, if you don't already have it.
2. Download the sample.
3. Import the sample into Android Studio.
4. Build and run the sample.


==========================================================
# Android Navigation codelab

Content: https://codelabs.developers.google.com/codelabs/android-navigation/



Android Architecture Components Navigation Basic Sample
==============================================

### Features

This sample showcases the following features of the Navigation component:

 * Navigating via actions
 * Transitions
 * Popping destinations from the back stack
 * Arguments (profile screen receives a user name)
 * Deep links (`www.example.com/user/{user name}` opens the profile screen)

### Screenshots
<img src="screenshot.png" height="400" alt="Screenshot"/> 

### Other Resources

 * Particularly In Java, consider using `Navigation.createNavigateOnClickListener()` to quickly
 create click listeners.
 * Consider including the [Navigation KTX libraries](https://developer.android.com/topic/libraries/architecture/adding-components#navigation)
  for more concise uses of the Navigation component. For example, calls to ![screenshot.png](screenshot.png)`Navigation.findNavController(view)` can
 be expressed as `view.findNavController()`.

### Tests

This sample contains UI tests that can be run on device (or emulator) or on the host
(as a JVM test, using Robolectric). As of Android Studio (3.3.1), running these tests will default
to Android Instrumented test (device or emulator). In order to run them with Robolectric you have
two options:
 * From the command line, running `./gradlew test`
 * From Android Studio, creating a new "Android JUnit" run configuration and targeting "all
 in package" or a specific class or directory.





Android Architecture Components Advanced Navigation Sample
==============================================

### Features

This sample showcases the behavior of a bottom navigation view following the [Principles of
Navigation](https://developer.android.com/topic/libraries/architecture/navigation#fixed).[README.md]

* Fixed start destination
* Navigation state should be represented via a stack of destinations
* The Up button never exits your app
* Up and Back are identical within your app's task
* Deep linking and navigating to a destination should yield the same stack

Check out the
[UI tests](https://github.com/googlesamples/android-architecture-components/tree/master/NavigationAdvancedSample/app/src/androidTest/java/com/example/android/navigationadvancedsample)
to learn about specific scenarios.





MAD Skills Navigation Sample (Donut Tracker)
==============================================

This sample shows the features of Navigation component highlighted by the Navigation
episodes in the MAD Skills series of [videos](https://www.youtube.com/user/androiddevelopers)
and [articles](https://medium.com/androiddevelopers). Specifically, episodes
2, 3, andd 4 walk through code from this sample.

### Features

This sample showcases the following features of the Navigation component:

* Dialog destinations (episode 2)
* Using SafeArgs to pass data between destinations (episode 3)
* Navigating via shortcuts and notifications with Deep Links (episode 4)

### Screenshots
<img src="screenshot.png" height="400" alt="Screenshot"/>

### Other Resources

* For an overview of using Navigation component, check out
  [Get started with the Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)
* Consider including the [Navigation KTX libraries](https://developer.android.com/topic/libraries/architecture/adding-components#navigation)
  for more concise uses of the Navigation component. For example, calls to `Navigation.findNavController(view)` can
  be expressed as `view.findNavController()`.
