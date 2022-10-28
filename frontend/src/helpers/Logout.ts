import router from '@/router'

export function logout() {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    router.push({ name: 'Login' })
}
