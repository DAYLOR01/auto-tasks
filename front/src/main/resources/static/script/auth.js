const token = window.localStorage.getItem('access_token');
const authUserName = document.getElementById('authUserName');
const logoutBtn = document.getElementById('logout');
const userManagement = document.getElementById('userManagement');
let currentUser;
header.append('Authorization', 'Bearer ' + token)

document.addEventListener('DOMContentLoaded', async function (){
    currentUser = await getAuthInfo();
    authUserName.innerHTML = currentUser.name
    logoutBtn.addEventListener('click', logout);
    if (currentUser.roles.find((el, i, arr) => {return el.name === "ROLE_USER_MANAGEMENT"})) {
        userManagement.classList.remove('d-none');
        userManagement.removeAttribute('disabled');
    }
})

async function getAuthInfo() {
    if(currentUser != null)
        return currentUser
    let response = await fetch(`${apiUrl}/login`, {
        headers: header
    });
    if(!response.ok)
        window.location.replace(`${baseUrl}/login?from=${window.location.pathname}${window.location.search}`)
    return await response.json()
}

const logout = () => {
    window.localStorage.removeItem('access_token');
    window.location.href = `${baseUrl}/login`
}
