# Reqres API Testing

This repository contains API testing projects for the [Reqres](https://reqres.in/) RESTful service. It includes both manual and automated API testing examples.

---

## 📁 Branches

* `main`: Postman collection for manual and automated execution using **Newman**
* [`master`](https://github.com/mmohamedADEL/ReqresAPiTesting/tree/master): REST-assured (Java) project for automated API testing

---

## 🔹 main Branch — Postman Collection

### 📄 File

* `Reqres.postman_collection.json`: Postman requests for testing login, user data, create/update/delete, and delayed responses.

### ▶️ How to Use with Postman

1. Install [Postman](https://www.postman.com/)
2. Import the `Reqres.postman_collection.json` file
3. Run requests individually or with the Collection Runner

### ▶️ How to Run with Newman (CLI)

1. Make sure you have [Node.js](https://nodejs.org/) installed
2. Install Newman and optional reporters:

   ```bash
   npm install -g newman newman-reporter-html newman-reporter-htmlextra
   ```
3. Run the collection:

   ```bash
   newman run Reqres.postman_collection.json -e reqres.postman_environment.json -r cli,html,htmlextra --reporter-html-export newman-report.html --reporter-htmlextra-export newman-htmlextra-report.html
   ```

This will generate both a basic HTML report and a more advanced GUI-style report using `htmlextra`.

---

## 🔸 master Branch — REST-assured Project

### 🛠️ Tech Stack

* Java
* Maven
* REST-assured
* TestNG
* Allure (optional)

### ▶️ How to Run

```bash
git clone https://github.com/mmohamedADEL/ReqresAPiTesting.git
cd ReqresAPiTesting
git checkout master
mvn clean test
```

(Optional to generate Allure report):

```bash
allure serve
```

> ☑️ **Note:** Make sure you're on the correct branch:
>
> * `main` → Postman Collection
> * `master` → REST-assured (Java) automation project

---

## 👤 Author

**Mohamed Adel**  
📧 [LinkedIn](https://www.linkedin.com/in/mohamed-adel-elbaz-79239a272/)  
🧪 ISTQB Certified | Software Tester 
