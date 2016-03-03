# Mobile Coding Challenge

**Mobile Coding Challenge** allows the user to issue a simple command to the **Wealthsimple** api, and displays the response to the user.

## Architecture ##

The application is a single-screen application using the **Model-View-Presenter** pattern to separate business logic and view event handling into testable components.
**View** layer is implemented by **Activity**, and forwards *lifecycle* + *user* events to **Presenter**.
The **Presenter** executes business logic and updates **View** if required.

The **MVP** pattern allows us to move away from giant, monolithic controllers that are tightly coupled with views. One of the benefits of this is to decouple business logic from Android components, such as Activities or Fragments. The Android component (activity/fragment/etc) is responsible for calling the appropriate methods in the Presenter. The presenter executes business logic, and calls the View interface to update any views. The Android component implements the View interface, and simply updates the widgets as required. This allows us to change the implementation of the view, such as replacing an Activity with a Fragment, and avoid re-factoring any business logic in the view controllers. Essentially activities/fragments etc become dumb views that are only responsible for passing events to, and receiving view updates from the presenter.

**Dependency-injection** is used to instantiate objects such as Presenter, View, API, etc.

Dependency injection provides a consistent mechanism to instantiate concrete instances of business objects in the application. One of the advantages of this approach is to have a clean separation between the creation of objects and the consumption of objects. This allows us to safely test components in isolation, as we can replace real implementations with mock implementations during testing. In addition, changes to instance creation need only to happen in one location, and are reflected across the entire object graph.

Unit tests use *mocked* implementation of the API, and are primarily used to verify business logic in the **Presenter**.
UI tests interact with the app and real API, and are primarily used to verify user interactions.

Using dependency injection, we can instantiate a mock API when performing unit tests in order to fully verify business logic in the **Presenter**. This gives us consistency in tests, as well as avoids calling into Android framework so that the tests run quickly.

## Libraries used ##

* `com.android.support:appcompat` - AppCompat library for compatibility w/ older versions of Android
* `com.jakewharton:butterknife` - View injection. Convenience methods to interact with views.
* `com.google.dagger:dagger` - Dependency injection. Manage object dependencies and provide concrete instances of objects.
* `io.reactivex:rxandroid`- RxJava for Android. Asynchronous, event-based streams for multi-threaded operations.
* `com.squareup.retrofit2:retrofit` - HTTP client. Create simple annotation-based API requests.
* `org.mockito:mockito` - Mock objects for testing
* `org.powermock:powermock` - Mock static objects for testing
* `com.android.support.test:espresso` - UI testing

## Improvements ##
* Increase test coverage by adding more unit tests for business logic (especially error cases)
* Add more rigorous UI tests
* Handle caching API requests
* Add proguard rules to preserve classes from 3rd party libraries
