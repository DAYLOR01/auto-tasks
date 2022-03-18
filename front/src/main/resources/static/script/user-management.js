const tableBody = document.getElementById('tableBody');
const firstPageBtn = document.getElementById('firstPage');
const prevPageBtn = document.getElementById('prevPage');
const pageNumberInput = document.getElementById('pageNumber');
const nextPageBtn = document.getElementById('nextPage');
const lastPageBtn = document.getElementById('lastPage');
const newUserOpenModalBtn = document.getElementById('newUserOpenModal');
const newUsername = document.getElementById('newUsername');
const newName = document.getElementById('newName');
const newPassword = document.getElementById('newPassword');
const newMainTags = document.getElementById('newMainTags');
const newSecondaryTags = document.getElementById('newSecondaryTags');
const newRoles = document.getElementById('newRoles')
const createNewUserBtn = document.getElementById('createNewUser');
let pageSize = 25;
let lastPage;

document.addEventListener('DOMContentLoaded', function () {
    fillTable(1);
    pageNumberInput.addEventListener('change', () => {fillTable(pageNumberInput.value)})
    firstPageBtn.addEventListener('click', () => {fillTable(1)})
    prevPageBtn.addEventListener('click', () => {fillTable(parseInt(pageNumberInput.value) - 1)})
    nextPageBtn.addEventListener('click', () => {fillTable(parseInt(pageNumberInput.value) + 1)})
    lastPageBtn.addEventListener('click', () => {fillTable(lastPage)})
    newUserOpenModalBtn.addEventListener('click', fillNewUserLists)
    createNewUserBtn.addEventListener('click', createNewUser)
})

const fillTable = (pageNumber) => {
    if (pageNumber > lastPage || pageNumber < 1) return;
    pageNumberInput.value = pageNumber
    fetch(`${apiUrl}/users?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
        headers: header
    })
        .then(function(value){
            if (value.status === 401 || value.status === 403) {
                window.location.replace(`${baseUrl}/home`)
            }
            if (value.status !== 200) {
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            lastPage = output.totalPages;
            pageNumberInput.setAttribute('max', output.totalPages);
            let innerContent = ""
            for (const user of output.elements) {
                innerContent += `
                <tr>
                  <th scope="row">${user.id}</th>
                  <td>
                    <a href="/users/${user.username}">${user.username}</a>
                  </td>
                  <td>${user.name}</td>
                  <td>
                  <div class="form-check form-switch">
                    <input ${user.active ? "checked" : ""} class="form-check-input" type="checkbox" role="switch" onclick="changeActive('${user.username}', this)">
                  </div>
                  </td>
                </tr>
                `
            }
            tableBody.innerHTML = innerContent;
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

const changeActive = (username, target) => {
    fetch(`${apiUrl}/users/change-active`, {
        headers: header,
        method: 'PUT',
        body: JSON.stringify({
            username: username,
            active: target.checked
        })
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .catch(function(reason){
            console.log(reason)
        })
}

const fillNewUserLists = () => {
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
                element.innerHTML += `<option value="${tag.id}">${tag.descriptionRU}</option>`
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
                newRoles.innerHTML += `<option value="${role.id}">${role.descriptionRU}</option>`
            }
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

const createNewUser = () => {
    let mainTagsArray = Array.from(newMainTags.querySelectorAll("option:checked"),e=>e.value)
    let tagsArray = Array.from(mainTagsArray).concat(
        Array.from(newSecondaryTags.querySelectorAll("option:checked"),e=>e.value)
    );
    let rolesArray = Array.from(newRoles.querySelectorAll("option:checked"),e=>e.value)
    newUsername.classList.remove('is-invalid')
    newPassword.classList.remove('is-invalid')
    let username = newUsername.value
    let name = newName.value
    let password = newPassword.value
    if (!username || !username.trim()) {
        newUsername.classList.add('is-invalid')
        return;
    }
    if (!password || !password.trim()) {
        newPassword.classList.add('is-invalid')
        return;
    }
    fetch(`${apiUrl}/users`, {
        headers: header,
        method: 'POST',
        body: JSON.stringify({
            username: username.trim(),
            name: name,
            password: password,
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
            window.location.href = `${baseUrl}/users/${output.username}`
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}