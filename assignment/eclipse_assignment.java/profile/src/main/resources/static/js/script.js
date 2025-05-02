document.getElementById("profileForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const name = document.getElementById("name").value.trim();
    const gender = document.getElementById("gender").value.trim();
    const color = document.getElementById("color").value;
    const fontSize = parseInt(document.getElementById("fontSize").value);

    if (!name || !gender || !color || isNaN(fontSize)) {
        alert("Please fill all fields correctly.");
        return;
    }

    const profile = {
        name,
        gender,
        color,
        fontSize
    };

    displayProfileCard(profile);

    // Clear form
    document.getElementById("profileForm").reset();
});

function displayProfileCard(profile) {
    const gender = profile.gender.toLowerCase(); // normalize
    const imagePath = gender === "male" ? "/images/male.png" : "/images/female.png";

    console.log("Rendering card for:", profile.name, "Gender:", gender, "Image:", imagePath);

    const cardHTML = `
        <div class="card" style="background-color: ${profile.color}; font-size: ${profile.fontSize}px; padding: 10px; margin: 10px; border-radius: 10px;">
            <img src="${imagePath}" alt="${gender}" width="100" height="100">
            <h2>${profile.name}</h2>
            <p>Gender: ${profile.gender}</p>
        </div>
    `;

    document.getElementById("profileCardContainer").innerHTML += cardHTML;
}
