export type User = {
    id: number;
    email: string;
    username: string;
    password: string;
    admin: boolean;
    photoUrl?: string;
    photo?: File;
}