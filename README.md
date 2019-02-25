# Weather APP

### Getting started
Date started 23 Feb 2019 14:30 Moscow Time

### TODO 
- Split into microservices(weather service, city service, Zuul, eureka ) 2-3days
- Run it on K8S cluster on GCP :: 0.5– 1 day
- Add CI/CD :: 0.5–1 day
- Add full non-blocking/reactive support see appropriate branch

#Dev plan 
## Stage 1



1. System design(several type of design)
    - API
    - Structure
    - Deployment
    - Flow...
2. Implements API layer
    5. Register city(Existing), Storing in database
        - Controller
        - Model
        - DTO
        - Repository
        - Service 
        - Tests
        - Other services like checking if city exists
    6. Get city weather 
        - Controller 
        - Service - Stage1: external Call API
        -  Repository
        -  Models
        -  DTO
3. Error handling check

## Stage 2

1. API Layer 
    1. City Weather
        1. Add Caching to external requests results: 5 minutes cache
        2. New API call should return data from cache if it is there 


## 	Stage 3



1. Return last weather for all cities in db (Pageable?)
2. Unregister city 
    1. Delete all information about city and it's weather  db, cache
3. XML/JSON depends on header 


## Stage 4



1. Add scheduling in background for updating caches for all cities with actual info
2. Store weather in DB??(already stored on 1st stage?)


## STage 5



1. Dockerize

not forget



1. Javadoc
2. tests
3. Spring + Hibernate or JPA

<table>
  <tr>
   <td>
Resource
   </td>
   <td>POST \
create
   </td>
   <td>GET \
read
   </td>
   <td>PUT
<p>
update
   </td>
   <td>DELETE
   </td>
  </tr>
  <tr>
   <td>/cities
   </td>
   <td>create a new city/ register
   </td>
   <td>––
   </td>
   <td>–––
   </td>
   <td>
   </td>
  </tr>
  <tr>
   <td>/cities/{id}
   </td>
   <td>––
   </td>
   <td>Get weather
   </td>
   <td>––
   </td>
   <td>delete city with all weather info
   </td>
  </tr>
  <tr>
   <td>/cities?
   </td>
   <td>
   </td>
   <td>
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
</table>
