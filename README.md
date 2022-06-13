# WebQuizEngine
Web Quiz Engine project from JetBrains Academy in 6 stages

## Stage 1/6: Solving a simple quiz

**About**

On the Internet, you can often find sites where you need to answer questions: educational sites, sites with psychological tests, job search services, or just entertaining sites like web quests. Something they all have in common is that they permit to answer questions (or quizzes) and then see the results.

In this project, you will develop a multi-user web service for creating and solving quizzes using REST API, an embedded database, security, and other technologies. Here we will concentrate on the server side ("engine") without a user interface at all. The project stages are described in terms of the client-server model, where the client can be a browser, the curl tool, a REST client (like postman) or something else.

During the development of the web service, you will probably have to do some Google searching and additional reading. This is a normal situation, just read a few articles when implementing stages.

After you complete this project, you will have a clear understanding of backend development. You'll also know how to combine various modern technologies to get a great result. If you continue the work on the project, you can also develop a web/mobile client for this web service.

**Description**

At the first stage, you need to develop a simple JSON API that always returns the same quiz to be solved. The API should support only two operations: getting the quiz and solving it by passing an answer. Each operation is described in more detail below.

Once the stage is completed, you will have a working web service with a comprehensive API.

To test your API, you may write Spring Boot tests, or use a rest client like postman or the curl tool. GET requests can be tested by accessing the URL in your browser. You can also check your application in the browser using reqbin.

**Get the quiz

The quiz has exactly three fields: title (string) text (string) and options (array). To get the quiz, the client sends the GET request to /api/quiz. The server should return the following JSON structure:

```JSON
{
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"]
}
```

In your API, the names of attributes must be exactly the same (title, text, options), but you can assign any values to them. The quiz should contain four items in the options array. The correct answer must be the third option, but since the indexes start from zero, its index is 2.


There is no need to force your server to respond a JSON with line breaks and additional spaces. This is used only to demonstrate the response in a human-readable format. Actually, your server returns a long single-line JSON: ``` {"title":"The Java Logo","text":"What is depicted on the Java logo?","options":["Robot","Tea leaf","Cup of coffee","Bug"]}. ```


**Solve the quiz**

To solve the quiz, the client needs to pass the ```answer``` parameter using the ```POST``` request to ```/api/quiz``` with content as parameter ```answer``` and value. This parameter is the index of a chosen option from ```options``` array. We suppose that in our service, indexes start from zero.

The server should return JSON with two fields: ```success``` (```true``` or ```false```) and ```feedback``` (just a string). There are two possible responses from the server:

- If the passed answer is correct (```POST``` to ```/api/quiz``` with content ```answer=2```):
```JSON
{"success":true,"feedback":"Congratulations, you're right!"}
```
- If the answer is incorrect (e.g., ```POST``` to ```/api/quiz``` with content ```answer=1```):
```JSON
{"success":false,"feedback":"Wrong answer! Please, try again."}
```
You can write any other strings in the ```feedback``` field, but the names of the fields and the ```true```/```false``` values must match this example.
