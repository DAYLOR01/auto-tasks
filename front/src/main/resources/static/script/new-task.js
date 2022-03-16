const taskHeader = document.getElementById('taskHeader')
const taskText = document.getElementById('taskText')
const mainTags = document.getElementById('mainTags')
const taskSecondaryTags = document.getElementById('taskSecondaryTags')
const assigneeSecondaryTags = document.getElementById('assigneeSecondaryTags')
const assigneeRole = document.getElementById('assigneeRole')
const autoChooseAssignee = document.getElementById('autoChooseAssignee')
const chooseAssignee = document.getElementById('chooseAssignee')
const saveTaskBtn = document.getElementById('saveTask')
const inspirationDate = document.getElementById('inspirationDate')
const datepicker = new Datepicker(inspirationDate, {
    'format': 'yyyy.mm.dd',
    title: "Выберите дату"
});

document.addEventListener('DOMContentLoaded', function () {
    loadTags('/main', mainTags);
    loadTags('?usability=CARDS_ONLY', taskSecondaryTags);
    loadTags('?usability=USER_ONLY', assigneeSecondaryTags);
    loadRoles();
    mainTags.addEventListener('change', loadUsersByTagsAndRole)
    assigneeSecondaryTags.addEventListener('change', loadUsersByTagsAndRole)
    assigneeRole.addEventListener('change', loadUsersByTagsAndRole)
    saveTaskBtn.addEventListener('click', saveTask)
})

const loadTags = (urlEnd, element) => {
    fetch(`${apiUrl}/tags${urlEnd}`, {
        headers: header
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            element.innerHTML = ""
            for (const tag of output) {
                element.innerHTML += `<option value="${tag.id}">${tag.descriptionRU}</option>`
            }
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

const loadRoles = () => {
    fetch(`${apiUrl}/roles/less-than-mine`, {
        headers: header
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            assigneeRole.innerHTML = ""
            for (const role of output) {
                assigneeRole.innerHTML += `<option value="${role.id}">${role.descriptionRU}</option>`
            }
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

const loadUsersByTagsAndRole = () => {
    if (autoChooseAssignee.checked)
        return;
    let mainTagsArray = Array.from(mainTags.querySelectorAll("option:checked"),e=>e.value)
    if (mainTagsArray.length === 0)
        return;
    let tags = Array.from(mainTagsArray);
    tags.concat(Array.from(assigneeSecondaryTags.querySelectorAll("option:checked"),e=>e.value));
    let role = assigneeRole.querySelector("option:checked").value
    let queryParams = `tagIds=${tags.join()}&roleId=${role}`
    fetch(`${apiUrl}/users/least-loaded?${queryParams}`, {
        headers: header
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            chooseAssignee.innerHTML = ""
            for (const user of output) {
                chooseAssignee.innerHTML += `<option value="${user.username}">${user.name}</option>`
            }
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

const saveTask = () => {
    taskHeader.classList.remove('is-invalid')
    mainTags.classList.remove('is-invalid')
    chooseAssignee.classList.remove('is-invalid')
    let headerValue = taskHeader.value;
    let text = taskText.value;
    let inspDate = inspirationDate.value ? new Date(inspirationDate.value) : null;
    let mainTagsArray = Array.from(mainTags.querySelectorAll("option:checked"),e=>e.value)
    let taskTags = Array.from(mainTagsArray).concat(
        Array.from(taskSecondaryTags.querySelectorAll("option:checked"),e=>e.value)
    );
    let assigneeTags = Array.from(mainTagsArray).concat(
        Array.from(assigneeSecondaryTags.querySelectorAll("option:checked"),e=>e.value)
    );
    let role = assigneeRole.querySelector("option:checked").value;
    let autoAssign = autoChooseAssignee.checked;
    let assignee = chooseAssignee.querySelector("option:checked")?.value;
    if(!(headerValue && headerValue.trim())) {
        taskHeader.classList.add('is-invalid')
        return;
    }
    if(mainTagsArray.length === 0) {
        mainTags.classList.add('is-invalid')
        return;
    }
    if(!(autoAssign || assignee)) {
        chooseAssignee.classList.add('is-invalid')
        return;
    }
    fetch(`${apiUrl}/tasks`, {
        method: 'POST',
        headers: header,
        body: JSON.stringify({
            header: headerValue,
            text: text,
            autoAssignUser: autoAssign,
            assignedUserTagIds: assigneeTags,
            assignedUserRole: role,
            assignedUser: autoAssign ? null : assignee,
            tagIds: taskTags,
            inspirationDate: inspDate ? [inspDate.getFullYear(), inspDate.getMonth()+1, inspDate.getDate()] : null
        })
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            window.location.replace(`${baseUrl}/tasks?id=${output.id}`)
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}