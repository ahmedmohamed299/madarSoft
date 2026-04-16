# MadarSoft User Management App

A professional, 2-screen Android application built to demonstrate modern Android development best practices. The app strictly follows **Clean Architecture** guidelines and enforces **SOLID** principles, ensuring a highly scalable, testable, and robust codebase.

## 📱 Features

- **User Registration (Input Screen):** Allows entering user details (Name, Age, Job Title, Gender) with comprehensive input validation.
- **User Dashboard (Display Screen):** Displays a reactive list of stored users. Includes empty-state handling.
- **Premium UI / UX:** Built with native XML Views utilizing **Material 3 Design components**. The UI dynamically adapts to system themes, providing optimized and beautiful out-of-the-box support for both **Dark Mode** and **Light Mode**. 

---

## 🛠️ Technology Stack

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI:** XML Layouts (No Jetpack Compose) + Material Components
- **Architecture:** Clean Architecture (Domain, Data, Presentation) + MVVM pattern
- **Dependency Injection:** [Dagger-Hilt](https://dagger.dev/hilt/)
- **Local Database:** [Room Database](https://developer.android.com/training/data-storage/room)
- **Asynchrony & Reactive Streams:** [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) + [Flow](https://kotlin.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)
- **Navigation:** [Jetpack Navigation Component](https://developer.android.com/guide/navigation) (SafeArgs)

---

## 🏗️ Project Structure (Clean Architecture)

The project separates concerns by strictly decoupling business logic from Android framework dependencies:

```text
com.android.madarsoft
├── data                # Internal data logic (Implementation)
│   ├── local           # Room DB, Entity Models, and DAOs
│   └── repository      # Maps DAOs cleanly to the Domain layer
├── domain              # Core Business Logic (No Android specific dependencies)
│   ├── model           # Pure Kotlin data classes (e.g., User)
│   ├── repository      # Repository interfaces (Contracts)
│   └── usecase         # Single-responsibility logic (AddUserUseCase, GetAllUsersUseCase)
├── presentation        # UI Layer 
│   ├── display         # RecyclerView, DisplayFragment, DisplayViewModel
│   ├── input           # Input form, Validations, InputFragment, InputViewModel
│   └── UiState.kt      # Encapsulated state resource representations (Idle, Loading, Success, Error)
├── di                  # Hilt Dependency Modules (DatabaseModule, AppModule)
└── utils               # Extension utilities containing Mapper functions
```

---

## 🧪 Testing

The codebase maintains a strong focus on testability, testing different repository flows, synchronous, and asynchronous behaviors without flaky interactions.

- **JUnit4:** Standard testing framework.
- **Mockito-Kotlin:** Mocking dependencies and asserting layer interactions.
- **Turbine:** For easily verifying reactive Kotlin `Flow` emissions and UI states.
- **kotlinx-coroutines-test:** Handing `runBlocking` and `TestDispatchers` to safely test suspend functions safely.

### How to Run Tests
To run the local unit tests via command-line:
```bash
./gradlew testDebugUnitTest
```

---

## ▶️ Getting Started

1. Clone this repository.
2. Open the project in **Android Studio** (Ladybug or newer).
3. Allow Gradle to sync the project dependencies.
4. Click **Run** (`Shift + F10`) to deploy to a connected physical device or emulator. 
*(Note: Requires minimum API level 30).*
