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

## Stage 2/6: Lots of quizzes

**Description**

At this stage, you will improve the web service to create, get and solve lots of quizzes, not just a single one. All quizzes should be stored in the service's memory, without an external storage.

The format of requests and responses will be similar to the first stage, but you will make the API more REST-friendly and extendable. Each of the four possible operations is described below.

To complete this stage, you may read about [some Jackson serializer properties for ignoring fields](https://www.baeldung.com/jackson-ignore-properties-on-serialization). But this is not the only way to solve this stage.

**Create a new quiz**

To create a new quiz, the client needs to send a JSON as the request's body via ``POST`` to `/api/quizzes`. The JSON should contain the four fields: `title` (a string), `text` (a string), options (an array of strings) and `answer` (integer index of the correct option). At this moment, all the keys are optional.

Here is a new JSON quiz as an example:

```JSON
{
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"],
  "answer": 2
}
```
The `answer` equals 2 corresponds to the third item from the `options` array (`"Cup of coffee"`)

The server response is a JSON with four fields: `id`, `title`, `text` and `options`. Here is an example.

```JSON
{
  "id": 1,
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"]
}
````

The `id` field is a generated unique integer identifier for the quiz. Also, the response may or may not include the `answer` field depending on your wishes. This is not very important for this operation.

At this moment, it is admissible if a creation request does not contain some quiz data. In the next stages, we will improve the service to avoid some server errors.

**Get a quiz by id**

To get a quiz by `id`, the client sends the `GET` request to `/api/quizzes/{id}`.

Here is a response example:

```JSON
{
  "id": 1,
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"]
}
```
```
The response must not include the answer field, otherwise, any user will be able to find the correct answer for any quiz.
```
If the specified quiz does not exist, the server should return the `404 (Not found)` status code.

**Get all quizzes**

To get all existing quizzes in the service, the client sends the `GET` request to `/api/quizzes`.

The response contains a JSON array of quizzes like the following:

```JSON
[
  {
    "id": 1,
    "title": "The Java Logo",
    "text": "What is depicted on the Java logo?",
    "options": ["Robot","Tea leaf","Cup of coffee","Bug"]
  },
  {
    "id": 2,
    "title": "The Ultimate Question",
    "text": "What is the answer to the Ultimate Question of Life, the Universe and Everything?",
    "options": ["Everything goes right","42","2+2=4","11011100"]
  }
]
```
```
The response must not include the answer field, otherwise, any user will be able to find the correct answer for any quiz.
```
If there are no quizzes, the service returns an empty JSON array: `[]`.

In both cases, the status code is `200 (OK)`.

**Solving a quiz**

To solve the quiz, the client sends a POST request to `/api/quizzes/{id}/solve` and passes the `answer` parameter in the content. This parameter is the index of a chosen option from `options` array. As before, it starts from zero.

The service returns a JSON with two fields: `success` (`true` or `false`) and `feedback` (just a string). There are three possible responses.

- If the passed answer is correct (e.g., `POST` to `/api/quizzes/1/solve` with content `answer=2`):
```JSON
{"success":true,"feedback":"Congratulations, you're right!"}
```
- If the answer is incorrect (e.g., `POST` to `/api/quizzes/1/solve` with content `answer=1`):
```JSON
{"success":false,"feedback":"Wrong answer! Please, try again."}
```
- If the specified quiz does not exist, the server returns the `404 (Not found)` status code.

You can write any other strings in the `feedback` field, but the names of fields and the `true`/`false` values must match this example.


