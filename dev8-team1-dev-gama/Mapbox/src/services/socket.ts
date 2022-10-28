import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

const sock = new SockJS(`http://localhost/api/chat/join`);
const stomp = Stomp.over(sock, {
    debug: true,
});

export default stomp;