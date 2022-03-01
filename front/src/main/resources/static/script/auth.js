const token = window.localStorage.getItem('access_token');
const header = new Headers();
header.append('Authorization', 'Bearer ' + token)

document.addEventListener('DOMContentLoaded', function (){
    fetch('http://localhost:8081/api/login', {
        headers: header
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            document.querySelector('#username').innerHTML = output.name
        })
        .catch(function(reason){
            window.location.replace('http://localhost:8080/login?from=' + window.location.pathname + window.location.search)
        })
})