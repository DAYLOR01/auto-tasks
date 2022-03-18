const taskAuthor = document.getElementById('taskAuthor');
const taskAssignDate = document.getElementById('taskAssignDate');
const taskHeader = document.getElementById('taskHeader');
const taskText = document.getElementById('taskText');
const taskTags = document.getElementById('taskTags');
const taskHistory = document.getElementById('taskHistory');
const commentaryTxtArea = document.getElementById('commentary');
const saveCommentaryBtn = document.getElementById('saveCommentary');
const taskStatus = document.getElementById('taskStatus');
const taskAssignedUser = document.getElementById('taskAssignedUser');
const taskInspirationDate = document.getElementById('taskInspirationDate');
const taskCompletionDate = document.getElementById('taskCompletionDate');
const taskId = (new URLSearchParams(window.location.search)).get('id');
const taskStatusMap = {
    OPEN: "Открыта",
    IN_PROGRESS: "В процессе",
    UNDER_REVIEW: "На проверке",
    COMPLETED: "Завершена",
    CLOSED: "Отменена"
}
let currentStatus;

document.addEventListener('DOMContentLoaded', function () {
    loadTask();
    taskStatus.addEventListener('change', changeStatus);
    saveCommentaryBtn.addEventListener('click', saveCommentary)
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
            taskAssignDate.innerText = new Date(output.assignDate).toLocaleDateString()
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
            taskStatus.querySelector(`option[value='${output.status}']`).setAttribute('selected', 'selected')
            currentStatus = output.status;
            taskAssignedUser.innerText = output.assignedUser.name
            taskInspirationDate.innerText = output.inspirationDate ? new Date(output.inspirationDate).toLocaleDateString() : "Нет"
            taskCompletionDate.innerText = output.completionDate ? new Date(output.completionDate).toLocaleDateString() : "Нет"
            reloadHistory();
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
            reloadHistory();
            if (newStatus === "COMPLETED" || newStatus === "CLOSED")
                taskCompletionDate.innerText = new Date().toLocaleDateString()
        })
        .catch(function (reason) {
            console.log(reason)
        })
}

const saveCommentary = () => {
    commentaryTxtArea.classList.remove('is-invalid')
    let commentaryText = commentaryTxtArea.value
    if (!commentaryText || !commentaryText.trim()) {
        commentaryTxtArea.classList.add('is-invalid')
        return;
    }
    fetch(`${apiUrl}/tasks/history/add-commentary`, {
        headers: header,
        method: 'POST',
        body: JSON.stringify({
            taskId: taskId,
            commentary: commentaryText
        })
    })
        .then(function (value) {
            if (value.status !== 200) {
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function (output) {
            reloadHistory();
            commentaryTxtArea.value = ""
        })
        .catch(function (reason) {
            console.log(reason)
        })
}

const reloadHistory = () => {
    fetch(`${apiUrl}/tasks/history?taskId=${taskId}`, {
        headers: header,
    })
        .then(function (value) {
            if (value.status !== 200) {
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function (output) {
            taskHistory.innerHTML = "";
            for (const historyEl of output) {
                let upperText, lowerText, dateTime;
                switch (historyEl.type) {
                    case "CHANGE_STATUS":
                        upperText = `${historyEl.createdBy.name} изменил статус задачи:`
                        lowerText = taskStatusMap[historyEl.value]
                        break;
                    case "ADD_COMMENTARY":
                        upperText = `${historyEl.createdBy.name} добавил комментарий:`
                        lowerText = `${historyEl.value.replace('\n','<br/>')}`
                        break;
                }
                dateTime = new Date(historyEl.createdAt).toLocaleString()
                taskHistory.innerHTML += `
                <div class="bg-light p-3 mb-2 rounded">
                    <p class="fs-6 mb-1">${upperText}</p>
                    <p class="fs-5 mb-1">${lowerText}</p>
                    <p class="text-muted text-end mb-0">${dateTime}</p>
                </div>
                `
            }
        })
        .catch(function (reason) {
            console.log(reason)
        })
}