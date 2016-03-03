# Mobile Coding Challenge

**Mobile Coding Challenge** allows the user to issue a simple command to the **Wealthsimple** api, and displays the response to the user.

## Architecture ##

The application is a single-screen application using the **Model-View-Presenter** pattern to separate business logic and view event handling into testable components.
**View** layer is implemented by **Activity**, and forwards *lifecycle* + *user* events to **Presenter**.
The **Presenter** executes business logic and updates **View** if required.

**Dependency-injection** is used to instantiate **Presenter** and **View**.

Unit tests use *mocked* implementation of the API, and are primarily used to verify business logic in the **Presenter**.
UI tests interact with the app and real API, and are primarily used to verify user interactions.

## Libraries used ##

* `com.android.support:appcompat` - AppCompat library for compatibility w/ older versions of Android
* `com.jakewharton:butterknife` - View injection. Convenience methods to interact with views.
* `com.google.dagger:dagger` - Dependency injection. Manage object dependencies and provide concrete instances of objects.
* `io.reactivex:rxandroid`- RxJava for Android. Asynchronous, event-based streams for multi-threaded operations.
* `com.squareup.retrofit2:retrofit` - HTTP client. Create simple annotation-based API requests.
* `org.mockito:mockito` - Mock objects for testing
* `org.powermock:powermock` - Mock static objects for testing
* `com.android.support.test:espresso` - UI testing
