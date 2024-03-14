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
