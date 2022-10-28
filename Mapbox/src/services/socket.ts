import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

const sock = new SockJS('/api/chat/join');
const stomp = Stomp.over(sock, {
    debug: false,
});

export default stomp;