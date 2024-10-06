document.getElementById('signupForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value;

    const data = {
        username: username,
        password: password,
        role: role
    };

    fetch('http://localhost:8080/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Signup failed');
        }
    })
    .then(data => {
        
        alert('Signup successful! Welcome');
    })
    .catch(error => {
        
        document.getElementById('error-message').innerText = error.message;
    });
});