# TODO List App 📋

A simple and user-friendly TODO list app built using **Kotlin** and **Jetpack Compose**. The app allows users to manage their tasks efficiently while demonstrating clean code architecture and modern Android development best practices.

---

## 📱 Features

1. **Main Screen**
   - Displays a list of TODO items.
   - Shows a placeholder text when the list is empty: *"Press the + button to add a TODO item."*
   - Includes a search bar to filter the TODO items in real-time (debounced after 2 seconds of inactivity).
   - Features a floating action button (`+`) to navigate to the "Add New TODO" screen.

2. **Add TODO Screen**
   - Allows users to add a new TODO item with a single-line input.
   - Includes an *"Add TODO"* button:
     - Saves the input to a local database.
     - Displays a loading indicator for 3 seconds before navigating back to the main screen.

3. **Error Handling**
   - If the user enters the word *"Error"*, an exception is thrown, and the app:
     - Navigates back to the main screen.
     - Displays a popup with the message: *"Failed to add TODO."*

---

## 🛠️ Tech Stack and Tools

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM
- **State Management**: Kotlin Flows and Coroutines
- **Database**: Local Database (e.g., Room)
- **Testing**: Unit Tests (optional)
- **Build Tool**: Gradle

---

## 🧩 Key Implementation Details

- **Persistence**: State is maintained across screen rotations.
- **Error Handling**: Exceptions are caught and appropriate UI feedback is provided.
- **Real-Time Search**: Implements a debounce mechanism for efficient filtering.
- **Clean Architecture**:
  - Modularized project structure.
  - Separation of concerns with MVVM architecture.

---

## 🔥 Bonus Features

- Modular architecture for better scalability.
- Unit tests for critical components.

---

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/todo-list-app.git
2.	Open the project in Android Studio.
3.	Build the project and run it on an emulator or a physical device.


