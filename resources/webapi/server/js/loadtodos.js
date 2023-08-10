console.log("Fetching stuff")
var xmlHttp = new XMLHttpRequest();
xmlHttp.open("GET", "http://localhost:8001/api/todos", false); // false for synchronous request
xmlHttp.send(null);
var fetchedXML = xmlHttp.responseText;

document.write('<h3>' + fetchedXML + '</h3>')