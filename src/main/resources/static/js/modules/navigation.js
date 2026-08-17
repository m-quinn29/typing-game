import {TypingEngine} from "./typing-engine.js";

const navButtons = document.querySelectorAll('.navigation-button-container button');

function setGameType(mode) {
    if (window.location.pathname === '/versus') {
        if (mode === 'versus') {
            return;
        }
        window.location.href = '/'
    }

    else if (window.location.pathname === '/') {
        if (mode === 'practice') {
            return;
        }
        window.location.href = '/versus';
    }
}

navButtons.forEach(button => {
    button.addEventListener('click', (event) => {
        const gameType = event.currentTarget.getAttribute('data-gameMode');
        setGameType(gameType);
    });
});