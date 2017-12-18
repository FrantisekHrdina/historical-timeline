# Historical Timeline REST API


## Event entity operations

### Get all available events

```
curl -i -X GET http://localhost:8080/pa165/events
```

### Get specific event by ID

```
curl -i -X GET http://localhost:8080/pa165/events/1
```

### Delete event by given ID

```
curl -i -X DELETE http://localhost:8080/pa165/events/1
```

### Create new event 

```
curl -X POST -i -H "Content-Type: application/json" --data 
'{"name":"Test","date":"2017-12-12","location":"Brno","description":"some desc"}' 
http://localhost:8080/pa165/events/create
```

### Update event

```
curl -X PUT -i -H "Content-Type: application/json" --data '{"id":"1","name":"UpdatedName", "date":"2017-11-11"}' http://localhost:8080/pa165/events/1
```


