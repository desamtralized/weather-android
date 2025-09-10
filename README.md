# Weather UI

A responsive Android weather application with adaptive layouts for mobile, tablet, and TV platforms.

## Features

- **Real-time weather data** from Weather.gov API
- **Adaptive UI** that adjusts to device screen size
- **Multi-platform support** for Android phones, tablets, and Fire TV
- **Lottie animations** for dynamic weather visualization
- **Mapbox integration** for static location maps
- **Material 3 design** with modern theming

## Architecture

### Multi-Module Structure

```
weatherui/
├── app/                    # Main application with platform-specific implementations
│   ├── google/            # Standard Android variant
│   └── firetv/            # Amazon Fire TV variant
├── modules/
│   ├── data/              # Weather API integration and repository layer
│   └── ui-widget/         # Reusable UI components and adaptive layouts
```

### Key Technologies

- **Jetpack Compose** for declarative UI
- **Hilt** for dependency injection
- **Retrofit** for network operations
- **StateFlow/Flow** for reactive data streams
- **MVVM pattern** with unidirectional data flow

## Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- JDK 17
- Android SDK with minimum API 24

### Build & Run

```bash
# Build debug variant
./gradlew assembleDebug

# Install on connected device
./gradlew installGoogleDebug    # Standard Android
./gradlew installFiretvDebug    # Fire TV

# Run tests
./gradlew test
```

### Product Flavors

- **google**: Standard Android mobile/tablet experience
- **firetv**: TV-optimized interface with D-pad navigation

## Configuration

Weather data requires valid coordinates. The app uses Weather.gov API which is free and requires no
API key.

## Development

```bash
# Run lint checks
./gradlew lint

# Clean build
./gradlew clean build

# Generate release bundle
./gradlew bundleRelease
```