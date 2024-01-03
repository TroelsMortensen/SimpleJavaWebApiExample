# SimpleJavaWebApiExample
A minimal Web App. The server is written in Java. The front end is basic html, css, and JavaScript.

Just run the server throught the Main class. The console should output a clickable link to open up the app in the browser.

I have two branches, the "old" have the Web Api also serve the pages, and uses XML for data transfer. The newer has the front end as a stand-alone, and uses JSON.

You navigate around between three pages through the links.

I use the library [xstream](https://x-stream.github.io/), to serialize data to XML, before sending it to the client.\
A better approach would be JSON, which might happen some day.

