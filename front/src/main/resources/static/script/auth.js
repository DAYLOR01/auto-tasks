const token = window.localStorage.getItem('access_token');
let currentUser;
header.append('Authorization', 'Bearer ' + token)

document.addEventListener('DOMContentLoaded', async function (){
    currentUser = await getAuthInfo();
    document.getElementById('authUserName').innerHTML = currentUser.name
    document.getElementById('logout').addEventListener('click', logout);
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
