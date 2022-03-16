const taskAuthor = document.getElementById('taskAuthor');
const taskAssignDate = document.getElementById('taskAssignDate');
const taskHeader = document.getElementById('taskHeader');
const taskText = document.getElementById('taskText');
const taskTags = document.getElementById('taskTags');
const taskHistory = document.getElementById('taskHistory');
const taskStatus = document.getElementById('taskStatus');
const taskAssignedUser = document.getElementById('taskAssignedUser');
const taskInspirationDate = document.getElementById('taskInspirationDate');
const taskCompletionDate = document.getElementById('taskCompletionDate');
const taskId = (new URLSearchParams(window.location.search)).get('id');
let currentStatus;

document.addEventListener('DOMContentLoaded', function () {
    loadTask();
    taskStatus.addEventListener('change', changeStatus);
})

const loadTask = () => {
    fetch(`${apiUrl}/tasks?id=${taskId}`, {
        headers: header
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            taskAuthor.innerText = output.authorUser.name;
            taskAssignDate.innerText = `${output.assignDate[0]}.${output.assignDate[1]}.${output.assignDate[2]}`
            taskHeader.innerText = output.header
            taskText.innerText = output.text
            taskTags.innerHTML = ""
            for (const tag of output.tags) {
                taskTags.innerHTML += `
                <li class="list-group-item list-group-item-${tag.usability === 'ALL'? 'primary' : 'info'}">
                    ${tag.descriptionRU}
                </li>
                `
            }
            // taskHistory
            taskStatus.querySelector(`option[value='${output.status}']`).setAttribute('selected', 'selected')
            currentStatus = output.status;
            taskAssignedUser.innerText = output.assignedUser.name
            taskInspirationDate.innerText = output.inspirationDate ? `${output.inspirationDate[0]}.${output.inspirationDate[1]}.${output.inspirationDate[2]}` : "Нет"
            taskCompletionDate.innerText = output.completionDate ? `${output.completionDate[0]}.${output.completionDate[1]}.${output.completionDate[2]}` : "Нет"
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

const changeStatus = () => {
    let newStatus = taskStatus.querySelector("option:checked").value
    if (!newStatus || newStatus === currentStatus)
        return;
    fetch(`${apiUrl}/tasks/change-status`, {
        headers: header,
        method: 'PUT',
        body: JSON.stringify({
            taskId: taskId,
            newStatus: newStatus
        })
    })
        .then(function (value) {
            if (value.status !== 200) {
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function (output) {
            //loadHistory
        })
        .catch(function (reason) {
            console.log(reason)
        })
}