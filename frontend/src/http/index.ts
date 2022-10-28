import axios from 'axios'

const http = axios.create({
  baseURL: '/api',
  timeout: 5000
})

http.interceptors.request.use(
  function(config) {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  function(error) {
    return Promise.reject(error)
  }
)

export default http
