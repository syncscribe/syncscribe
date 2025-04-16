import {create} from 'zustand';
import {User} from "oidc-client-ts";

interface AuthUserState {
  user: User | null;
  setUser: (user: User) => void;
  clearUser: () => void;
}

export const useAuthUserStore = create<AuthUserState>((set) => ({
  user: null,
  setUser: (user: User) => set({user: user}),
  clearUser: () => set({user: null}),
}));
