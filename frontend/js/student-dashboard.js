// student-dashboard.js
document.addEventListener('DOMContentLoaded', async function () {
    try {
        const response = await fetch('http://localhost:8080/assignments', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch assignments.');
        }

        const assignments = await response.json();
        displayAssignments(assignments);
    } catch (error) {
        document.getElementById('error-message').innerText = error.message;
    }
});

function displayAssignments(assignments) {
    const assignmentsTableBody = document.getElementById('assignmentsTable').getElementsByTagName('tbody')[0];

    // Clear the table before adding new rows
    assignmentsTableBody.innerHTML = '';

    assignments.forEach(assignment => {
        const row = assignmentsTableBody.insertRow();

        const titleCell = row.insertCell(0);
        const descriptionCell = row.insertCell(1);
        const dueDateCell = row.insertCell(2);
        const classNameCell = row.insertCell(3);
        const createdByCell = row.insertCell(4);
        const actionCell = row.insertCell(5);

        titleCell.innerText = assignment.title;
        descriptionCell.innerText = assignment.description;
        dueDateCell.innerText = new Date(assignment.dueDate).toLocaleDateString(); // Format as needed
        classNameCell.innerText = assignment.className;
        createdByCell.innerText = assignment.createdBy || 'N/A'; // Handle if createdBy is null

        // Add the Submit button
        const submitButton = document.createElement('button');
        submitButton.innerText = 'Submit Assignment';
        submitButton.onclick = () => submitAssignment(assignment.id);
        actionCell.appendChild(submitButton);
    });
}

async function submitAssignment(assignmentId) {
    const s = localStorage.getItem('userId');
    const submissionText = prompt("Enter your submission text:");

    const studentId = parseInt(s)
    console.log(studentId)

    if (!submissionText) {
        alert("Submission text cannot be empty.");
        return;
    }

    const submissionData = {
        assignmentId: assignmentId,
        studentId: studentId,
        submissionText: submissionText
    };

    try {
        const response = await fetch('http://localhost:8080/submissions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(submissionData)
        });

        if (!response.ok) {
            throw new Error('Failed to submit assignment.');
        }

        alert("Assignment submitted successfully!");
    } catch (error) {
        alert(error.message);
    }
}
