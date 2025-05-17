# 🧪 Reqres API Testing Project

This project demonstrates **API automation testing** using the public [Reqres](https://reqres.in/) REST API. It leverages **REST Assured**, **TestNG**, and **Maven**, following best practices like the **Page Object Model (POM)** for modularity, **external test data**, and organized test suites.

---

✅ Features
📦 Framework: REST Assured + TestNG + Maven

🧱 Design Pattern: Page Object Model (POM)

🗃️ External Test Data: JSON files for flexible test input

📄 Comprehensive Test Coverage: Covers registration, login, users, delayed response, etc.

📈 Allure Reports integrated

🔄 CI/CD Integration:

🧪 Runs tests with Jenkins

🚀 Executes on GitHub Actions

🔍 Logging with Log4j2

⚙️ Utility classes for reusable components

✅ Listeners for better test reporting and event handling
---

## 📁 Project Structure

```
ReqresAPiTesting/
+---main
|   +---java
|   |   +---data
|   |   |       Constants.java
|   |   |
|   |   +---models
|   |   |       User.java
|   |   |
|   |   \---utilities
|   |       +---helper
|   |       |       DataUtility.java
|   |       |       LogUtil.java
|   |       |       RequestBody.java
|   |       |       RequestSpec.java
|   |       |
|   |       \---Requests
|   |               DelayedResponse.java
|   |               LoginUtility.java
|   |               RegisterUtils.java
|   |               ResourceUtility.java
|   |               UserUtils.java
|   |
|   \---resources
|           Allure.properties
|           Log4j2.properties
|
\---test
    +---java
    |   +---Listeners
    |   |       TestListenerClass.java
    |   |
    |   \---Tests
    |           DelayedResponseTest.java
    |           E2ETests.java
    |           LoginTest.java
    |           RegisterTest.java
    |           ResourceTest.java
    |           UserTest.java
    |
    \---resources
            TestData.json
```

---

## 🚀 How to Run

### 🛠️ Prerequisites

- Java JDK 8 or above
- Maven
- IDE (e.g., IntelliJ IDEA, Eclipse)

### 🔧 Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/mmohamedADEL/ReqresAPiTesting.git
   ```

2. Navigate to the project:
   ```bash
   cd ReqresAPiTesting
   ```

3. Install dependencies:
   ```bash
   mvn clean install
   ```

4. Run tests using TestNG:
   ```bash
   mvn test
   ```

---
📊 Allure Reporting
To view the test reports:

bash
Copy
Edit
# Generate the report
 ```bash
   allure serve
   ```
This will launch the Allure Report in your browser.

⚙️ CI/CD Pipelines
✅ Jenkins: Configure the job to run mvn clean test and publish Allure reports.

✅ GitHub Actions: Workflow available under .github/workflows/ to auto-run tests on push.


## 📦 Dependencies

- **REST Assured** – for API testing
- **TestNG** – for test management
- **Jackson / Gson** – for JSON handling
- **Maven** – for project build and dependency management

---


## 👤 Author

**Mohamed Adel**  
📧 [LinkedIn](https://www.linkedin.com/in/mohamed-adel-elbaz-79239a272/)  
🧪 ISTQB Certified | Software Tester 

---

