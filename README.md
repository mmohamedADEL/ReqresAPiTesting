# Reqres API Testing

This repository contains API testing projects for the [Reqres](https://reqres.in/) RESTful service. It includes both manual and automated API testing examples.

---

## 📁 Branches

- `main`: Postman collection for manual testing
-  [master](https://github.com/mmohamedADEL/ReqresAPiTesting/tree/master): REST-assured (Java) project for automated API testing

---

## 🔹 main Branch — Postman Collection

### 📄 File

- `Reqres.postman_collection.json`: Postman requests for testing login, user data, create/update/delete, and delayed responses.

### ▶️ How to Use

1. Install [Postman](https://www.postman.com/)
2. Import the `Reqres.postman_collection.json` file
3. Run requests individually or with the Collection Runner

---

## 🔸 master Branch — REST-assured Project

### 🧰 Tech Stack

- Java
- Maven
- REST-assured
- TestNG
- Allure (optional)

### ▶️ How to Run

```bash
git clone https://github.com/mmohamedADEL/ReqresAPiTesting.git
cd ReqresAPiTesting
git checkout master
mvn clean test
```

(Optional for reports):
```bash
 allure serve
```



