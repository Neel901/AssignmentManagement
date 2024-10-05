// dashboard.js
document.getElementById('assignmentForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent default form submission

    const title = document.getElementById('title').value;
    const description = document.getElementById('description').value;
    const dueDate = document.getElementById('dueDate').value;
    const className = document.getElementById('className').value;
    const createdBy = localStorage.getItem('userId'); // Get user ID from localStorage

    // Create the payload for the request
    const payload = {
        title: title,
        description: description,
        dueDate: dueDate,
        className: className,
        createdBy: Number(createdBy) // Assuming createdBy is a number (e.g., user ID)
    };

    try {
        const response = await fetch('http://localhost:8080/assignments', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        // Handle response
        const data = await response.json();

        if (response.ok) {
            // Successful assignment creation
            document.getElementById('response-message').innerText = data.message; // Show success message
            document.getElementById('assignmentForm').reset(); // Clear form
        } else {
            // Error handling
            throw new Error(data.message || 'Failed to add assignment. Please try again.');
        }
    } catch (error) {
        document.getElementById('error-message').innerText = error.message;
    }
});
window.onload = function() {
    fetchSubmittedAssignments();
};

function fetchSubmittedAssignments() {
    fetch('http://localhost:8080/submitted-assignments') // Adjust the URL as needed
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            displaySubmittedAssignments(data);
        })
        .catch(error => {
            document.getElementById('error-message').innerText = "Error fetching assignments: " + error.message;
        });
}

function displaySubmittedAssignments(submissions) {
    const tableBody = document.getElementById('submissionsTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = ''; // Clear previous content

    submissions.forEach(submission => {
        const row = tableBody.insertRow();
        
        // Assuming submission has these properties
        const assignmentTitle = submission.assignment.title; // Adjust based on your data structure
        const studentName = submission.student.username; // Adjust based on your data structure
        const submissionDate = new Date(submission.submissionDate).toLocaleString(); // Format date
        const submissionText = submission.submissionText;

        row.insertCell(0).innerText = assignmentTitle;
        row.insertCell(1).innerText = studentName;
        row.insertCell(2).innerText = submissionDate;
        row.insertCell(3).innerText = submissionText;
    });
}
