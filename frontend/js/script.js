// script.js (login logic)
document.getElementById('loginForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent default form submission

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value;

    // Create the payload for the request
    const payload = {
        username: username,
        password: password,
        role: role
    };

    try {
        const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        // Handle response
        const data = await response.json();

        if (response.ok) {
            // Successful login
            // alert(data.message); // Show success message
            localStorage.setItem('userId', data.userId); // Store user ID in localStorage

            // Check if the user role is Teacher
            if (role === 'TEACHER') {
                // Redirect to the teacher dashboard
                window.location.href = 'teacher.html'; // Path to your teacher dashboard HTML
            } else {
                // Handle redirection for other roles
                window.location.href = 'student.html'; // Example for student role
            }
        } else {
            // Error handling
            throw new Error(data.message || 'Login failed. Please try again.');
        }
    } catch (error) {
        document.getElementById('error-message').innerText = error.message;
    }
});
