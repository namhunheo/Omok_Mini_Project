const statusEl = document.getElementById("status");
const countdownEl = document.getElementById("countdown");
const boardEl = document.getElementById("board");

function handleServerMessage(msg) {
    switch (msg.type) {
        case "COUNTDOWN":
            showCountdown(msg.payload.sec);
            break;

        case "GAME_START":
            statusEl.innerText = "게임 시작!";
            countdownEl.innerText = "";
            startGame("BLACK"); // 서버 payload 기준으로 나중에 수정
            break;

        case "MOVE":
            applyMove(
                msg.payload.x,
                msg.payload.y,
                msg.payload.color
            );
            break;

        case "ROOM_WAIT":
            statusEl.innerText = "상대방을 기다리는 중...";
            countdownEl.innerText = "";
            break;

        case "GAME_END":
            alert("게임 종료: " + msg.payload.reason);
            location.href = "/omok/lobby";
            break;
    }
}

function showCountdown(sec) {
    statusEl.innerText = "게임 준비 중...";
    countdownEl.innerText = `시작까지 ${sec}초`;
}

function renderBoard() {
    boardEl.innerHTML = "";
    boardEl.className = "board";

    for (let y = 0; y < BOARD_SIZE; y++) {
        for (let x = 0; x < BOARD_SIZE; x++) {
            const cell = document.createElement("div");
            cell.className = "cell";
            cell.onclick = () => placeStone(x, y);
            boardEl.appendChild(cell);
        }
    }
}

function drawStone(x, y, color) {
    const idx = y * BOARD_SIZE + x;
    const cell = boardEl.children[idx];
    cell.innerText = color === "BLACK" ? "●" : "○";
}
