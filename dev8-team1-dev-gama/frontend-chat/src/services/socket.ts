import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

const sock = new SockJS(`${process.env.VUE_APP_API_URL}/chat/join`);
const stomp = Stomp.over(sock, {
    debug: false,
});

export default stomp;