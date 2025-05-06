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
    {
        "name": "John Doe",
        "email": "john.doe@example.com"
    },
    {
        "name": "Jane Smith",
        "email": "jane.smith@example.com"
    },
    {
        "name": "Emily Davis",
        "email": "emily.davis@example.com"
    },
    {
        "name": "Michael Brown",
        "email": "michael.brown@example.com"
    },
    {
        "name": "Sarah Wilson",
        "email": "sarah.wilson@example.com"
    },
    {
        "name": "David Clark",
        "email": "david.clark@example.com"
    },
    {
        "name": "James Harris",
        "email": "james.harris@example.com"
    },
    {
        "name": "Olivia Martin",
        "email": "olivia.martin@example.com"
    },
    {
        "name": "Daniel Lee",
        "email": "daniel.lee@example.com"
    },
    {
        "name": "Sophia Taylor",
        "email": "sophia.taylor@example.com"
    },
    {
        "name": "William Anderson",
        "email": "william.anderson@example.com"
    },
    {
        "name": "Ava Thomas",
        "email": "ava.thomas@example.com"
    },
    {
        "name": "Liam Jackson",
        "email": "liam.jackson@example.com"
    },
    {
        "name": "Charlotte White",
        "email": "charlotte.white@example.com"
    },
    {
        "name": "Benjamin Harris",
        "email": "benjamin.harris@example.com"
    },
    {
        "name": "Amelia Clark",
        "email": "amelia.clark@example.com"
    },
    {
        "name": "Lucas Lewis",
        "email": "lucas.lewis@example.com"
    },
    {
        "name": "Mia Walker",
        "email": "mia.walker@example.com"
    },
    {
        "name": "Alexander Hall",
        "email": "alexander.hall@example.com"
    },
    {
        "name": "Isabella Allen",
        "email": "isabella.allen@example.com"
    },
    {
        "name": "Ethan Young",
        "email": "ethan.young@example.com"
    }
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