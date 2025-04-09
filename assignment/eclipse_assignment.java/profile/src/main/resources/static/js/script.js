document.getElementById("profileForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const name = document.getElementById("name").value;
    const gender = document.getElementById("gender").value;
    const color = document.getElementById("color").value;
    const fontSize = document.getElementById("fontSize").value;

    const profileData = {
        name: name,
        gender: gender,
        color: color,
        fontSize: fontSize
    };

    // Send to Spring Boot backend
    fetch("/submit", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(profileData)
    })
    .then(response => response.json())
    .then(data => {
        displayProfileCard(data);
    })
    .catch(error => {
        console.error("Error:", error);
    });
});

function displayProfileCard(profile) {
    const container = document.getElementById("profileCardContainer");
    const profileImage = profile.gender === "Male" ? "/images/male.jpg" : "/images/female.png";

    container.innerHTML = `
        <div class="profile-card" style="background-color:${profile.color}; font-size:${profile.fontSize}px">
            <img src="${profileImage}" alt="Profile Picture" />
            <h2>${profile.name}</h2>
        </div>
    `;
}
