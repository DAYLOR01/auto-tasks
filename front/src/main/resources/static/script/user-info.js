const usersName = document.getElementById('usersName');
const deactivateUserBtn = document.getElementById('deactivateUser');
const activateUserBtn = document.getElementById('activateUser');
const usersRoles = document.getElementById('usersRoles');
const usersTags = document.getElementById('usersTags');
const editUserOpenModalBtn = document.getElementById('editUserOpenModal');
const newName = document.getElementById('newName');
const newPassword = document.getElementById('newPassword');
const newMainTags = document.getElementById('newMainTags');
const newSecondaryTags = document.getElementById('newSecondaryTags');
const newRoles = document.getElementById('newRoles')
const editUserBtn = document.getElementById('editUser');
const username =  document.getElementById('usersUsername').innerText.toLowerCase();
let editingUser;


document.addEventListener('DOMContentLoaded', function () {
    fillUserInfo();
    editUserOpenModalBtn.addEventListener('click', fillEditUserInfo);
    editUserBtn.addEventListener('click', editUser);
})

const fillUserInfo = () => {
    fetch(`${apiUrl}/users/${username}`, {
        headers: header
    })
        .then(function(value){
            if (value.status === 401 || value.status === 403) {
                window.location.replace(`${baseUrl}/home`)
            }
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            editingUser = output;
            usersName.innerText = output.name;
            if (output.active) {
                deactivateUserBtn.classList.remove('d-none')
                deactivateUserBtn.addEventListener('click', () => {changeActive(false)})
            }
            else {
                activateUserBtn.classList.remove('d-none')
                activateUserBtn.addEventListener('click', () => {changeActive(true)})
            }
            usersRoles.innerHTML = "";
            for (const role of output.roles) {
                usersRoles.innerHTML += `
                    <div class="p-2 flex-fill rounded-3 border border-1 m-1 bg-info bg-opacity-25">
                        ${role.descriptionRU}
                    </div>
                `
            }
            usersTags.innerHTML = ""
            for (const tag of output.tags) {
                usersTags.innerHTML += `
                    <li class="list-group-item list-group-item-${tag.usability === 'ALL'? 'primary' : 'info'}">
                        ${tag.descriptionRU}
                    </li>
                `
            }
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

const changeActive = (isActive) => {
    fetch(`${apiUrl}/users/change-active`, {
        headers: header,
        method: 'PUT',
        body: JSON.stringify({
            username: editingUser.username,
            active: isActive
        })
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            location.reload()
        })
        .catch(function(reason){
            console.log(reason)
        })
}

const fillEditUserInfo = () => {
    if (!editingUser) return;
    newName.value = editingUser.name;
    loadTags('ALL', newMainTags);
    loadTags('USER_ONLY', newSecondaryTags);
    loadRoles();
}

const loadTags = (usability, element) => {
    fetch(`${apiUrl}/tags?usability=${usability}`, {
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
                let selected = Boolean(editingUser.tags.find((el, i, arr) => {return el.id == tag.id}))
                element.innerHTML += `<option ${selected ? 'selected' : ''} value="${tag.id}">${tag.descriptionRU}</option>`
            }
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

const loadRoles = () => {
    fetch(`${apiUrl}/roles`, {
        headers: header
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            newRoles.innerHTML = ""
            for (const role of output) {
                let selected = Boolean(editingUser.roles.find((el, i, arr) => {return el.id === role.id}))
                newRoles.innerHTML += `<option ${selected ? 'selected' : ''} value="${role.id}">${role.descriptionRU}</option>`
            }
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

const editUser = () => {
    let mainTagsArray = Array.from(newMainTags.querySelectorAll("option:checked"),e=>e.value)
    let tagsArray = Array.from(mainTagsArray).concat(
        Array.from(newSecondaryTags.querySelectorAll("option:checked"),e=>e.value)
    );
    let rolesArray = Array.from(newRoles.querySelectorAll("option:checked"),e=>e.value)
    fetch(`${apiUrl}/users`, {
        headers: header,
        method: 'PUT',
        body: JSON.stringify({
            username: editingUser.username,
            name: newName.value,
            password: newPassword.value && newPassword.value.trim() ? newPassword.value : null,
            tags: tagsArray,
            roles: rolesArray
        })
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            window.location.reload();
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}