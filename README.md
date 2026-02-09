# 🌦️ Climey

**Climey** is a native Android weather application engineered to deliver a minimalist, fluid, and highly adaptive user experience. Built with modern Android development standards, it leverages **Material Design 3** to provide a consistent and aesthetically pleasing interface across a wide range of devices, from standard smartphones to foldables and tablets.

## 📱 Interface Preview

| Dark Mode | Light Mode | Tablet / Foldable Support |
| :---: | :---: | :---: |
|<img width="1080" height="2400" alt="Screenshot_20260209_114712" src="https://github.com/user-attachments/assets/1b83ecea-a837-4fd3-b2ee-46be5970a2b9" /> | <img width="1080" height="2400" alt="Screenshot_20260209_114810" src="https://github.com/user-attachments/assets/e7111b2f-0e69-45f8-b9c6-88cf6fd3e220" /> | <img width="2208" height="1840" alt="Screenshot_20260209_120117" src="https://github.com/user-attachments/assets/5c0eefe0-7b97-46d2-8833-c6f664592910" />|


## 🚀 Project Overview & Features

Climey is not just about displaying weather data; it is an exploration of modern UI/UX patterns and robust software architecture.

* **Bespoke Minimalist Design:** The interface is a custom creation, prioritizing clarity and elegance over clutter.
* **Immersive Interactions:** Features subtle micro-animations and **Lottie** integrations to provide visual feedback and enhance user engagement.
* **Adaptive Layouts:** The UI is fully responsive. It intelligently utilizes available screen estate, offering optimized layouts for **Foldables and Tablets** without compromising the experience on standard handsets.
* **Modern UI Toolkit:** Developed entirely using **Jetpack Compose** and **Material Design 3**.

## 🏗️ Architecture & Engineering

This project adheres to strict software engineering principles to ensure scalability, testability, and maintainability.

* **Clean Architecture:** The codebase is strictly divided into Presentation, Domain, and Data layers, ensuring separation of concerns.
* **MVVM Pattern:** Utilizes Model-View-ViewModel to manage UI state reactively and efficiently.
* **KMP-Ready Structure:** While currently deployed as a Native Android application, the domain and data layers are structured to facilitate a seamless migration to **Kotlin Multiplatform (KMP)**. The business logic is decoupled from the Android framework where possible.

## 🤖 Development Philosophy (AI Usage)

In an era of automated code generation, Climey maintains a human-centric approach to software craftsmanship:

> **100% Human-Written Code:**
> All architectural decisions, core logic, and UI implementations are manually crafted by the developer. **Artificial Intelligence tools were utilized exclusively for technical research and documentation purposes.** No code generation tools were used for the application development.

## 🛠️ Getting Started

Follow these detailed instructions to build and run the project locally. No external API keys are required for the initial setup.

### Prerequisites
* **Git** installed on your machine.
* A stable internet connection to download dependencies.

### Installation Steps

**1. Install Android Studio**
Download and install the latest stable version of **Android Studio** (Koala or newer is recommended) from the [official developer site](https://developer.android.com/studio).
* During installation, ensure the **Android SDK** and **Android Virtual Device (AVD)** components are selected.

**2. Clone the Repository**
Open your terminal or command prompt and run the following command to download the project source code:

```bash
git clone [https://github.com/JBgamer5/Climey.git](https://github.com/JBgamer5/Climey.git)
