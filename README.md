# 📘 StackExchange Test App

An Android application built with **Kotlin** that demonstrates consuming the **StackExchange API** to retrieve a list of users and detailed user information.  
The project follows the **MVVM architecture** and uses modern Android development libraries.

---

## 🧾 Overview

This project showcases how to integrate the **StackExchange API** into an Android application using best practices such as:

- **MVVM (Model–View–ViewModel)** architecture
- **Retrofit** for networking
- **Gson Converter** for JSON parsing
- **ViewModel & LiveData**
- **Coil** for image loading
- Clean separation of concerns

The app retrieves:
- 👥 A paginated list of StackOverflow users  
- 👤 Detailed information for a selected user

---

## 🔗 StackExchange API

The app consumes the public **StackExchange REST API**.

### Endpoints Used

- **Get users**
GET https://api.stackexchange.com/2.3/users

### Sample Query Parameters

- `site=stackoverflow`
- `page`
- `pagesize`
- `order`
- `sort`

---

## 🏗️ Architecture

This project follows the **MVVM (Model–View–ViewModel)** pattern.
- **View (Activity / Fragment / XML)** → **ViewModel** → **Repository** → **Remote Data Source (Retrofit API)**
