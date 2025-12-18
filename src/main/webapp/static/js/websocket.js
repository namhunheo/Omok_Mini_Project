let socket = null;

function connectWebSocket() {
    socket = new WebSocket(WS_URL);

    socket.onopen = () => {
        console.log("WebSocket 연결 성공");
    };

    socket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        handleServerMessage(message);
    };

    socket.onclose = () => {
        console.log("WebSocket 연결 종료");
    };
}

function sendMessage(type, payload) {
    socket.send(JSON.stringify({ type, payload }));
}

document.getElementById("sendChat").onclick = () => {
    const input = document.getElementById("chatInput");
    if (!input.value.trim()) return;

    sendMessage("CHAT", input.value);
    input.value = "";
};


connectWebSocket();