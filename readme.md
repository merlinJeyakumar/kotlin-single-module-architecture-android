# NativeDevps Android Architecture (android-arch-nativedevps)

## Overview
`android-arch-nativedevps` is the standard template architecture repository used across NativeDevps Android projects. It provides a robust, Highly Maintainable Single-Module MVVM Architecture built on top of Jetpack components, Kotlin Coroutines, and Dagger Hilt.

It heavily integrates tools from the **[JeyKSupport](https://github.com/merlinJeyakumar/jeyksupport)** SDK and provides a secondary **NativeDevps module** (`nativedevps`) that houses standardization classes, predefined UI, and project-specific extensions.

## Project Structure
The repository is modularized as follows:
- `:app`: The consumer module for standard application code implementation (`com.nativedevps.myapplication`).
- `:jeyksupport`: Contains foundational base classes, deep utility extensions, flow executors, and generic methodologies.
- `:nativedevps`: A specialized module that builds upon `jeyksupport`. It establishes standard UI flows (Login, Register, Contact Us) and NativeDevps-specific base classes.
- `:S3Agent`: SDK module for AWS S3 integrations (if applicable).
- `buildSrc`: Handles Gradle dependency management and logic configuration separately.

## Core Architectural Components

### 1. Base UI Components (`com.nativedevps.base`)
- **`NativeDevpsBaseActivity` & `NativeDevpsBaseFragment`**: Enhances `jeyksupport`'s `BaseActivity` by providing mandatory NativeDevps-specific features automatically:
  - **App Update Enforcement**: Automatically checks `requiredVersion` against the Play Store. Prompts users when an updated version is required.
  - **Permission Management**: Centralized flow for Android 13+ (`POST_NOTIFICATIONS`) prompt requesting and analytics tracking.
  - **Dialogs & Delegations**: Native access to standardized `InputDialog`, `ListDialog`, `InformationDialog`, and `ConfirmationDialog` via `NativeDevpsProvider`.
- **`NativeDevpsBaseViewModel`**: Built upon `BaseViewModel`, extending its state handling.
  - Exposes essential state flows out-of-the-box (`isLoggedIn`, `userAccountDetails`, `requiredVersion`, `signatureVerified`).
  - Contains **FlowUseCase Executors** (`loadNCollect` / `loadOnQueue`) extending `jeyksupport` for easy backend communication, handling progress loaders, API success (`SuccessApiResult`), and unified API error prompting automatically.

### 2. Standardized Predefined UI (`com.nativedevps.ui`)
The `nativedevps` module provides ready-to-use activities to avoid boilerplate for every new project.
- **Login (`LoginActivity`)**: Pre-configured Validation, forgot password flows, and navigation extensions.
- **Register (`RegisterActivity`)**: Default account creation layouts mapped with UseCases.
- **Contact Us / Support (`ContactUsActivity`)**: Includes navigation routing payload parameters and support requests mechanisms.
- **Rating (`RatingDialog`)**: Standardized implementation utilizing `UpdateRatingUseCase` for app store feedback tracking.
- **Notification Overview (`NotificationActivity`)**: Built-in Firebase/OneSignal push payload history parsing.

### 3. Analytics Standard
The architecture mandates structured analytics out-of-the-box via `NativeDevpsAnalytics`. Base components directly intercept default behaviors (e.g., Permission Denied) and push metrics to Firebase/Crashlytics automatically tracking standard conversions.

## Principles to adhere to Development:
- Implement any new network logic through `FlowUseCase` and execute it with `loadNCollect` in ViewModels.
- Always use `ViewDataBinding` inheriting from `NativeDevpsBaseActivity`.
- Treat the `:app` module as purely application logic, avoiding re-inventing authentication or standard dialogue features already present in `:nativedevps`.
