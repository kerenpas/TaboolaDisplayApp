# TaboolaDisplayApp

## Overview
TaboolaDisplayApp is an Android application designed to display and manage a grid of cells, each with customizable color and visibility. The app supports real-time and persistent updates to cell states, enabling seamless integration with external controller apps and robust UI synchronization.

## Architecture

### Main Components
- **Room Database**: Persists cell color and visibility data in a local SQLite database using Room ORM. The `cellColor` table stores each cell's position, color, and visibility state.
- **ContentProvider**: Exposes the cell color data for cross-app access. External apps (such as a controller app) can update cell states even when TaboolaDisplayApp is not running.
- **AIDL Service**: Provides a real-time IPC mechanism for controller apps to update cell states while TaboolaDisplayApp is running, ensuring immediate UI feedback.
- **Repository Pattern**: The app uses repositories to abstract data access, supporting both in-memory and persistent storage.
- **Hilt Dependency Injection**: All major components (database, DAOs, use cases, repositories) are provided as singletons using Hilt for clean, testable architecture.
- **Caching & Offline Mode**: The app uses local caching (Room database and in-memory cache) to ensure that cell state data is available and up-to-date even when the device is offline. All cell state changes are stored locally and synchronized with the UI, so the app works fully in offline mode.

### Data Flow
- **UI Layer**: Observes cell state changes via LiveData/RxJava from the repository.
- **Repository Layer**: Handles updates from both the AIDL service and ContentProvider, synchronizing with the Room database and in-memory cache.
- **ContentProvider**: Allows external apps to update cell states by writing to the database. When TaboolaDisplayApp restarts, it loads the latest cell states from the database and updates the UI.
- **AIDL Service**: Enables real-time updates to the UI by directly invoking repository methods when the app is running.

## Why Use Both AIDL Service and ContentProvider?

### AIDL Service
- **Purpose**: Enables fast, real-time communication between the controller app and TaboolaDisplayApp when both are running.
- **Benefit**: Immediate UI updates, low latency, and efficient in-memory data handling.

### ContentProvider
- **Purpose**: Allows persistent updates to cell states even when TaboolaDisplayApp is not running.
- **Benefit**: Ensures that any changes made by the controller app are saved to the database and reflected in the UI the next time TaboolaDisplayApp starts.

### Combined Approach
By using both mechanisms, the app achieves:
- **Reliability**: Updates are never lost, as they are always saved to the database via the ContentProvider.
- **Responsiveness**: Real-time UI updates are possible when the app is running, thanks to the AIDL service.
- **Interoperability**: External apps can interact with TaboolaDisplayApp regardless of its running state.
- **Offline Support**: All cell state data is cached locally, so the app works seamlessly even without an internet connection.

## Getting Started
1. Clone the repository:
   ```
   
   ```
2. Open the project in Android Studio.
3. Build and run the app on your device or emulator.

## License
This project is licensed under the MIT License.
