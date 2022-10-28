import vue from 'vue'

declare module 'vue-router' {
    interface RouteMeta {
        public: boolean;
        role: Array<'user' | 'admin'>
    }
}