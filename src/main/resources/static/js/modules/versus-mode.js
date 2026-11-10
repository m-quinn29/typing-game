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
    const matchResponse = await response.json();
    typingEngine.raceId = matchResponse.raceId;
    typingEngine.playerId = matchResponse.playerId;

    await socketReady;

    await initRace();
}

async function initRace() {
    console.log("Setting up race:", typingEngine.raceId);

    await stompClient.subscribe(`/topic/race/${typingEngine.raceId}`, message => {
        console.log("Subscribed");
        const raceState = JSON.parse(message.body);
        console.log("Received RaceState update:", JSON.stringify(raceState, null, 2));
    });

    stompClient.publish({
        destination: `/app/race/${typingEngine.raceId}/progress`,
        body: JSON.stringify({
            playerId: typingEngine.playerId,
            charactersTyped: 0,
            wpm: 0,
            finished: false
        })
    });

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