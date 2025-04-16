import {createZitadelAuth, ZitadelConfig} from "@zitadel/react";
import {useEffect, useState} from "react";
import {Navigate, Route, Routes} from "react-router-dom";
import Login from "@/pages/auth/Login.tsx";
import ForgotPassword from "@/pages/auth/ForgotPassword.tsx";
import Signup from "@/pages/auth/Signup.tsx";
import {Layout} from "@/components/Layout.tsx";
import Main from "@/pages/Main.tsx";
import DocEditor from "@/pages/DocEditor.tsx";
import DocView from "@/pages/DocView.tsx";
import Callback from "@/pages/auth/Callback.tsx";
import ProtectedRoute from "@/components/auth/ProtectedRoute.tsx";
import NotFound from "@/pages/NotFound.tsx";
import {useAuthUserStore} from "@/store/useAuthUserStore.ts";

function App() {
  const config: ZitadelConfig = {
    authority: "http://localhost:8080/",
    client_id: "315832230725353476",
    redirect_uri: "http://localhost:5173/callback",
    response_type: 'code',
    scope: 'openid profile email',
    post_logout_redirect_uri: "http://localhost:5173/",
    response_mode: "query",
  }
  const zitadel = createZitadelAuth(config);

  const signout = () => {
    zitadel.signout();
  }

  const signin = () => {
    zitadel.authorize();
  }

  const [authenticated, setAuthenticated] = useState<boolean | null>(null);
  const setUser = useAuthUserStore((state) => state.setUser)

  useEffect(() => {
    zitadel.userManager.getUser().then((user) => {
      console.log("App: ", user);
      if (user) {
        setAuthenticated(true);
        setUser(user)
      } else {
        setAuthenticated(false);
      }
    })

    if (window.location.href.includes('id_token') || window.location.href.includes('code')) {
        zitadel.userManager.signinRedirectCallback()
          .then((user) => {
            if (user) {
              console.log('Redirect callback user', user);
              setAuthenticated(true);
              setUser(user); // Store the entire user object
            }
          })
          .catch((error) => {
            console.error('Sign-in error', error);
          });
      }
  }, [zitadel]);

  return (
    <Routes>
      <Route path="/" element={<Navigate to={"/login"}/>}></Route>
      <Route path="/callback" element={
        <Callback
          authenticated={authenticated}
          setAuth={setAuthenticated}
          userManager={zitadel.userManager}
        />
      }/>
      <Route path={"/login"} element={<Login login={signin}/>}></Route>
      <Route path={"/forgotPassword"} element={<ForgotPassword/>}></Route>
      <Route path={"/signup"} element={<Signup/>}></Route>
      <Route element={<ProtectedRoute/>}>
        <Route path={"/home"} element={<Layout signout={signout}/>}>
          <Route index element={<Main/>}></Route>
          <Route path={"editor"} element={<DocEditor/>}></Route>
          <Route path={"documents"} element={<DocView/>}></Route>
          <Route path={"documents?root=*"} element={<DocView/>}></Route>
          <Route path={"documents?filter=*"} element={<DocView/>}></Route>
          <Route path={"documents/labels"} element={<DocView/>}></Route>
        </Route>
      </Route>
      <Route path={"*"} element={<NotFound />}></Route>
    </Routes>
  )
}

export default App;