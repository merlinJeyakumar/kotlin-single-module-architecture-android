# NativeDevps Android Architecture Template

A starter Android application structure for teams that want a consistent place to begin with screens, state, storage, remote services, notifications, and application configuration.

This repository is a **template and demonstration shell**, not a complete consumer product. The included `app` module shows how shared NativeDevps components can be connected in a new Android application.

## What it demonstrates

- A launch flow through `HomeActivity` with sample dialogs, loading states, rating/about entry points, notification handling, and a mock API action.
- Application startup wiring for Hilt dependency injection, WorkManager, and Firebase Remote Config.
- Reference patterns for screen state, preferences, local database access, and remote service calls.
- Firebase Cloud Messaging, crash-reporting, notification, and FileProvider integration points for a consuming application.
- Separate development and production build flavors.
- View Binding and Data Binding support for application screens.
- Shared UI and utility foundations supplied by the required local NativeDevps modules.

## How the template is organised

| Area | Purpose |
| --- | --- |
| `app/` | The application-specific module. It contains the demonstration launcher, sample screens, dependency wiring, local preferences, sample Room storage, and placeholder remote-service flows. |
| `modules/jeyksupport` | Required local support-library checkout for shared Android foundations and utilities. |
| `modules/nativedevps/nativedevps` | Required local NativeDevps module used by the template’s shared UI and application foundations. |
| `modules/S3Agent` | Required local Android S3 helper module included by the Gradle settings. |
| `buildSrc/` | Shared Gradle versions, dependency declarations, and build helpers. |

The three local modules are required by `settings.gradle`. Their implementation source is not included in this repository clone, so Gradle sync and builds need those directories to be available at the listed paths.

## Included application flows

- **Home demonstration:** opens inherited dialogs and loaders, handles a sample notification/deep-link path, and triggers a mock initial request.
- **Preference storage:** shows a DataStore-backed preference layer for app values.
- **Local storage:** includes a sample Room database, DAO, and entity pattern.
- **Remote-service wiring:** includes Retrofit/OkHttp and Flow-oriented use-case examples that can be replaced with product-specific services.
- **Firebase hooks:** includes Remote Config startup, notification service registration, Crashlytics dependencies, and Google Services plugin wiring.
- **Shared module hooks:** declares integration points for the required local modules without documenting their unavailable implementation source as part of this repository.

## Prerequisites

Before opening the project in Android Studio, provide these local modules:

```text
modules/
├── jeyksupport/
├── nativedevps/
│   └── nativedevps/
└── S3Agent/
```

The current application module is configured with:

- Java 17
- Android `minSdk 21`
- Android `compileSdk 35` and `targetSdk 35`
- View Binding and Data Binding
- Development and production product flavors
- Hilt, Retrofit/OkHttp, Room, DataStore, Firebase, Navigation, and WorkManager

Create or select your own Firebase project before using Firebase, Crashlytics, Remote Config, or Cloud Messaging in a new application. Consumer applications must use their own package identity and service configuration rather than relying on template configuration.

## Getting started

1. Place the required local modules in the paths shown above.
2. Open the repository in Android Studio with Java 17 installed.
3. Configure the application ID, app name, Firebase project, backend URLs, and product-specific values for the new application.
4. Sync Gradle and select the appropriate development or production build variant.
5. Build the application module after replacing the demonstration services with the intended product integrations.

From the repository root, the development debug variant can be assembled with:

```bash
./gradlew :app:assembleDevelopmentDebug
```

On Windows:

```bat
gradlew.bat :app:assembleDevelopmentDebug
```

## Current scope

This repository is a reusable starting point. The included screens, user models, local database, notification flow, and remote-service examples are reference material rather than a finished business application.

Before using the template as a production baseline, review and replace:

- sample API routes and demonstration data flows;
- application identity and Firebase service configuration;
- network, storage, notification, and privacy settings for the target product;
- the local-module versions and integration points required by the consuming application.

## Verification status

The repository includes unit and instrumentation test scaffolding. The source does not demonstrate completed end-to-end product-flow coverage, so a consuming application should add feature-specific tests and release validation before production use.