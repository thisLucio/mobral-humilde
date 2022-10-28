type User = {
  id: number;
  username: string;
  photoUrl?: string;
};

export function getUser(): User {
  const userData = localStorage.getItem("user");
  const user = userData ? (JSON.parse(userData) as User) : null;

  if (!user) {
    throw new Error("Could not retrieve user");
  }

  return user;
}
