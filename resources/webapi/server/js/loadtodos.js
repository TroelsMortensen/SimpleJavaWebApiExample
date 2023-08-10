console.log("Fetching stuff")
var xmlHttp = new XMLHttpRequest();
xmlHttp.open("GET", "http://localhost:8001/api/todos", false); // false for synchronous request
xmlHttp.send(null);
var fetchedXML = xmlHttp.responseText; // here I have received my data as XML

var parser = new DOMParser();
var xmlDoc = parser.parseFromString(fetchedXML, "text/xml"); // here I have a DOM of the XML

var todoTags = xmlDoc.getElementsByTagName("todo");

var resultTableHTML = '<table>';
{
    resultTableHTML += '<tr><th>id</th><th>title</th><th>description</th><th>status</th><th>assignee</th></tr>';
    for (let i = 0; i < todoTags.length; i++) {
        resultTableHTML += '<tr>';
        let id = todoTags[i].getElementsByTagName("id")[0].innerHTML;
        let title = todoTags[i].getElementsByTagName("title")[0].innerHTML;
        let description = todoTags[i].getElementsByTagName("description")[0].innerHTML;
        let status = todoTags[i].getElementsByTagName("isCompleted")[0].innerHTML;
        let assignee = todoTags[i].getElementsByTagName("assigneeName")[0].innerHTML;
        resultTableHTML += '<td>' + id + '</td>';
        resultTableHTML += '<td>' + title + '</td>';
        resultTableHTML += '<td>' + description + '</td>';
        resultTableHTML += '<td>' + status + '</td>';
        resultTableHTML += '<td>' + assignee + '</td>';
        resultTableHTML += '</tr>';
    }
}
resultTableHTML += '</table>';

document.write(resultTableHTML);