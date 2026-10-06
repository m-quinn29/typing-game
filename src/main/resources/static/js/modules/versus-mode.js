import {TypingEngine} from "./typing-engine.js";

const DOM = {
    promptElement: document.getElementById('prompt-text'),
    inputElement: document.getElementById('input-field'),
    timerElement: document.getElementById('timer'),
};

const typingEngine = new TypingEngine(DOM);
let stompClient;

const socketReady = new Promise((resolve, reject) => {
    stompClient = new StompJs.Client({
        webSocketFactory: () => new SockJS('/ws'),

        onConnect: () => {
            console.log("Connected to WebSocket");
            resolve();
        },

        onStompError: (frame) => {
            console.error("STOMP error:", frame);
            reject(frame);
        }
    });

    stompClient.activate();
});

function addListeners(){
    const joinButtons = document.querySelectorAll('.join-buttons button');

    joinButtons.forEach(button => {
        button.addEventListener('click', (event) => {
            const raceType = event.currentTarget.getAttribute('data-type');
            startRace(raceType);
        });
    });
}

async function startRace(raceType) {
    document.querySelector('.join-buttons').style.display = "none";

    typingEngine.raceType = raceType;
    showState('waiting-for-race');

    const response = await fetch(`/find-race?raceType=${raceType}`);
    typingEngine.raceId = await response.text();

    await socketReady;

    setupRaceLobby();
}

function setupRaceLobby() {
    console.log("Setting up race:", typingEngine.raceId);


    stompClient.subscribe(`/topic/race/${typingEngine.raceId}`, message => {
        const raceState = JSON.parse(message.body);
    });

    // TODO: When subscribing return json with raceId, prompt, players
    typingEngine.newRaceText();
    showState('race');


}

/**
 * Deactivates all active DOM elements and sets provided state class active.
 * @param state HTML class to set visible.
 */
function showState(state) {
    document.querySelectorAll('.game-state').forEach(element => {
        element.classList.remove('active')
    });

    document.querySelector(`.${state}`).classList.add('active');
}

addListeners();