
2) TODO:

-insert a DTO layer between rest resource and the app. In the DTO layer we will put
all necessary filed to be send to the app layer in the json format.



- create the following use case starting from the app layer (e.g. suppose what we need in the app layer)
V A.1) Customer signup
  POST /customer/signup (email, password) return CustomerDTO with TokenDTO

V A.2) Customer login
  GET /customer/login (email, password) return CustomerDTO with TokenDTO and List of RestoDTO

V A.3) Customer update 
  POST /customer/update (email, password, token)

B.1) Provider signup
B.2) Provider login
B.3) Provider Resto's customer update
B.4) Provider update 
 
C.1) Token management (es. invalidate)
 
1) for unit test:
add appengine-api-stubs-1.9.18.jar to classpath
add appengine-testing-1.9.18.jar to classpath
