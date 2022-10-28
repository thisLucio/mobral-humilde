import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

const sock = new SockJS(`/api/chat/join`);
const socket = Stomp.over(sock, {
    debug: false,
});

export default socket;