// Tab Switching Logic
document.getElementById('vision-tab').addEventListener('click', function () {
    document.querySelector('.vision-content').style.display = 'block';
    document.querySelector('.values-content').style.display = 'none';
    this.classList.add('active');
    document.getElementById('values-tab').classList.remove('active');
});

document.getElementById('values-tab').addEventListener('click', function () {
    document.querySelector('.vision-content').style.display = 'none';
    document.querySelector('.values-content').style.display = 'block';
    this.classList.add('active');
    document.getElementById('vision-tab').classList.remove('active');
});
// Smooth Scroll for Internal Links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        document.querySelector(this.getAttribute('href')).scrollIntoView({
            behavior: 'smooth'
        });
    });
});
// Carousel Navigation
const carousel = document.querySelector('.projects-carousel');
const prevButton = document.querySelector('.control-prev');
const nextButton = document.querySelector('.control-next');

prevButton.addEventListener('click', () => {
    carousel.scrollBy({
        left: -300,
        behavior: 'smooth',
    });
});

nextButton.addEventListener('click', () => {
    carousel.scrollBy({
        left: 300,
        behavior: 'smooth',
    });
});

const words = ["Trust", "Service", "Excellence"];
let index = 0;

function changeWord() {
    const heroTitle = document.getElementById("hero-title");
    
    // Add a fade-out effect
    heroTitle.style.opacity = 0;

    setTimeout(() => {
        // Change the word and fade back in
        heroTitle.textContent = `Built on ${words[index]} Since 1983`;
        heroTitle.style.opacity = 1;

        index = (index + 1) % words.length; // Cycle through words
    }, 500); // Half a second fade-out before switching
}

// Change words every 2.5 seconds
setInterval(changeWord, 2500);

const word = ["We Build the Future", "Shaping Tomorrow with Excellence"];
let index1 = 0;

function changeText() {
    const textElement = document.getElementById("rotating-text");

    // Apply fade-out effect
    textElement.style.opacity = 0;

    setTimeout(() => {
        // Change text and fade back in
        textElement.textContent = word[index1];
        textElement.style.opacity = 1;

        index1 = (index1 + 1) % word.length; // Cycle words
    }, 500);
}

setInterval(changeText, 3000);
