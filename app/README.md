
# Bus Scheduler App

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

==================================================================


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
=======================================================================================

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
