function register() {

    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    var userData = {
        username: username,
        password: password,
    };


    fetch('http://localhost:9495/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Registration failed. Please try again.');
            }
            return response.json();
        })
        .then(data => {

            console.log(data);
            alert('Registration successful!');
        })
        .catch(error => {

            console.error(error);
            alert('Registration failed. Please try again.');
        });
}
