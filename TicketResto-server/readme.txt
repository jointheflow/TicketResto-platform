
2) TODO:

V insert a DTO layer between rest resource and the app. In the DTO layer we will put
all necessary field to be send to the app layer in the json format.



- create the following use case starting from the app layer (e.g. suppose what we need in the app layer)
V A.1) Customer signup
  POST /customer/signup (email, password) return CustomerDTO with TokenDTO

V A.2) Customer login
  GET /customer/login (email, password) return CustomerDTO with TokenDTO and List of RestoDTO

V A.3) Customer update 
  POST /customer/update (id, email, password, token)

V B.1) Provider signup
  POST /provider/signup (email, password, name)
  
B.2) Provider login

V B.3) Provider Resto's customer update
	POST /provider/resto/update (customerId, providerId, providerToken, providerEmail, restoValue)
  B.3.1) check dto return, complete test
  
B.4) Provider update 
 
C.1) Token management (es. invalidate)
 
1) for unit test:
add appengine-api-stubs-1.9.18.jar to classpath
add appengine-testing-1.9.18.jar to classpath
