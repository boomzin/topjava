#MealRestController tests

##Get all meals - method getAll()
Request
```
curl http://10.50.7.55:8081/topjava/rest/meals
```
Response
```json
[{"id":100008,"dateTime":[2020,1,31,20,0],"description":"Ужин","calories":510,"excess":true},{"id":100007,"dateTime":[2020,1,31,13,0],"description":"Обед","calories":1000,"excess":true},{"id":100006,"dateTime":[2020,1,31,10,0],"description":"Завтрак","calories":500,"excess":true},{"id":100005,"dateTime":[2020,1,31,0,0],"description":"Еда на граничное значение","calories":100,"excess":true},{"id":100004,"dateTime":[2020,1,30,20,0],"description":"Ужин","calories":500,"excess":false},{"id":100003,"dateTime":[2020,1,30,13,0],"description":"Обед","calories":1000,"excess":false},{"id":100002,"dateTime":[2020,1,30,10,0],"description":"Завтрак","calories":500,"excess":false}]
```


##Get meal by id - method get(int id)
Request
```
curl http://10.50.7.55:8081/topjava/rest/meals/100006
```
Response
```json
{"id":100006,"dateTime":[2020,1,31,10,0],"description":"Завтрак","calories":500,"time":[10,0],"date":[2020,1,31],"new":false}
```


##Delete meal by id - method delete(int id)
Request
```
curl -X DELETE http://10.50.7.55:8081/topjava/rest/meals/100006
```
Response
```json

```


##Create new meal - method createWithLocation(Meal meal)
Request
```
curl -L -X POST 'http://10.50.7.55:8081/topjava/rest/meals' -H 'Content-Type: application/json' --data-raw '{"dateTime": [2021,1,30,10,0],"description": "New meal","calories": 666}'
```
Response
```json
{"id":100014,"dateTime":[2021,1,30,10,0],"description":"New meal","calories":666,"time":[10,0],"date":[2021,1,30],"new":false}
```


##Update meal by id - method update(Meal meal, int id)
Request
```
curl -H 'Content-Type: application/json' -X PUT -d '{"dateTime":[2020,1,30,10,0],"description":"Завтрак updated","calories":555,"time":[10,0],"date":[2020,1,30]}' http://10.50.7.55:8081/topjava/rest/meals/100002
```
Response
```json

```
Request
```
curl http://10.50.7.55:8081/topjava/rest/meals/100002
```
Response
```json
{"id":100002,"dateTime":[2020,1,30,10,0],"description":"Завтрак updated","calories":555,"time":[10,0],"date":[2020,1,30],"new":false}
```


#Get meal filtered by date and time- method getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime)
Request
```
curl http://10.50.7.55:8081/topjava/rest/meals/filtered?startDate=2020-01-30&startTime=12:00&endDate=2020-01-31&endTime=14:00
```
Response
```json
[{"id":100008,"dateTime":[2020,1,31,20,0],"description":"Ужин","calories":510,"excess":true},{"id":100007,"dateTime":[2020,1,31,13,0],"description":"Обед","calories":1000,"excess":true},{"id":100006,"dateTime":[2020,1,31,10,0],"description":"Завтрак","calories":500,"excess":true},{"id":100005,"dateTime":[2020,1,31,0,0],"description":"Еда на граничное значение","calories":100,"excess":true},{"id":100004,"dateTime":[2020,1,30,20,0],"description":"Ужин","calories":500,"excess":false},{"id":100003,"dateTime":[2020,1,30,13,0],"description":"Обед","calories":1000,"excess":false},{"id":100002,"dateTime":[2020,1,30,10,0],"description":"Завтрак","calories":500,"excess":false}]
```


[comment]: <> (##Get meal by non-existent id - method get&#40;int id&#41;)

[comment]: <> (Request)

[comment]: <> (```)

[comment]: <> (curl http://10.50.7.55:8081/topjava/rest/meals/100015)

[comment]: <> (```)

[comment]: <> (Response)

[comment]: <> (```html)

[comment]: <> (<!doctype html><html lang="ru"><head><title>HTTP Status 500 – Internal Server Error</title><style type="text/css">body {font-family:Tahoma,Arial,sans-serif;} h1, h2, h3, b {color:white;background-color:#525D76;} h1 {font-size:22px;} h2 {font-size:16px;} h3 {font-size:14px;} p {font-size:12px;} a {color:black;} .line {height:1px;background-color:#525D76;border:none;}</style></head><body><h1>HTTP Status 500 – Internal Server Error</h1><hr class="line" /><p><b>Type</b> Exception Report</p><p><b>Message</b> Request processing failed; nested exception is ru.javawebinar.topjava.util.exception.NotFoundException: Not found entity with id=100015</p><p><b>Description</b> The server encountered an unexpected condition that prevented it from fulfilling the request.</p><p><b>Exception</b></p><pre>org.springframework.web.util.NestedServletException: Request processing failed; nested exception is ru.javawebinar.topjava.util.exception.NotFoundException: Not found entity with id=100015)

[comment]: <> (	org.springframework.web.servlet.FrameworkServlet.processRequest&#40;FrameworkServlet.java:1014&#41;)

[comment]: <> (	org.springframework.web.servlet.FrameworkServlet.doGet&#40;FrameworkServlet.java:898&#41;)

[comment]: <> (	javax.servlet.http.HttpServlet.service&#40;HttpServlet.java:626&#41;)

[comment]: <> (	org.springframework.web.servlet.FrameworkServlet.service&#40;FrameworkServlet.java:883&#41;)

[comment]: <> (	javax.servlet.http.HttpServlet.service&#40;HttpServlet.java:733&#41;)

[comment]: <> (	org.apache.tomcat.websocket.server.WsFilter.doFilter&#40;WsFilter.java:53&#41;)

[comment]: <> (	org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal&#40;CharacterEncodingFilter.java:201&#41;)

[comment]: <> (	org.springframework.web.filter.OncePerRequestFilter.doFilter&#40;OncePerRequestFilter.java:119&#41;)

[comment]: <> (</pre><p><b>Root Cause</b></p><pre>ru.javawebinar.topjava.util.exception.NotFoundException: Not found entity with id=100015)

[comment]: <> (	ru.javawebinar.topjava.util.ValidationUtil.checkNotFound&#40;ValidationUtil.java:50&#41;)

[comment]: <> (	ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId&#40;ValidationUtil.java:40&#41;)

[comment]: <> (	ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId&#40;ValidationUtil.java:35&#41;)

[comment]: <> (	ru.javawebinar.topjava.service.MealService.get&#40;MealService.java:26&#41;)

[comment]: <> (	ru.javawebinar.topjava.web.meal.AbstractMealController.get&#40;AbstractMealController.java:29&#41;)

[comment]: <> (	ru.javawebinar.topjava.web.meal.MealRestController.get&#40;MealRestController.java:27&#41;)

[comment]: <> (	java.base&#47;jdk.internal.reflect.NativeMethodAccessorImpl.invoke0&#40;Native Method&#41;)

[comment]: <> (	java.base&#47;jdk.internal.reflect.NativeMethodAccessorImpl.invoke&#40;NativeMethodAccessorImpl.java:78&#41;)

[comment]: <> (	java.base&#47;jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke&#40;DelegatingMethodAccessorImpl.java:43&#41;)

[comment]: <> (	java.base&#47;java.lang.reflect.Method.invoke&#40;Method.java:567&#41;)

[comment]: <> (	org.springframework.web.method.support.InvocableHandlerMethod.doInvoke&#40;InvocableHandlerMethod.java:197&#41;)

[comment]: <> (	org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest&#40;InvocableHandlerMethod.java:141&#41;)

[comment]: <> (	org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle&#40;ServletInvocableHandlerMethod.java:106&#41;)

[comment]: <> (	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod&#40;RequestMappingHandlerAdapter.java:894&#41;)

[comment]: <> (	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal&#40;RequestMappingHandlerAdapter.java:808&#41;)

[comment]: <> (	org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle&#40;AbstractHandlerMethodAdapter.java:87&#41;)

[comment]: <> (	org.springframework.web.servlet.DispatcherServlet.doDispatch&#40;DispatcherServlet.java:1063&#41;)

[comment]: <> (	org.springframework.web.servlet.DispatcherServlet.doService&#40;DispatcherServlet.java:963&#41;)

[comment]: <> (	org.springframework.web.servlet.FrameworkServlet.processRequest&#40;FrameworkServlet.java:1006&#41;)

[comment]: <> (	org.springframework.web.servlet.FrameworkServlet.doGet&#40;FrameworkServlet.java:898&#41;)

[comment]: <> (	javax.servlet.http.HttpServlet.service&#40;HttpServlet.java:626&#41;)

[comment]: <> (	org.springframework.web.servlet.FrameworkServlet.service&#40;FrameworkServlet.java:883&#41;)

[comment]: <> (	javax.servlet.http.HttpServlet.service&#40;HttpServlet.java:733&#41;)

[comment]: <> (	org.apache.tomcat.websocket.server.WsFilter.doFilter&#40;WsFilter.java:53&#41;)

[comment]: <> (	org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal&#40;CharacterEncodingFilter.java:201&#41;)

[comment]: <> (	org.springframework.web.filter.OncePerRequestFilter.doFilter&#40;OncePerRequestFilter.java:119&#41;)

[comment]: <> (</pre><p><b>Note</b> The full stack trace of the root cause is available in the server logs.</p><hr class="line" /><h3>Apache Tomcat/9.0.45</h3></body></html>)

[comment]: <> (```)

