const myName = document.getElementById('myName');
const myUsername = document.getElementById('myUsername');
const myRoles = document.getElementById('myRoles');
const myTags = document.getElementById('myTags');

const fillUserInfo = () => {
    myName.innerText = currentUser.name
    myUsername.innerText = currentUser.username
}
