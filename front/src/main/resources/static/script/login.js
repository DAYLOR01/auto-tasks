const from = (new URLSearchParams(window.location.search)).get('from');
const loginBtn = document.getElementById('login');

document.addEventListener('DOMContentLoaded', function (){
	loginBtn.addEventListener('click', login)
})

const login = () => {
	let username = document.getElementById('username').value;
	let password = document.getElementById('password').value;
	loginBtn.classList.remove('is-invalid')

	fetch(`${apiUrl}/login`, {
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
			window.location.replace(`${baseUrl}${from??''}`);
		})
		.catch(function(reason){
			console.log('error: ' + reason.status);
			loginBtn.classList.add('is-invalid');
		})
}