# transport-api
- Restful Corporate Transport Application API made using Spring Boot, Spring Security, JWT, MySQL, JPA
- Features abilities for organizations to define bus routes and stops for their designated office locations within a city
- Allows users to book and cancel bus passes of various types for their desired routes

## Sample valid JSON request body

POST /api/login - Login 

```
{
  "username": "test_u32432",
  "password": "pass"
}
```

POST /api/register - Register new user

```
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

POST /api/routes - Create new route

```
{
  "name": "OL5RouteA",
  "officeLocationId": 2,
  "shift": "MORNING",
  "busId": 6
}
```

PUT /api/routes/{id} - Update route

```
{
  "name": "SectorAM",
  "officeLocationId": 1,
  "busId": 9,
  "shift": "MORNING"
}
```

POST /api/officeLocation - Create new office location

```
{
  "city": "MUMBAI",
  "location": "SSEVEN-B5"
}
```

POST /api/stops - Create new bus stop

```
{
  "name": "Douglas Rd",
  "city": "MUMBAI",
  "routeId": 14,
  "expectedArrival": "01:12"
}
```

PATCH /api/trips/{id}/status - Update trip status

```
{
    "status": "CANCELLED"
}
```

POST /api/trips/{id}/verify - Verify trip

```
{
    "verificationToken": 6900
}
```

POST /api/buspass - Create new buspass

```
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
