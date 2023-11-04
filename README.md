# Dat3-Startcode
My startcode for the 3rd semester exam

**Step-by-step guide to exam**
* [Step 1](#step-1) - Copy the code into your own repository. 
Make sure your artifact is named correctly. It should just be named dat3
* [Step 2](#step-2) - If the assignment has a database, create it and add the connection info to the POM file
* [Step 3](#step-3) - Make the persistence layer
* [Step 4](#step-4) - Test your persistence layer. Do not use main for this. Use a test class
* [Step 5](#step-5) - Make the REST API. DON'T SCREW YOURSELF BY MAKING AN UPDATE METHOD. IT'S NOT PART OF THE ASSIGNMENT
* [Step 6](#step-6) - Test your REST API. Do not use main for this. Use a test class.

If a step isn't a part of the assignment, you can skip it. Otherwise, use the steps as a guide to what you need to do when.

Add all the theory questions below and delete everything above including this line, as it is just a guide:

## 1.5 Create a dev.http file and test the endpoints. Copy the output to your README.md file to document
that the endpoints are working as expected.

* Create plant:
  HTTP/1.1 200 OK
  Date: Sat, 04 Nov 2023 12:14:54 GMT
  Content-Type: application/json
  Content-Length: 82

Response file saved.
> 2023-11-04T131454.200.json
{
"id": 1,
"plantType": "PlantType1",
"plantName": "Plant 1",
"price": 10.0,
"height": 10.0
}

Response code: 200 (OK); Time: 172ms (172 ms); Content length: 82 bytes (82 B)

* Get All:
HTTP/1.1 200 OK
Date: Sat, 04 Nov 2023 12:14:54 GMT
Content-Type: application/json
Content-Length: 105

Response file saved.
> 2023-11-04T131455.200.json
[
{
"id": 1,
"plantType": "PlantType1",
"plantName": "Plant 1",
"price": 10.0,
"height": 10.0,
"reseller_plants": []
}
]
Response code: 200 (OK); Time: 341ms (341 ms); Content length: 105 bytes (105 B)

* Get by id:
  HTTP/1.1 200 OK
  Date: Sat, 04 Nov 2023 12:14:55 GMT
  Content-Type: application/json
  Content-Length: 103

Response file saved.
> 2023-11-04T131455-1.200.json
{
"id": 1,
"plantType": "PlantType1",
"plantName": "Plant 1",
"price": 10.0,
"height": 10.0,
"reseller_plants": []
}

Response code: 200 (OK); Time: 28ms (28 ms); Content length: 103 bytes (103 B)

* Get by type:
  HTTP/1.1 200 OK
  Date: Sat, 04 Nov 2023 12:14:55 GMT
  Content-Type: application/json
  Content-Length: 105

Response file saved.
> 2023-11-04T131455-2.200.json
[
{
"id": 1,
"plantType": "PlantType1",
"plantName": "Plant 1",
"price": 10.0,
"height": 10.0,
"reseller_plants": []
}
]

Response code: 200 (OK); Time: 53ms (53 ms); Content length: 105 bytes (105 B)

## 2.1:
| HTTP method | REST Ressource            | Exceptions and status(es)                     |
|-------------|---------------------------|-----------------------------------------------|
| GET         | `/api/plants`             | 500 (Exception) or 200                        |
| GET         | `/api/plants/{id}`        | 500 (Exception), 404 (NotFound) or 200        |
| GET         | `/api/plants/type/{type}` | 500 (Exception), 404 (NotFound) or 200        |
| POST        | `/api/plants`             | 500 (Exception), 400 (IllegalArgument) or 201 |

## 3.4 Please note in your README.md file which programming paradigm the stream API is inspired by.
The stream API is inspired by the functional programming paradigm.

## 5.4: Please describe in you own words the main differences between regular unit-tests and tests done in this task in your README.md file.
The tests done in task 5 is integration tests. I am testing on the entire database system, rather than just a part of the logic layer.

## 6.5: Please describe in your own words why testing REST endpoints is different from the tests you did in Task 5. Write you answer in your README.md file.
In the tests in task 6 I am testing on the REST API, which is integrated with the database. In task 5, I am only testing on the database layer.
Adding the API to the testing means that I am testing on the entire system now

