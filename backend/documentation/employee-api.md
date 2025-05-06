 **`curl` commands** for the **EmployeeApiController** you shared earlier.  

---

# 📋 `curl` Commands for `EmployeeApiController`

### 1. ➡️ Fetch all employees (GET `/api/employees`)

```bash
curl -X GET http://localhost:8080/api/employees
```

---

### 2. ➡️ Create a new employee (POST `/api/employees`)

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com"
}'
```

---

### 2.1. ➡️ Create bulk employees (POST `/api/employees/bulk`)

```bash
curl --location 'http://localhost:8080/api/employees/bulk' \
--header 'Content-Type: application/json' \
--data-raw '[
  { "name": "John Doe", "email": "john.doe@example.com", "department": "HR" },
  { "name": "Jane Smith", "email": "jane.smith@example.com", "department": "Finance" },
  { "name": "Michael Johnson", "email": "michael.johnson@example.com", "department": "IT" },
  { "name": "Emily Davis", "email": "emily.davis@example.com", "department": "Marketing" },
  { "name": "William Brown", "email": "william.brown@example.com", "department": "Operations" },
  { "name": "Olivia Wilson", "email": "olivia.wilson@example.com", "department": "Legal" },
  { "name": "James Taylor", "email": "james.taylor@example.com", "department": "IT" },
  { "name": "Sophia Martinez", "email": "sophia.martinez@example.com", "department": "HR" },
  { "name": "Benjamin Anderson", "email": "benjamin.anderson@example.com", "department": "Finance" },
  { "name": "Isabella Thomas", "email": "isabella.thomas@example.com", "department": "Marketing" },
  { "name": "Jacob Jackson", "email": "jacob.jackson@example.com", "department": "Operations" },
  { "name": "Mia White", "email": "mia.white@example.com", "department": "IT" },
  { "name": "Daniel Harris", "email": "daniel.harris@example.com", "department": "Finance" },
  { "name": "Charlotte Martin", "email": "charlotte.martin@example.com", "department": "HR" },
  { "name": "Logan Thompson", "email": "logan.thompson@example.com", "department": "Legal" },
  { "name": "Amelia Garcia", "email": "amelia.garcia@example.com", "department": "Marketing" },
  { "name": "Alexander Clark", "email": "alexander.clark@example.com", "department": "IT" },
  { "name": "Evelyn Rodriguez", "email": "evelyn.rodriguez@example.com", "department": "Finance" },
  { "name": "Matthew Lewis", "email": "matthew.lewis@example.com", "department": "HR" },
  { "name": "Abigail Lee", "email": "abigail.lee@example.com", "department": "Operations" },
  { "name": "Henry Walker", "email": "henry.walker@example.com", "department": "Legal" },
  { "name": "Harper Hall", "email": "harper.hall@example.com", "department": "Marketing" },
  { "name": "Sebastian Allen", "email": "sebastian.allen@example.com", "department": "Finance" },
  { "name": "Avery Young", "email": "avery.young@example.com", "department": "HR" },
  { "name": "David Hernandez", "email": "david.hernandez@example.com", "department": "IT" },
  { "name": "Ella King", "email": "ella.king@example.com", "department": "Marketing" },
  { "name": "Joseph Wright", "email": "joseph.wright@example.com", "department": "Operations" },
  { "name": "Scarlett Lopez", "email": "scarlett.lopez@example.com", "department": "Legal" },
  { "name": "Samuel Scott", "email": "samuel.scott@example.com", "department": "Finance" },
  { "name": "Luna Green", "email": "luna.green@example.com", "department": "HR" }
]

'
```

---

### 3. ➡️ Get a specific employee by ID (GET `/api/employees/{id}`)

```bash
curl -X GET http://localhost:8080/api/employees/1
```
*(replace `1` with the actual employee ID)*

---


### 3.1 ➡️ Pagination and Sorting in listAll() API

```bash
curl -X GET "http://localhost:8080/api/employees?page=0&size=5&sort=name,asc" -H "Content-Type: application/json"

```

---

### 4. ➡️ Update an employee by ID (PUT `/api/employees/{id}`)

```bash
curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "email": "john.smith@example.com"
}'
```
*(replace `1` with the actual employee ID)*

---

### 5. ➡️ Delete an employee by ID (DELETE `/api/employees/{id}`)

```bash
curl -X DELETE http://localhost:8080/api/employees/1
```
*(replace `1` with the actual employee ID)*

Here are the example curl commands for soft delete and hard delete using your updated DELETE /api/employees/{id} API:

---

###  Soft Delete (Mark as INACTIVE)

This is the default behavior (hardDelete=false):

```bash
curl -X DELETE http://localhost:8080/api/employees/10
```

Or explicitly:

```bash
curl -X DELETE "http://localhost:8080/api/employees/10?hardDelete=false"
```

📝 This will set the employee's status to INACTIVE without removing the record from the database.

---

###  Hard Delete (Permanently Delete)

This deletes the record from the database:

```bash
curl -X DELETE "http://localhost:8080/api/employees/10?hardDelete=true"
```


---

### 6.  Search by name or email (query = "Jo")

```bash
curl -X GET "http://localhost:8080/api/employees/search?query=Jo"
```

This will return employees like:

```json
[
  { "id": 1, "name": "John Doe", "email": "john@example.com" },
  { "id": 2, "name": "Joseph Smith", "email": "joe.smith@corp.com" }
]
```

---

### ✅ With pagination (optional, if supported):

```bash
curl -X GET "http://localhost:8080/api/employees/search?query=Jo&page=0&size=5"
```

This would return paged results like:

```json
{
  "content": [
    { "id": 1, "name": "John Doe", "email": "john@example.com" },
    { "id": 2, "name": "Joseph Smith", "email": "joe.smith@corp.com" }
  ],
  "totalElements": 2,
  "totalPages": 1,
  "number": 0,
  "size": 5
}
```

---
Here are the curl commands to test your paginated filter API for employees by department and status:

---

### 6. Filter Employees by Department and Status (Paged)

Basic curl request:

```bash
curl -X GET "http://localhost:8080/api/employees/filter?department=HR&status=ACTIVE&page=0&size=5"
```

💡 Explanation of query parameters:

* department=HR → Filter employees in the “HR” department
* status=ACTIVE → Filter only active employees
* page=0 → First page (0-based index)
* size=5 → Return 5 employees per page

---

### 🔒 If your API requires authentication (e.g., Bearer token):

```bash
curl -X GET "http://localhost:8080/api/employees/filter?department=HR&status=ACTIVE&page=0&size=5" \
  -H "Authorization: Bearer <your_token_here>"
```

---

Expected response (example):

```json
{
  "content": [
    { "id": 1, "name": "Alice HR", "email": "alice@company.com" },
    { "id": 2, "name": "Bob HR", "email": "bob@company.com" }
  ],
  "totalElements": 10,
  "totalPages": 2,
  "number": 0,
  "size": 5
}
```


---

### 8. Filter Employees by Department and Status (Paged)

Basic curl request:

```bash
curl -X GET "http://localhost:8080/api/employees/filter?department=HR&status=ACTIVE&page=0&size=5"
```

💡 Explanation of query parameters:

* department=HR → Filter employees in the “HR” department
* status=ACTIVE → Filter only active employees
* page=0 → First page (0-based index)
* size=5 → Return 5 employees per page

---

