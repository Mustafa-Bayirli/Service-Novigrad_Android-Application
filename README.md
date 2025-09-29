# Service Novigrad - Android Application

---

## 📱 Project Overview
A complete Android application for the fictional province of Novigrad, providing government services similar to Service Ontario. Developed for SEG2105 - Introduction to Software Engineering.

---

## 🏗️ Project Structure

SERVICE-NOVIGRAD/
- ├── app/
- │   ├── src/main/java/com/example/project_notgoingtofail/
- │   │   ├── AdminMainActivity.java
- │   │   ├── AdminBranchEditingActivity.java
- │   │   ├── AdminUserEditingActivity.java
- │   │   ├── BranchEmployeeActivity.java
- │   │   ├── CustomerActivity.java
- │   │   ├── CustomerServiceActivity.java
- │   │   ├── LoginActivity.java
- │   │   ├── RegisterActivity.java
- │   │   ├── MainActivity.java
- │   │   └── ErrorActivity.java
- │   ├── src/androidTest/ - Instrumentation tests
- │   ├── src/test/ - Unit tests
- │   └── res/ - Resources and layouts
- ├── sqlite/ - Database files
- └── Domain diagram.pdf - UML documentation

---

## 👥 User Roles & Features

### Administrator
- **Default Credentials:** username: \`admin\`, password: \`123admin456\`
- Manage services (Driver's License, Health Card, Photo ID)
- Manage branch and customer accounts
- Define required documents for each service

### Branch Employee
- Create and manage branch profiles
- Set working hours
- Approve/reject service requests
- Manage offered services

### Customer
- Search for branches by location/service/hours
- Submit service requests with documents
- Rate branch experiences

--- 

## 🛠️ Technical Stack
- **Language:** Java
- **Database:** SQLite
- **IDE:** Android Studio
- **Architecture:** MVC Pattern
- **Testing:** JUnit

---

## 🚀 Installation & Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK
- Java JDK 8+

### Build Instructions
1. Clone this repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Build → Make Project (Ctrl+F9)
5. Run on emulator or device

### APK Generation
\`\`\`bash
./gradlew assembleDebug
APK location: app/build/outputs/apk/debug/app-debug.apk
\`\`\`


---

## 📊 Database Schema
The application uses SQLite with multiple tables:
- \`users\` - User accounts and credentials
- \`branches\` - Branch information and services
- \`services\` - Available services and requirements
- \`requests\` - Customer service requests

---

## 🧪 Testing
Run unit tests:
\`\`\`bash
./gradlew test
\`\`\`

Run instrumented tests:
\`\`\`bash
./gradlew connectedAndroidTest
\`\`\`

---

## 📅 Project Deliverables
- ✅ **Deliverable 1:** User Account Management
- ✅ **Deliverable 2:** Admin Functionality  
- ✅ **Deliverable 3:** Branch Employee Functionality
- ✅ **Deliverable 4:** Complete Application

## ⚠️ Important Notes
- Database file: \`Service_Novigrad.db\`
- Default admin account is pre-configured
- All sensitive data should be handled securely in production
- Follow Android design guidelines for UI improvements

