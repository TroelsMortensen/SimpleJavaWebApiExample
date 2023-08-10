console.log("Fetching stuff")
var xmlHttp = new XMLHttpRequest();
xmlHttp.open("GET", "http://localhost:8001/todos", false); // false for synchronous request
xmlHttp.send(null);
var result = xmlHttp.responseText;
document.write('<h3>' + result + '</h3>')