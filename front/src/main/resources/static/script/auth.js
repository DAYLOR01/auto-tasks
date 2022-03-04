const token = window.localStorage.getItem('access_token');
var currentUser;
header.append('Authorization', 'Bearer ' + token)

document.addEventListener('DOMContentLoaded', function (){
    getAuthInfo();
    document.getElementById('logout').addEventListener('click', logout);
})

const getAuthInfo = () => {
    fetch(`${apiUrl}/login`, {
        headers: header
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            currentUser = output
            document.getElementById('authUserName').innerHTML = output.name
            if(fillUserInfo != null)
                fillUserInfo()
        })
        .catch(function(reason){
            window.location.replace(`${baseUrl}/login?from=${window.location.pathname}${window.location.search}`)
        })
}

const logout = () => {
    window.localStorage.removeItem('access_token');
    window.location.href = `${baseUrl}/login`
}
