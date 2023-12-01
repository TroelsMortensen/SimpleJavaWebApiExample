const xhr = new XMLHttpRequest();
xhr.open("GET", "http://localhost:8001/api/todos", false); // false to make synchronous request
xhr.send(null);

const fetchedJSON = xhr.responseText; // here I have received my data as JSON
const todos = JSON.parse(fetchedJSON)

let resultTableHTML = '<table>';
{
    resultTableHTML += '<tr><th>id</th><th>title</th><th>description</th><th>status</th><th>assignee</th></tr>';
    for (let i = 0; i < todos.length; i++) {
        const todo = todos[i];
        resultTableHTML += '<tr>';
        resultTableHTML += '<td>' + todo.id + '</td>';
        resultTableHTML += '<td>' + todo.title + '</td>';
        resultTableHTML += '<td>' + todo.description + '</td>';
        resultTableHTML += '<td>' + todo.isCompleted + '</td>';
        resultTableHTML += '<td>' + todo.assigneeName + '</td>';
        resultTableHTML += '</tr>';
    }
}
resultTableHTML += '</table>';

document.getElementById("todos-table").innerHTML = resultTableHTML;