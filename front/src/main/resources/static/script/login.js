const header = new Headers();
header.append('Content-Type', 'application/json;charset=utf-8');
const from = (new URLSearchParams(window.location.search)).get('from');
const baseUrl = 'http://localhost:8080';

const login = () => {
	let username = document.querySelector('#username').value;
	let password = document.querySelector('#password').value;

	fetch('http://localhost:8081/api/login', {
		method: 'POST',
		headers: header,
		body: JSON.stringify({
			username: username,
			password: password
		})
	})
		.then(function(value){
			if (value.status !== 200){
				return Promise.reject(new Error(value.status));
			}
			return value.json();
		})
		.then(function(output){
			window.localStorage.setItem('access_token', output.token);
			window.location.replace(baseUrl + (from??''));
		})
		.catch(function(reason){
			console.log('error: ' + reason.status);
		})
}