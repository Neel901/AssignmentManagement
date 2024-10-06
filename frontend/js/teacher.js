
document.getElementById('assignmentForm').addEventListener('submit', async function (event) {
    event.preventDefault(); 

    const title = document.getElementById('title').value;
    const description = document.getElementById('description').value;
    const dueDate = document.getElementById('dueDate').value;
    const className = document.getElementById('className').value;
    const createdBy = localStorage.getItem('userId');

    const payload = {
        title: title,
        description: description,
        dueDate: dueDate,
        className: className,
        createdBy: Number(createdBy)
    };

    try {
        const response = await fetch('http://localhost:8080/assignments', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        const data = await response.json();

        if (response.ok) {
            document.getElementById('response-message').innerText = data.message; 
            document.getElementById('assignmentForm').reset();
        } else {
            throw new Error(data.message || 'Failed to add assignment. Please try again.');
        }
    } catch (error) {
        document.getElementById('error-message').innerText = error.message;
    }
});
window.onload = function() {
    const today = new Date().toISOString().split('T')[0]; 
    document.getElementById('dueDate').setAttribute('min', today);
    fetchSubmittedAssignments();
};

function fetchSubmittedAssignments() {
    fetch('http://localhost:8080/submitted-assignments') 
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
    tableBody.innerHTML = ''; 

    submissions.forEach(submission => {
        const row = tableBody.insertRow();
      
        const assignmentTitle = submission.assignment.title; 
        const studentName = submission.student.username; 
        const submissionDate = new Date(submission.submissionDate).toLocaleString(); 
        const submissionText = submission.submissionText;

        row.insertCell(0).innerText = assignmentTitle;
        row.insertCell(1).innerText = studentName;
        row.insertCell(2).innerText = submissionDate;
        row.insertCell(3).innerText = submissionText;
    });
    
}