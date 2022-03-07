const myName = document.getElementById('myName');
const myUsername = document.getElementById('myUsername');
const myRoles = document.getElementById('myRoles');
const myTags = document.getElementById('myTags');
const changePasswordBtn = document.getElementById('changePassword')

document.addEventListener('DOMContentLoaded', async function () {
    let user = await getAuthInfo();
    fillUserInfo(user);
    changePasswordBtn.addEventListener('click', changePassword)
})

const fillUserInfo = (user) => {
    myName.innerText = user.name
    myUsername.innerText = user.username
    myRoles.innerHTML = ""
    for (const role of user.roles) {
        myRoles.innerHTML += `
            <div class="p-2 flex-fill rounded-3 border border-1 m-1 bg-info bg-opacity-25">
                ${role.descriptionRU}
            </div>
        `
    }
    myTags.innerHTML = ""
    for (const tag of user.tags) {
        myTags.innerHTML += `
            <li class="list-group-item list-group-item-${tag.usability === 'ALL'? 'primary' : 'info'}">
                ${tag.descriptionRU}
            </li>
        `
    }
}

const changePassword = () => {
    let oldPassword = document.getElementById('oldPassword')
    let newPassword = document.getElementById('newPassword')
    let confirmPassword = document.getElementById('confirmPassword')
    oldPassword.classList.remove('is-invalid')
    newPassword.classList.remove('is-invalid')
    confirmPassword.classList.remove('is-invalid')
    if(newPassword.value && newPassword.value.trim()) {
        if(newPassword.value === confirmPassword.value) {
            fetch(`${apiUrl}/users/change-password`, {
                headers: header,
                method: 'PUT',
                body: JSON.stringify({
                    oldPassword: oldPassword.value,
                    newPassword: newPassword.value
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
                    oldPassword.classList.add('is-invalid')
                })
        } else {
            confirmPassword.classList.add('is-invalid')
        }
    } else {
        newPassword.classList.add('is-invalid')
    }
}