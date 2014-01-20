## Jersey Spring REST Service - Example 4
Author: Niclas Tegnér

This is an example of a REST service with caching by the [Ehcache framework][1]. 

To illustrate that caching is in place, the REST service is using a DAO that 
takes at least 3 seconds to fetch a record. This means that if a response of a 
request takes 3 seconds or more it's the REST service that responds, if it takes 
much less time it's the cache framework that responds. 

For more details about cache size, time to live etc, please take a look at the 
ehcache.xml configuration.

### Feature highlights
- Rest service with full CRUD support and caching enabled.
- Access restricted to HTTP Basic Authentication.
- Spring Framework 4.0.0
- Spring Security 3.2.0
- Jersey 1.18
- Ehcache 2.8.0
- Classic spring xml configuration.

### Testing the REST service

Get the same fruit twice. The first request should take about 3 seconds, the second request should take a couple of milliseconds:

```
curl -i -u "kim:H4rd2Gu3ss" -H "Accept: application/json" http://localhost:8084/jersey-spring-example4/fruit/1
curl -i -u "kim:H4rd2Gu3ss" -H "Accept: application/json" http://localhost:8084/jersey-spring-example4/fruit/1
```

Get - Update - Get. Both get methods should take about 3 seconds because an update will evict a cached fruit item.

```
curl -i -u "kim:H4rd2Gu3ss" -H "Accept: application/json" http://localhost:8084/jersey-spring-example4/fruit/1
curl -i -u "kim:H4rd2Gu3ss" -X PUT -H "Content-Type: application/json" -d '{"id":1,"name":"Grape","description":"Small delicious orbs"}' http://localhost:8084/jersey-spring-example4/fruit
curl -i -u "kim:H4rd2Gu3ss" -H "Accept: application/json" http://localhost:8084/jersey-spring-example4/fruit/1
```

[1]: http://ehcache.org/