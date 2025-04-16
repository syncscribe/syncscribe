import { createZitadelAuth, ZitadelConfig } from "@zitadel/react";
import { User } from "oidc-client-ts";
import { useEffect, useState } from "react";
import { Navigate, Route, Routes, useNavigate } from "react-router-dom";
import Login from "@/pages/auth/Login.tsx";
import ForgotPassword from "@/pages/auth/ForgotPassword.tsx";
import Signup from "@/pages/auth/Signup.tsx";
import { Layout } from "@/components/Layout.tsx";
import Main from "@/pages/Main.tsx";
import DocEditor from "@/pages/DocEditor.tsx";
import DocView from "@/pages/DocView.tsx";
import Callback from "@/pages/auth/Callback.tsx";
import ProtectedRoute from "@/components/auth/ProtectedRoute.tsx";
import NotFound from "@/pages/NotFound.tsx";
import { useAuthUserStore } from "@/store/useAuthUserStore.ts";
import Logout from "./pages/auth/Logout";

function App() {
  const config: ZitadelConfig = {
    authority: "http://localhost:8080/",
    client_id: "315832230725353476",
    redirect_uri: "http://localhost:5173/oauth2/callback",
    response_type: "code",
    scope: "openid profile email",
    post_logout_redirect_uri: "http://localhost:5173/",
    response_mode: "query",
  };
  const zitadel = createZitadelAuth(config);

  const signout = async () => {
    await zitadel.signout();
  };

  const signin = async () => {
    await zitadel.authorize();
  };

  const [authenticated, setAuthenticated] = useState<boolean | null>(null);
  const setUser = useAuthUserStore((state) => state.setUser);
  const clearUser = useAuthUserStore((state) => state.clearUser);

  useEffect(() => {
    // Check for existing user session first
    zitadel.userManager.getUser().then((user: User | null) => {
      if (user) {
        setAuthenticated(true);
        setUser(user);
      } else {
        setAuthenticated(false);
        clearUser();
      }
    });

    // Handle the callback from Zitadel
    const handleCallback = async () => {
      if (
        window.location.href.includes("id_token") ||
        window.location.href.includes("code")
      ) {
        try {
          const user = await zitadel.userManager.signinRedirectCallback();
          if (user) {
            setAuthenticated(true);
            setUser(user);
          }
        } catch (error) {
          setAuthenticated(false);
          clearUser();
        }
      }
    };

    handleCallback();

    // Set up event listeners for auth state changes
    const handleUserLoaded = (user: User) => {
      setAuthenticated(true);
      setUser(user);
    };

    const handleUserUnloaded = () => {
      setAuthenticated(false);
      clearUser();
    };

    zitadel.userManager.events.addUserLoaded(handleUserLoaded);
    zitadel.userManager.events.addUserUnloaded(handleUserUnloaded);

    return () => {
      zitadel.userManager.events.removeUserLoaded(handleUserLoaded);
      zitadel.userManager.events.removeUserUnloaded(handleUserUnloaded);
    };
  }, [zitadel]);

  return (
    <Routes>
      <Route path="/" element={<Navigate to={"/login"} />}></Route>
      <Route
        path="/oauth2/callback"
        element={
          <Callback
            authenticated={authenticated}
            setAuth={setAuthenticated}
            userManager={zitadel.userManager}
          />
        }
      />
      <Route path={"/logout"} element={<Logout logout={signout} />}></Route>
      <Route path={"/login"} element={<Login login={signin} />}></Route>
      <Route path={"/forgotPassword"} element={<ForgotPassword />}></Route>
      <Route path={"/signup"} element={<Signup />}></Route>
      <Route element={<ProtectedRoute />}>
        <Route path={"/home"} element={<Layout />}>
          <Route index element={<Main />}></Route>
          <Route path={"editor"} element={<DocEditor />}></Route>
          <Route path={"documents"} element={<DocView />}></Route>
          <Route path={"documents/labels"} element={<DocView />}></Route>
        </Route>
      </Route>
      <Route path={"*"} element={<NotFound />}></Route>
    </Routes>
  );
}

export default App;
