# NoteApp - Notes Management Application

[English](#noteapp---notes-management-application) | [Русский](#noteapp---приложение-для-управления-заметками)

---

## NoteApp - Приложение для управления заметками

### Описание

NoteApp — Android-приложение для создания, редактирования и управления заметками. Приложение реализовано с использованием Clean Architecture, обеспечивает безопасное хранение данных через Room Database и аутентификацию через Firebase Authentication.

### Основные возможности

- Аутентификация пользователей через Firebase (регистрация и вход)
- Создание, редактирование и удаление заметок
- Локальное хранение данных в Room Database
- Поиск заметок с подсветкой результатов
- Переключение между Grid и List режимами отображения
- Цветовые категории заметок (жёлтый, чёрный, бордовый)
- Настройка темы (тёмная/светлая)
- Настройка размера текста заметок
- Onboarding с анимациями Lottie

### Технологический стек

**Языки и платформы:**
- Kotlin 2.0.20
- Android SDK 34 (Android 14)
- Min SDK 24 (Android 7.0)
- Java 1.8

**Архитектура:**
- Clean Architecture (Data, Domain, Presentation)
- MVVM (Model-View-ViewModel)
- MVI (Model-View-Intent) для управления состоянием
- Repository Pattern

**Основные библиотеки:**
- AndroidX Core KTX 1.13.1
- AndroidX AppCompat 1.7.0
- Material Design Components 1.12.0
- Navigation Component 2.8.2
- Room Database 2.6.1
- Firebase Authentication 23.0.0
- Koin 4.0.0 (Dependency Injection)
- Lottie 6.1.0
- DotsIndicator 4.3

### Архитектура проекта

Проект организован по принципам Clean Architecture с разделением на три основных слоя:

**Presentation Layer** (`ui/`)
- Фрагменты (экранные компоненты)
- ViewModels (бизнес-логика представления)
- Адаптеры RecyclerView
- Intent-классы для MVI паттерна

**Data Layer** (`data/`)
- Room Database (локальное хранилище)
- SharedPreferences (настройки приложения)
- Реализация репозиториев
- Миграции базы данных

**DI Layer** (`di/`)
- Модули Koin для внедрения зависимостей
- Конфигурация Room, Firebase, Preferences

### Структура проекта

```
app/src/main/java/com/example/kotlin3_3/
├── data/
│   ├── local/room/
│   │   ├── AppDatabase.kt
│   │   ├── Migrations.kt
│   │   ├── dao/NoteDao.kt
│   │   ├── entities/Note.kt
│   │   └── convertors/DateConvertors.kt
│   ├── preference/PreferencesHelper.kt
│   └── repository/impl/
│       ├── NoteRepositoryImpl.kt
│       └── AuthRepositoryImpl.kt
├── di/
│   ├── AppModule.kt
│   ├── RepositoryModule.kt
│   └── ViewModelModule.kt
├── ui/
│   ├── activity/MainActivity.kt
│   ├── fragments/
│   │   ├── firebase/ (SignIn, SignUp)
│   │   ├── noteapp/ (NoteApp, AddNote)
│   │   ├── onboard/ (Onboarding)
│   │   ├── menu/ (Profile, Settings, Share, Dev, Exit)
│   │   └── intent/ (MVI Intents)
│   ├── adapters/NoteAdapter.kt
│   └── model/
└── utils/
    ├── state/ (Sealed State classes)
    └── CoroutineDispatchers.kt
```

### Установка и настройка

**Требования:**
- Android Studio Hedgehog (2023.1.1) или новее
- JDK 1.8 или выше
- Android SDK 34
- Gradle 8.5+

**Шаги установки:**

1. Клонируйте репозиторий:
   ```bash
   git clone <repository-url>
   cd NoteApp
   ```

2. Настройте Firebase:
   - Создайте проект в Firebase Console
   - Включите Authentication → Email/Password
   - Скачайте файл `google-services.json` и поместите в директорию `app/`

3. Откройте проект в Android Studio:
   - File → Open → выберите папку проекта
   - Дождитесь завершения синхронизации Gradle

4. Запустите приложение:
   - Подключите устройство или запустите эмулятор
   - Нажмите Run или используйте сочетание клавиш Shift + F10

### Функциональность

**Аутентификация:**
- Регистрация нового пользователя (email и пароль)
- Вход в существующий аккаунт
- Валидация email и пароля
- Обработка ошибок Firebase
- Сохранение сессии пользователя

**Управление заметками:**
- Создание заметок с заголовком и описанием
- Редактирование существующих заметок
- Удаление заметок с подтверждением через AlertDialog
- Поиск заметок по названию с подсветкой совпадений
- Переключение между Grid и List режимами отображения
- Цветовые категории: жёлтый (#EBE4C9), чёрный (#191818), бордовый (#571818)
- Автоматическое добавление даты и времени при создании

**Настройки:**
- Переключение между тёмной и светлой темой
- Выбор размера текста заметок (маленький, средний, большой)
- Превью изменений в реальном времени

**Профиль:**
- Отображение email пользователя
- Дата создания аккаунта (из метаданных Firebase)

**Навигация:**
- Боковое меню (Navigation Drawer) с профилем
- Onboarding экраны с анимациями
- Переходы между экранами через Navigation Component

### Миграции базы данных

При изменении схемы базы данных необходимо:

1. Увеличить версию базы данных в `AppDatabase.kt`:
   ```kotlin
   @Database(entities = [Note::class], version = 11)
   ```

2. Добавить миграцию в `Migrations.kt`:
   ```kotlin
   val MIGRATION_10_11 = migration(10, 11) { db ->
       db.execSQL("ALTER TABLE note_table ADD COLUMN is_pinned INTEGER NOT NULL DEFAULT 0")
   }
   
   val ALL_MIGRATIONS: Array<Migration> = arrayOf(
       MIGRATION_10_11,
   )
   ```

### Тестирование

Проект включает базовую инфраструктуру для тестирования:
- JUnit 4.13.2 для unit-тестов
- Espresso 3.6.1 для UI-тестов
- AndroidX Test Extensions 1.2.1

### Лицензия

Проект создан в образовательных целях.

### Контакты разработчика

- Email: erbolulanov@gmail.com
- Telegram: @Erbolbro
- LinkedIn: Erbol Ulanov

---

## NoteApp - Notes Management Application

### Description

NoteApp is an Android application for creating, editing, and managing notes. The application is implemented using Clean Architecture principles, provides secure data storage through Room Database, and authentication through Firebase Authentication.

### Key Features

- User authentication via Firebase (registration and login)
- Create, edit, and delete notes
- Local data storage in Room Database
- Note search with result highlighting
- Toggle between Grid and List display modes
- Color categories for notes (yellow, black, burgundy)
- Theme customization (dark/light)
- Text size customization for notes
- Onboarding with Lottie animations

### Technology Stack

**Languages and Platforms:**
- Kotlin 2.0.20
- Android SDK 34 (Android 14)
- Min SDK 24 (Android 7.0)
- Java 1.8

**Architecture:**
- Clean Architecture (Data, Domain, Presentation)
- MVVM (Model-View-ViewModel)
- MVI (Model-View-Intent) for state management
- Repository Pattern

**Main Libraries:**
- AndroidX Core KTX 1.13.1
- AndroidX AppCompat 1.7.0
- Material Design Components 1.12.0
- Navigation Component 2.8.2
- Room Database 2.6.1
- Firebase Authentication 23.0.0
- Koin 4.0.0 (Dependency Injection)
- Lottie 6.1.0
- DotsIndicator 4.3

### Project Architecture

The project is organized according to Clean Architecture principles with separation into three main layers:

**Presentation Layer** (`ui/`)
- Fragments (screen components)
- ViewModels (presentation business logic)
- RecyclerView adapters
- Intent classes for MVI pattern

**Data Layer** (`data/`)
- Room Database (local storage)
- SharedPreferences (application settings)
- Repository implementations
- Database migrations

**DI Layer** (`di/`)
- Koin modules for dependency injection
- Room, Firebase, Preferences configuration

### Project Structure

```
app/src/main/java/com/example/kotlin3_3/
├── data/
│   ├── local/room/
│   │   ├── AppDatabase.kt
│   │   ├── Migrations.kt
│   │   ├── dao/NoteDao.kt
│   │   ├── entities/Note.kt
│   │   └── convertors/DateConvertors.kt
│   ├── preference/PreferencesHelper.kt
│   └── repository/impl/
│       ├── NoteRepositoryImpl.kt
│       └── AuthRepositoryImpl.kt
├── di/
│   ├── AppModule.kt
│   ├── RepositoryModule.kt
│   └── ViewModelModule.kt
├── ui/
│   ├── activity/MainActivity.kt
│   ├── fragments/
│   │   ├── firebase/ (SignIn, SignUp)
│   │   ├── noteapp/ (NoteApp, AddNote)
│   │   ├── onboard/ (Onboarding)
│   │   ├── menu/ (Profile, Settings, Share, Dev, Exit)
│   │   └── intent/ (MVI Intents)
│   ├── adapters/NoteAdapter.kt
│   └── model/
└── utils/
    ├── state/ (Sealed State classes)
    └── CoroutineDispatchers.kt
```

### Installation and Setup

**Requirements:**
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 1.8 or higher
- Android SDK 34
- Gradle 8.5+

**Installation Steps:**

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd NoteApp
   ```

2. Configure Firebase:
   - Create a project in Firebase Console
   - Enable Authentication → Email/Password
   - Download `google-services.json` file and place it in the `app/` directory

3. Open the project in Android Studio:
   - File → Open → select project folder
   - Wait for Gradle sync to complete

4. Run the application:
   - Connect a device or start an emulator
   - Press Run or use Shift + F10

### Functionality

**Authentication:**
- New user registration (email and password)
- Login to existing account
- Email and password validation
- Firebase error handling
- User session persistence

**Note Management:**
- Create notes with title and description
- Edit existing notes
- Delete notes with confirmation via AlertDialog
- Search notes by title with match highlighting
- Toggle between Grid and List display modes
- Color categories: yellow (#EBE4C9), black (#191818), burgundy (#571818)
- Automatic date and time addition on creation

**Settings:**
- Toggle between dark and light theme
- Text size selection for notes (small, medium, large)
- Real-time preview of changes

**Profile:**
- Display user email
- Account creation date (from Firebase metadata)

**Navigation:**
- Side menu (Navigation Drawer) with profile
- Onboarding screens with animations
- Screen transitions via Navigation Component

### Database Migrations

When changing the database schema:

1. Increase the database version in `AppDatabase.kt`:
   ```kotlin
   @Database(entities = [Note::class], version = 11)
   ```

2. Add migration in `Migrations.kt`:
   ```kotlin
   val MIGRATION_10_11 = migration(10, 11) { db ->
       db.execSQL("ALTER TABLE note_table ADD COLUMN is_pinned INTEGER NOT NULL DEFAULT 0")
   }
   
   val ALL_MIGRATIONS: Array<Migration> = arrayOf(
       MIGRATION_10_11,
   )
   ```

### Testing

The project includes basic testing infrastructure:
- JUnit 4.13.2 for unit tests
- Espresso 3.6.1 for UI tests
- AndroidX Test Extensions 1.2.1

### License

This project is created for educational purposes.

### Developer Contacts

- Email: erbolulanov@gmail.com
- Telegram: @Erbolbro
- LinkedIn: Erbol Ulanov
