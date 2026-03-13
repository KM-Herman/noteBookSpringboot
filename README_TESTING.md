# Testing Guide: Rwanda Administrative Hierarchy & User Linking

This document provides exhaustive, step-by-step instructions and payloads to test every scenario of the Rwanda Administrative Hierarchy implementation.

---

## 1. Setup
Ensure your application is running (`mvn spring-boot:run`) and listening on `http://localhost:8080`.

---

## 2. Phase 1: Administrative Data Import
**Endpoint**: `POST /api/admin/import-locations`

### Scenario 1.1: Success - Full Hierarchical Import
- **Objective**: Import a complete structure from Province down to Village.
- **Payload**:
```json
{
  "City of Kigali": {
    "Nyarugenge": {
      "Nyarugenge": {
        "Kiyovu": ["Ubumwe", "Rugunga"],
        "Agatare": ["Agatare I"]
      }
    }
  },
  "Northern Province": {
    "Musanze": {
      "Muhoza": {
        "Mpenge": ["Mpenge Village A"]
      }
    }
  }
}
```
- **Expected**: `200 OK` and message "Administrative data imported successfully".

### Scenario 1.2: Error - Malformed JSON
- **Objective**: Test system resilience.
- **Payload**: `{"Invalid": ["JSON"}` (missing braces)
- **Expected**: `500 Internal Server Error` with "Failed to import data".

---

## 3. Phase 2: User Creation with Village Linking
**Endpoint**: `POST /api/users`

### Scenario 2.1: Link by Village Name (Happy Path)
- **Objective**: Create a user and link them automatically by searching for a village name.
- **URL**: `http://localhost:8080/api/users?villageCode=Ubumwe`
- **Payload**:
```json
{
  "username": "herman_kigali",
  "email": "herman@kigali.rw",
  "password": "securepassword"
}
```
- **Expected**: User created. The response should show a nested `village` object representing "Ubumwe", which is linked to Cell "Kiyovu", Sector "Nyarugenge", etc.

### Scenario 2.2: Link by Village Code
- **Objective**: Use the unique code (generated during import) to link a user.
- **How to get code**: Perform `GET /api/provinces` and drill down into the village objects to find a `code` (e.g., `MPEN-456`).
- **URL**: `http://localhost:8080/api/users?villageCode=MPEN-456`
- **Payload**:
```json
{
  "username": "musanze_user",
  "email": "musanze@rwanda.rw",
  "password": "securepassword"
}
```
- **Expected**: User created and linked to the specific village for Musanze.

### Scenario 2.3: Non-Existent Village
- **Objective**: Test behavior when an invalid location is provided.
- **URL**: `http://localhost:8080/api/users?villageCode=SomewhereElse`
- **Payload**: Standard user JSON.
- **Expected**: User created with `village: null`.

---

## 4. Phase 3: Data Retrieval & Integrity
**Endpoint**: `GET /api/users/province/{province}`

### Scenario 3.1: Retrieve by Province Name
- **Objective**: Show the power of multi-level joins.
- **URL**: `http://localhost:8080/api/users/province/City of Kigali`
- **Expected**: List of users including `herman_kigali`.

### Scenario 3.2: Relationship Cleanup (Cascading)
- **Objective**: Show that parents manage children.
- **Action**: Identify a province ID via `GET /api/provinces` (e.g., `id: 1`).
- **Endpoint**: `DELETE /api/provinces/1`
- **Expected**: `200 OK`.
- **Verification**: Run `GET /api/provinces` again; the province and ALL its associated districts, sectors, cells, and villages should be gone from the database.

---

## 5. Summary Table for Presentation

| Action | Endpoint | Method | Key Feature to Mention |
| :--- | :--- | :--- | :--- |
| **Import Data** | `/api/admin/import-locations` | `POST` | Recursive Parsing & Auto-Generation of codes. |
| **Create User** | `/api/users?villageCode=...` | `POST` | Automatic Relationship Resolution. |
| **Search Users** | `/api/users/province/{name}` | `GET` | Multi-level SQL JOIN (5+ levels deep). |
| **Delete Province** | `/api/provinces/{id}` | `DELETE` | JPA Cascade Propagation. |
