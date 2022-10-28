import axios from "axios";

const api = axios.create({
  baseURL: "/api",
});

api.interceptors.request.use(function (config) {
  const token = localStorage.getItem("token");
  config.headers.Authorization = token ?? "";
  return config;
});

export default api;
