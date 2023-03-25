# transport-api
- Restful Corporate Transport Application API made using Spring Boot, Spring Security, JWT, MySQL, JPA
- Features abilities for organizations to define bus routes and stops for their designated office locations within a city
- Allows users to book and cancel bus passes of various types for their desired routes

## Rest API

### Auth

| Method | URL           | Description       | Sample JSON |
|--------|---------------|-------------------|-------------|
| POST   | /api/login    | Login             | [JSON](#login) |
| POST   | /api/register | Register new user | [JSON](#signup) |

### User

| Method | URL                          | Description          | Sample JSON |
|--------|------------------------------|----------------------|-------------|
| GET    | /api/user/{username}/me      | Current user details |             |
| GET    | /api/user/{username}/buspass | Get users passes     |             |

### Routes

| Method | URL                         | Description               | Sample JSON |
|--------|-----------------------------|---------------------------|-------------|
| GET    | /api/routes/{routeId}       | Get a route by id         |             |
| POST   | /api/routes                 | Create new route          | [JSON](#create-route) |
| PUT    | /api/routes/{id}            | Update route              | [JSON](#update-route) |
| GET    | /api/routes/{routeId}/stops | Get all stops for a route |             |

### Stops

| Method | URL             | Description     | Sample JSON |
|--------|-----------------|-----------------|-------------|
| GET    | /api/stops/{id} | Get stop by id  |             |
| POST   | /api/stops      | Create new stop | [JSON](#create-stop) |

### Office Location

| Method | URL                                     | Description                           | Sample JSON |
|--------|-----------------------------------------|---------------------------------------|-------------|
| GET    | /api/officeLocation/{id}                | Get office location by id             |             |
| GET    | /api/officeLocation                     | Get all office locations              |             |
| POST   | /api/officeLocation                     | Create new office location            | [JSON](#create-ol) |
| GET    | /api/officeLocation/{locationId}/routes | Get all routes for an office location |             |

### Trips

| Method | URL                    | Description             | Sample JSON |
|--------|------------------------|-------------------------|-------------|
| GET    | /api/trips/{id}        | Get trip by id          |             |
| PATCH  | /api/trips/{id}/status | Update trip status      | [JSON](#update-trip-status) |
| POST   | /api/trips/{id}/verify | Verify trip             | [JSON](#verify-trip) |
| GET    | /api/trips/{id}/users  | Get all users in a trip |             |

### Bus Pass

| Method | URL                     | Description              | Sample JSON |
|--------|-------------------------|--------------------------|-------------|
| POST   | /api/buspass            | Create bus pass          | [JSON](#create-new-pass) |
| GET    | /api/buspass/{id}       | Get bus pass by id       |             |
| GET    | /api/buspass/{id}/trips | Get all trips for a pass |             |


## Sample valid JSON request body

##### <a id='login'> POST /api/login - Login  </a>

```json
{
  "username": "test_u32432",
  "password": "pass"
}
```

##### <a id='signup'> POST /api/register - Register new user </a>

```json
{
  "username": "test_u32432",
  "email": "Domenico.Hansen48@yahoo.com",
  "firstname":"Gina",
  "lastname":"Predovic",
  "mobileNumber":"607-522-7837",
  "password": "pass",
  "address": {
      "houseAddress":"9669 Chase Glen",
      "city":"Domenickchester",
      "state":"Schiller",
      "pincode":"34958"
  }
}
```

##### <a id='create-route'> POST /api/routes - Create new route </a>

```json
{
  "name": "OL5RouteA",
  "officeLocationId": 2,
  "shift": "MORNING",
  "busId": 6
}
```

##### <a id='update-route'> PUT /api/routes/{id} - Update route </a>

```json
{
  "name": "SectorAM",
  "officeLocationId": 1,
  "busId": 9,
  "shift": "MORNING"
}
```

##### <a id='create-ol'> POST /api/officeLocation - Create new office location </a>

```json
{
  "city": "MUMBAI",
  "location": "SSEVEN-B5"
}
```

##### <a id='create-stop'> POST /api/stops - Create new bus stop </a>

```json
{
  "name": "Douglas Rd",
  "city": "MUMBAI",
  "routeId": 14,
  "expectedArrival": "01:12"
}
```

##### <a id='update-trip-status'> PATCH /api/trips/{id}/status - Update trip status </a>

```json
{
    "status": "CANCELLED"
}
```

##### <a id='verify-trip'> POST /api/trips/{id}/verify - Verify trip </a>

```json
{
    "verificationToken": 6900
}
```

##### <a id='create-new-pass'> POST /api/buspass - Create new buspass </a>

```json
{
  "routeId": 14,
  "pickupPointId": 19,
  "officeLocationId": 3,
  "shift": "MORNING",
  "tripType": "BOTH",
  "busPassType": "HYBRID",
  "month": "JULY",
  "selectedDates": ["2023-07-11", "2023-07-14", "2023-07-16"]
}
```
